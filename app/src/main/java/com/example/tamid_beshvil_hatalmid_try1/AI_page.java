package com.example.tamid_beshvil_hatalmid_try1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AI_page extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText messageInput;
    private FloatingActionButton sendButton;
    private MaterialToolbar topAppBar;

    private List<ChatMessage> messageList;
    private ChatAdapter adapter;
    private GeminiManager geminiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_page);

        recyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        topAppBar = findViewById(R.id.topAppBar);

        geminiManager = GeminiManager.getInstance();
        messageList = new ArrayList<>();
        adapter = new ChatAdapter(messageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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


        sendButton.setOnClickListener(v -> {
            String text = messageInput.getText().toString().trim();
            if (!text.isEmpty()) {
                addMessage(text, true);
                messageInput.setText("");
                askGemini(text);
            }
        });

        messageInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && messageList != null && messageList.size() > 0) {
                recyclerView.postDelayed(() -> {
                    recyclerView.smoothScrollToPosition(messageList.size() - 1);
                }, 200);
            }
        });

    }

    private void addMessage(String text, boolean isUser) {
        runOnUiThread(() -> {
            messageList.add(new ChatMessage(text, isUser));
            adapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.smoothScrollToPosition(messageList.size() - 1);
        });
    }

    private void askGemini(String prompt) {
        Content content = new Content.Builder().addText(prompt).build();
        ListenableFuture<GenerateContentResponse> response =
                geminiManager.getModel().generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                addMessage(result.getText(), false);
            }

            @Override
            public void onFailure(Throwable t) {
                addMessage("שגיאה: " + t.getMessage(), false);
            }
        }, this.getMainExecutor());
    }

    private class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
        private List<ChatMessage> list;

        public ChatAdapter(List<ChatMessage> list) { this.list = list; }

        @NonNull
        @Override
        public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ChatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
            ChatMessage msg = list.get(position);
            String prefix = msg.isUser ? "אני: " : "מורה חכם: ";
            holder.textView.setText(prefix + msg.text);

            holder.textView.setTextColor(msg.isUser ? 0xFFF48FB1 : 0xFF333333);
        }

        @Override
        public int getItemCount() { return list.size(); }

        class ChatViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            public ChatViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }

    private static class ChatMessage {
        String text;
        boolean isUser;
        ChatMessage(String text, boolean isUser) {
            this.text = text;
            this.isUser = isUser;
        }
    }
}