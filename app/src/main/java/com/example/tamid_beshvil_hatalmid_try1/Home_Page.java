package com.example.tamid_beshvil_hatalmid_try1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;

public class Home_Page extends AppCompatActivity {

    private Button photoB, qesB, AIB;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(this, Home_Page.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        topAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.action_profile) {
                Intent intent = new Intent(this, PersonalInfo_Page.class);
                startActivity(intent);
                return true;

            } else if (id == R.id.action_logout) {
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, SignInUp_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        photoB = findViewById(R.id.PhotoBtn);
        qesB = findViewById(R.id.QesBtn);
        AIB = findViewById(R.id.AIBtn);

        AIB.setOnClickListener(v -> startActivity(new Intent(this, AI_page.class)));
        qesB.setOnClickListener(v -> startActivity(new Intent(this, QuestionBillActivity.class)));
        photoB.setOnClickListener(v -> startActivity(new Intent(this, AI_Solve_Photo_Activity.class)));
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Home_Page.this, SignInUp_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}