package com.example.tamid_beshvil_hatalmid_try1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionBillActivity extends AppCompatActivity implements QuestionBillAdapter.OnDeleteClickListener {

    private List<QuestionItem> mathList, physicsList, englishList;
    private QuestionBillAdapter mathAdapter, physicsAdapter, englishAdapter;
    private ActivityResultLauncher<Intent> addLauncher;
    private DatabaseReference dbRef;
    private boolean isUserTeacher = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bill);

        dbRef = FirebaseDatabase.getInstance().getReference("QuestionBank");

        setupLauncher();
        setupToolbarLogic();
        checkTeacher();
    }

    private void setupLauncher() {
        addLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Intent data = result.getData();
                String uid = FirebaseAuth.getInstance().getUid();

                QuestionItem newItem = new QuestionItem(
                        data.getStringExtra("subject"),
                        data.getStringExtra("title"),
                        data.getStringExtra("desc"),
                        data.getStringExtra("solve"),
                        data.getStringExtra("video"),
                        data.getStringExtra("exam"),
                        uid
                );
                dbRef.push().setValue(newItem);
            }
        });
    }

    private void loadFirebaseData() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resetToHardcoded(); // Re-adds your original 6 questions

                for (DataSnapshot ds : snapshot.getChildren()) {
                    QuestionItem item = ds.getValue(QuestionItem.class);
                    if (item != null) {
                        item.setKey(ds.getKey()); // Store key for removal

                        String sub = item.getSubject();
                        if ("מתמטיקה".equals(sub)) mathList.add(item);
                        else if ("פיזיקה".equals(sub)) physicsList.add(item);
                        else if ("אנגלית".equals(sub)) englishList.add(item);
                    }
                }
                refreshAdapters();
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void refreshAdapters() {
        mathAdapter.notifyDataSetChanged();
        physicsAdapter.notifyDataSetChanged();
        englishAdapter.notifyDataSetChanged();
    }

    // --- DELETE LOGIC ---
    @Override
    public void onDeleteClick(QuestionItem item) {
        new AlertDialog.Builder(this)
                .setTitle("מחיקת שאלה")
                .setMessage("האם אתה בטוח שברצונך למחוק שאלה זו לצמיתות?")
                .setPositiveButton("כן, מחק", (dialog, which) -> {
                    if (item.getKey() != null) {
                        dbRef.child(item.getKey()).removeValue()
                                .addOnSuccessListener(aVoid -> Toast.makeText(this, "השאלה נמחקה בהצלחה", Toast.LENGTH_SHORT).show());
                    }
                })
                .setNegativeButton("ביטול", null)
                .show();
    }

    private void resetToHardcoded() {
        mathList.clear(); physicsList.clear(); englishList.clear();
        String sys = "system";

        // Restore MATH
        mathList.add(new QuestionItem("מתמטיקה", "יואל גבע 806", "בגרות קיץ 2024", "https://files.geva.co.il/geva_website/uploads/2024/05/%D7%A4%D7%AA%D7%A8%D7%95%D7%9F-%D7%9E%D7%9C%D7%90-35581.pdf", "https://www.youtube.com/watch?v=x52jbGYK7xs", "https://files.geva.co.il/geva_website/uploads/2024/05/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-35581.pdf", sys));
        mathList.add(new QuestionItem("מתמטיקה", "יואל גבע 807", "בגרות חורף 2024", "https://files.geva.co.il/geva_website/uploads/2024/03/35582-%D7%A4%D7%AA%D7%A8%D7%95%D7%9F-%D7%9E%D7%9C%D7%90-35582-1.pdf", "https://files.geva.co.il/geva_website/uploads/2024/03/%D7%A4%D7%AA%D7%A8%D7%9F-%D7%9E%D7%9C%D7%90-35582-1.pdf", "https://files.geva.co.il/geva_website/uploads/2024/03/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-35582.pdf", sys));

        // Restore PHYSICS
        physicsList.add(new QuestionItem("פיזיקה", "מכניקה יואל גבע", "בגרות קיץ 2024", "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A4%D7%AA%D7%A8%D7%95%D7%9F-36361-%D7%A7%D7%99%D7%A5-2024-1.pdf", "https://www.youtube.com/watch?v=DWPRa7pMIrc", "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-36361.pdf", sys));
        physicsList.add(new QuestionItem("פיזיקה", "חשמל יואל גבע", "בגרות קיץ 2024", "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A4%D7%AA%D7%A8%D7%95%D7%9F-36371-%D7%A7%D7%99%D7%A5-2024-2.pdf", "https://www.youtube.com/watch?v=e0JPcRm5Mr0", "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-36371.pdf", sys));

        // Restore ENGLISH
        englishList.add(new QuestionItem("אנגלית", "Module G", "Summer 2024 A", "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A4%D7%AA%D7%A8%D7%95%D7%9F-16582.pdf", null, "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-16582.pdf", sys));
        englishList.add(new QuestionItem("אנגלית", "Module E", "Summer 2024 A", "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A4%D7%AA%D7%A8%D7%95%D7%9F-16481.pdf", null, "https://files.geva.co.il/geva_website/uploads/2024/06/%D7%A9%D7%90%D7%9C%D7%95%D7%9F-16481.pdf", sys));
    }

    private void checkTeacher() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid != null) {
            FirebaseDatabase.getInstance().getReference("Teachers").child(uid).get().addOnSuccessListener(snapshot -> {
                if (snapshot.exists()) {
                    isUserTeacher = true; // User is a teacher
                    MenuItem plus = ((MaterialToolbar)findViewById(R.id.topAppBar)).getMenu().findItem(R.id.action_add_question);
                    if (plus != null) plus.setVisible(true);
                }
                initLists(); // Initialize lists with the isTeacher info
                loadFirebaseData(); // Fetch the cloud data
            });
        }
    }

    private void initLists() {
        mathList = new ArrayList<>(); physicsList = new ArrayList<>(); englishList = new ArrayList<>();
        mathAdapter = new QuestionBillAdapter(mathList, this, isUserTeacher, this);
        physicsAdapter = new QuestionBillAdapter(physicsList, this, isUserTeacher, this);
        englishAdapter = new QuestionBillAdapter(englishList, this, isUserTeacher, this);

        RecyclerView rvMath = findViewById(R.id.rvMath);
        rvMath.setLayoutManager(new LinearLayoutManager(this));
        rvMath.setAdapter(mathAdapter);

        RecyclerView rvPhysics = findViewById(R.id.rvPhysics);
        rvPhysics.setLayoutManager(new LinearLayoutManager(this));
        rvPhysics.setAdapter(physicsAdapter);

        RecyclerView rvEnglish = findViewById(R.id.rvEnglish);
        rvEnglish.setLayoutManager(new LinearLayoutManager(this));
        rvEnglish.setAdapter(englishAdapter);
    }


        private void setupToolbarLogic() {
            MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
            topAppBar.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_add_question) {
                    // FIX: Use the launcher instead of startActivity
                    addLauncher.launch(new Intent(this, AddQuestionActivity.class));
                    return true;
                }
             else if (id == R.id.action_home) {
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