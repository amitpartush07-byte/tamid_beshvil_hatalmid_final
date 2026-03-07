package com.example.tamid_beshvil_hatalmid_try1;

import static com.example.tamid_beshvil_hatalmid_try1.FBRef.refAuth;

import android.content.Intent;
import android.os.Bundle;import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignInUp_Activity extends AppCompatActivity {

    private EditText Email, Pass;
    private Button SignUp, SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_signinup);

        Email = findViewById(R.id.Email_txt);
        Pass = findViewById(R.id.Pass_txt);
        SignUp = findViewById(R.id.SignUp_btn);
        SignIn = findViewById(R.id.SignIn_btn);


        SignIn.setOnClickListener(view -> signInUser());
        SignUp.setOnClickListener(view -> signUpUser());
    }

    private void signUpUser() {
        String emailStr = Email.getText().toString().trim();
        String passStr = Pass.getText().toString().trim();

        if (emailStr.isEmpty() || passStr.isEmpty()) {
            Toast.makeText(this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        refAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "נרשמת בהצלחה!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, PersonalInfo_Page.class));
                        finish();
                    } else {
                        Toast.makeText(this, "שגיאה בהרשמה: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void signInUser() {
        String emailStr = Email.getText().toString().trim();
        String passStr = Pass.getText().toString().trim();

        if (emailStr.isEmpty() || passStr.isEmpty()) {
            Toast.makeText(this, "אנא הזן אימייל וסיסמה", Toast.LENGTH_SHORT).show();
            return;
        }

        refAuth.signInWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(this, Home_Page.class));
                        finish();
                    } else {
                        Toast.makeText(this, "התחברות נכשלה", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}