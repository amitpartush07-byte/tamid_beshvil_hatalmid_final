package com.example.tamid_beshvil_hatalmid_try1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class SignInUp_Activity extends AppCompatActivity {

    private TextInputEditText emailTxt, passTxt;
    private Button btnSignUp, btnSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_signinup);

        mAuth = FirebaseAuth.getInstance();
        emailTxt = findViewById(R.id.Email_txt);
        passTxt = findViewById(R.id.Pass_txt);
        btnSignUp = findViewById(R.id.SignUp_btn);
        btnSignIn = findViewById(R.id.SignIn_btn);

        btnSignIn.setOnClickListener(v -> loginUser());
        btnSignUp.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = emailTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Success: Show stylish Alert
                        showSuccessAlert();
                    } else {
                        Toast.makeText(this, "שגיאה בהרשמה: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loginUser() {
        String email = emailTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "אנא הזן פרטי התחברות", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(this, Home_Page.class));
                        finish();
                    } else {
                        Toast.makeText(this, "התחברות נכשלה: בדוק אימייל וסיסמה", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showSuccessAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("ברוכים הבאים!")
                .setMessage("החשבון שלך נוצר בהצלחה. כעת נעבור למסך השלמת הפרטים האישיים.")
                .setCancelable(false)
                .setPositiveButton("המשך", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(SignInUp_Activity.this, PersonalInfo_Page.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }
}