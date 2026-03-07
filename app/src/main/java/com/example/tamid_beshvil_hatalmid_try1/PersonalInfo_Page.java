package com.example.tamid_beshvil_hatalmid_try1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalInfo_Page extends AppCompatActivity {

    TextInputEditText fullName, userID, grade, phTxt, subject;
    Button update;
    MaterialButtonToggleGroup toggleGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalinfo);

        // Link Java objects to XML IDs
        fullName = findViewById(R.id.NameTxt);
        userID = findViewById(R.id.UserIDTxt);
        grade = findViewById(R.id.GradeTxt);
        phTxt = findViewById(R.id.PNTxt);
        subject = findViewById(R.id.SubTxt);
        update = findViewById(R.id.UpdateBtn);
        toggleGroup = findViewById(R.id.ToggleGroupType);

        update.setOnClickListener(view -> updateInfo());
    }

    private void updateInfo() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // Get Input Data
        String name = fullName.getText().toString().trim();
        String idNum = userID.getText().toString().trim();
        String phone = phTxt.getText().toString().trim();

        // Optional Fields
        String userGrade = grade.getText().toString().trim();
        String sub = subject.getText().toString().trim();

        if (name.isEmpty()) {
            fullName.setError("חובה להזין שם מלא");
            fullName.requestFocus();
            return;
        }
        if (idNum.isEmpty()) {
            userID.setError("חובה להזין תעודת זהות");
            userID.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            phTxt.setError("חובה להזין מספר טלפון");
            phTxt.requestFocus();
            return;
        }

        int checkedId = toggleGroup.getCheckedButtonId();

        if (checkedId == R.id.btnTeacher) {
            Teacher teacher = new Teacher(name, idNum, email, "******", sub, phone);
            FirebaseDatabase.getInstance().getReference("Teachers").child(uid).setValue(teacher)
                    .addOnSuccessListener(aVoid -> success());
        } else {
            Student student = new Student(name, idNum, email, "******", userGrade, phone);
            FirebaseDatabase.getInstance().getReference("Students").child(uid).setValue(student)
                    .addOnSuccessListener(aVoid -> success());
        }
    }

    private void success() {
        Toast.makeText(this, "הפרטים נשמרו בהצלחה!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, Home_Page.class));
        finish();
    }
}