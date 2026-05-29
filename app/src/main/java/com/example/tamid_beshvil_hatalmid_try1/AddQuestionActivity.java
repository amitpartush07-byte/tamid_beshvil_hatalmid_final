package com.example.tamid_beshvil_hatalmid_try1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class AddQuestionActivity extends AppCompatActivity {

    private TextInputEditText editTitle, editDesc, editExam, editSolve, editVideo;
    private Spinner spinnerSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDescription);
        editExam = findViewById(R.id.editExamLink);
        editSolve = findViewById(R.id.editSolveLink);
        editVideo = findViewById(R.id.editVideoLink);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        Button btnSave = findViewById(R.id.btnSave);

        String[] subjects = {"מתמטיקה", "פיזיקה", "אנגלית"};
        spinnerSubject.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjects));

        setupToolbarLogic();

        btnSave.setOnClickListener(v -> {
            String sub = spinnerSubject.getSelectedItem().toString();
            String t = editTitle.getText().toString().trim();
            String d = editDesc.getText().toString().trim();
            String e = editExam.getText().toString().trim();
            String s = editSolve.getText().toString().trim();
            String vUrl = editVideo.getText().toString().trim();

            if (t.isEmpty() || d.isEmpty() || e.isEmpty() || s.isEmpty()) {
                Toast.makeText(this, "אנא מלא את כל שדות החובה", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent res = new Intent();
            res.putExtra("subject", sub);
            res.putExtra("title", t);
            res.putExtra("desc", d);
            res.putExtra("exam", e);
            res.putExtra("solve", s);
            res.putExtra("video", vUrl);
            setResult(RESULT_OK, res);
            finish();
        });


    }
    private void setupToolbarLogic() {
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
             if (id == R.id.action_home) {
                startActivity(new Intent(this, Home_Page.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
            } else if (id == R.id.action_profile) {
                startActivity(new Intent(this, PersonalInfo_Page.class));
                return true;
            } else if (id == R.id.action_logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignInUp_Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                return true;
            }
            return false;
        });
    }
}