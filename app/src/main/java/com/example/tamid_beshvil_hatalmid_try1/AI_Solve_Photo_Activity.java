package com.example.tamid_beshvil_hatalmid_try1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.android.material.appbar.MaterialToolbar;
// IMPORTANT: Ensure these come from com.google.common
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;

public class AI_Solve_Photo_Activity extends AppCompatActivity {

    private ImageView ivQuestion;
    private TextView tvSolution;
    private ProgressBar progressBar;
    private Button btnSolve, btnPickImage;
    private Bitmap selectedBitmap;
    private GeminiManager geminiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_solve_photo);

        ivQuestion = findViewById(R.id.ivQuestion);
        tvSolution = findViewById(R.id.tvSolution);
        progressBar = findViewById(R.id.progressBar);
        btnSolve = findViewById(R.id.btnSolve);
        btnPickImage = findViewById(R.id.btnPickImage);
        geminiManager = GeminiManager.getInstance();

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.action_home) {
                Intent intent = new Intent(this, Home_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if (id == R.id.action_profile) {
                Intent intent = new Intent(this, PersonalInfo_Page.class);
                startActivity(intent);

            } else if (id == R.id.action_logout) {
                // 3. LOGOUT OPTION (Inside dots): Sign out
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, SignInUp_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        handleImageSelection(imageUri);
                    }
                }
        );

        btnPickImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        btnSolve.setOnClickListener(v -> solveWithAI());
    }

    private void handleImageSelection(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap original = BitmapFactory.decodeStream(inputStream);

            // Resize to prevent memory crashes
            int width = original.getWidth();
            int height = original.getHeight();
            float ratio = (float) width / height;
            int newWidth = 1024;
            int newHeight = (int) (1024 / ratio);

            selectedBitmap = Bitmap.createScaledBitmap(original, newWidth, newHeight, true);
            ivQuestion.setImageBitmap(selectedBitmap);

            btnSolve.setEnabled(true);
            tvSolution.setText("תמונה נטענה בהצלחה. לחץ על הכפתור כדי לפתור.");
        } catch (Exception e) {
            Toast.makeText(this, "שגיאה בטעינת התמונה", Toast.LENGTH_SHORT).show();
        }
    }

    private void solveWithAI() {
        if (selectedBitmap == null) return;

        progressBar.setVisibility(View.VISIBLE);
        tvSolution.setText("ה-AI מנתח את השאלה ומכין פתרון...");
        btnSolve.setEnabled(false);

        Content content = new Content.Builder()
                .addImage(selectedBitmap)
                .addText("Solve this school question step by step. Explain your logic clearly in Hebrew. Use professional academic language.")
                .build();

        ListenableFuture<GenerateContentResponse> response =
                geminiManager.getModel().generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    tvSolution.setText(result.getText());
                    btnSolve.setEnabled(true);
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    tvSolution.setText("שגיאה בחיבור ל-AI: " + t.getMessage());
                    btnSolve.setEnabled(true);
                    Toast.makeText(AI_Solve_Photo_Activity.this, "החיבור נכשל", Toast.LENGTH_SHORT).show();
                });
            }
        }, this.getMainExecutor());
    }
}