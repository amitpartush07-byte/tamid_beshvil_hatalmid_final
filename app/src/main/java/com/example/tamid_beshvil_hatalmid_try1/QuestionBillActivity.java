package com.example.tamid_beshvil_hatalmid_try1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;

public class QuestionBillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bill);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_home) {
                startActivity(new Intent(this, Home_Page.class));
                return true;
            } else if (item.getItemId() == R.id.action_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, SignInUp_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        RecyclerView rvMath = findViewById(R.id.rvMath);
        rvMath.setLayoutManager(new LinearLayoutManager(this));
        List<QuestionItem> mathList = new ArrayList<>();

        mathList.add(new QuestionItem("מתמטיקה", "יואל גבע 806 (581)", "בגרות קיץ 2024 מועד א",
                "https://files.geva.co.il/geva_website/uploads/2024/05/581-%D7%A4%D7%AA%D7%A8%D7%95%D7%A0%D7%95%D7%AA-%D7%A1%D7%95%D7%A4%D7%99%D7%99%D7%9D.pdf",
                "https://files.geva.co.il/geva_website/uploads/2024/05/%D7%A4%D7%AA%D7%A8%D7%9F-%D7%9E%D7%9C%D7%90-35581.pdf",
                "https://files.geva.co.il/geva_website/uploads/2024/05/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-35581.pdf"));

        mathList.add(new QuestionItem("מתמטיקה", "יואל גבע 807 (582)", "בגרות חורף 2024",
                "https://files.geva.co.il/geva_website/uploads/2024/03/35582-%D7%A4%D7%AA%D7%A8%D7%95%D7%A0%D7%95%D7%AA-%D7%A1%D7%95%D7%A4%D7%99%D7%99%D7%9D.pdf",
                "https://files.geva.co.il/geva_website/uploads/2024/03/%D7%A4%D7%AA%D7%A8%D7%9F-%D7%9E%D7%9C%D7%90-35582-1.pdf",
                "https://files.geva.co.il/geva_website/uploads/2024/03/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-35582.pdf"));

        rvMath.setAdapter(new QuestionBillAdapter(mathList, this));

        RecyclerView rvPhysics = findViewById(R.id.rvPhysics);
        rvPhysics.setLayoutManager(new LinearLayoutManager(this));
        List<QuestionItem> physicsList = new ArrayList<>();

        physicsList.add(new QuestionItem("פיזיקה", "מכניקה יואל גבע", "בגרות קיץ 2024",
                "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A4%D7%AA%D7%A8%D7%95%D7%9F-36361-%D7%A7%D7%99%D7%A5-2024-1.pdf",
                "https://www.youtube.com/watch?v=DWPRa7pMIrc",
                "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-36361.pdf"));

        physicsList.add(new QuestionItem("פיזיקה", "חשמל יואל גבע", "בגרות קיץ 2024",
                "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A4%D7%AA%D7%A8%D7%95%D7%9F-36371-%D7%A7%D7%99%D7%A5-2024-2.pdf",
                "https://www.youtube.com/watch?v=e0JPcRm5Mr0",
                "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-36371.pdf"));

        rvPhysics.setAdapter(new QuestionBillAdapter(physicsList, this));

        RecyclerView rvEnglish = findViewById(R.id.rvEnglish);
        rvEnglish.setLayoutManager(new LinearLayoutManager(this));
        List<QuestionItem> englishList = new ArrayList<>();

        englishList.add(new QuestionItem("אנגלית", "English Module G", "בגרות קיץ 2024 מועד א",
                "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A4%D7%AA%D7%A8%D7%95%D7%9F-16582.pdf",
                null,
                "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-16582.pdf"));

        englishList.add(new QuestionItem("אנגלית", "English Module E", "בגרות קיץ 2024 מועד א",
                "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A4%D7%AA%D7%A8%D7%95%D7%9F-16481.pdf",
                null,
                "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-16481.pdf"));

        rvEnglish.setAdapter(new QuestionBillAdapter(englishList, this));
    }
}