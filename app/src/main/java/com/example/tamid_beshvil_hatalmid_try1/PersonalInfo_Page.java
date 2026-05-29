package com.example.tamid_beshvil_hatalmid_try1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalInfo_Page extends AppCompatActivity {

    private TextInputEditText fullName, userID, grade, phTxt, subject;
    private Button update;
    private MaterialButtonToggleGroup toggleGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalinfo);

        fullName = findViewById(R.id.NameTxt);
        userID = findViewById(R.id.UserIDTxt);
        grade = findViewById(R.id.GradeTxt);
        phTxt = findViewById(R.id.PNTxt);
        subject = findViewById(R.id.SubTxt);
        update = findViewById(R.id.UpdateBtn);
        toggleGroup = findViewById(R.id.ToggleGroupType);
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
            } else if (id == R.id.action_logout) {com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, SignInUp_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });
        loadExistingUserData();

        update.setOnClickListener(view -> updateInfo());
    }


    private void loadExistingUserData() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        FirebaseDatabase.getInstance().getReference("Students").child(uid).get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        fullName.setText(snapshot.child("name").getValue(String.class));
                        userID.setText(snapshot.child("userID").getValue(String.class));
                        phTxt.setText(snapshot.child("phone").getValue(String.class));
                        grade.setText(snapshot.child("grade").getValue(String.class));
                        toggleGroup.check(R.id.btnStudent);
                    } else {
                        FirebaseDatabase.getInstance().getReference("Teachers").child(uid).get()
                                .addOnSuccessListener(tSnapshot -> {
                                    if (tSnapshot.exists()) {
                                        fullName.setText(tSnapshot.child("name").getValue(String.class));
                                        userID.setText(tSnapshot.child("userID").getValue(String.class));
                                        phTxt.setText(tSnapshot.child("phone").getValue(String.class));
                                        subject.setText(tSnapshot.child("subject").getValue(String.class));
                                        toggleGroup.check(R.id.btnTeacher);
                                    }
                                });
                    }
                });
    }


    private void updateInfo() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        String name = fullName.getText().toString().trim();
        String idNum = userID.getText().toString().trim();
        String phone = phTxt.getText().toString().trim();
        String userGrade = grade.getText().toString().trim();
        String sub = subject.getText().toString().trim();

        if (name.isEmpty() || idNum.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "אנא מלא את כל שדות החובה (*)", Toast.LENGTH_SHORT).show();
            return;
        }

        int checkedId = toggleGroup.getCheckedButtonId();
        if (checkedId == R.id.btnTeacher) {
            Teacher teacher = new Teacher(name, idNum, email, "******", sub, phone);
            FirebaseDatabase.getInstance().getReference("Teachers").child(uid).setValue(teacher)
                    .addOnSuccessListener(aVoid -> showCompletionAlert());
        } else {
            // Default to student
            Student student = new Student(name, idNum, email, "******", userGrade, phone);
            FirebaseDatabase.getInstance().getReference("Students").child(uid).setValue(student)
                    .addOnSuccessListener(aVoid -> showCompletionAlert());
        }
    }

    private void showCompletionAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("הפרטים נשמרו!")
                .setMessage("הפרופיל שלך עודכן בהצלחה. כעת ניתן לחזור למסך הבית.")
                .setCancelable(false)
                .setPositiveButton("חזרה למסך הבית", (dialog, which) -> goHome())
                .show();
    }

    private void goHome() {
        Intent intent = new Intent(PersonalInfo_Page.this, Home_Page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}