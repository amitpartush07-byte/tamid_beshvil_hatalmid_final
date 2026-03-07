package com.example.tamid_beshvil_hatalmid_try1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for the Question Bank list.
 * Designs the cards in a "Baby Pink" bubble style.
 */
public class QuestionBillAdapter extends RecyclerView.Adapter<QuestionBillAdapter.ViewHolder> {

    private final List<QuestionItem> items;
    private final Context context;

    public QuestionBillAdapter(List<QuestionItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the stylish card layout
        View v = LayoutInflater.from(context).inflate(R.layout.item_question_bill, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionItem item = items.get(position);

        // 1. Set Text Data from the QuestionItem model
        holder.subject.setText(item.getSubject());
        holder.book.setText(item.getBookName());
        holder.examNameText.setText(item.getExamName());

        // 2. Full Solution Link (Pink Button)
        holder.btnSolution.setOnClickListener(v -> openUrl(item.getSolutionLink()));

        // 3. Exam Only Link (Yellow Text Button)
        holder.btnExamOnly.setOnClickListener(v -> openUrl(item.getExamLink()));

        // 4. Video Link (Yellow/Soft Pink Button - Hidden if no link exists)
        if (item.getVideoLink() != null && !item.getVideoLink().trim().isEmpty()) {
            holder.btnVideo.setVisibility(View.VISIBLE);
            holder.btnVideo.setOnClickListener(v -> openUrl(item.getVideoLink()));
        } else {
            holder.btnVideo.setVisibility(View.GONE);
        }
    }

    /**
     * Safely opens links in the browser.
     * Prevents crashes by cleaning the URL and using Try-Catch.
     */
    private void openUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            Toast.makeText(context, "קישור לא זמין", Toast.LENGTH_SHORT).show();
            return;
        }

        String fixedUrl = url.trim();
        // Automatically add HTTPS if missing to prevent "Cleartext traffic" security errors
        if (!fixedUrl.startsWith("http://") && !fixedUrl.startsWith("https://")) {
            fixedUrl = "https://" + fixedUrl;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fixedUrl));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "שגיאה בפתיחת הקישור", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * ViewHolder links the Java variables to the IDs in item_question_bill.xml
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, book, examNameText;
        Button btnSolution, btnVideo, btnExamOnly;

        public ViewHolder(View itemView) {
            super(itemView);

            // These IDs must match exactly what is in your item_question_bill.xml
            subject = itemView.findViewById(R.id.subjectText);
            book = itemView.findViewById(R.id.bookDetails);
            examNameText = itemView.findViewById(R.id.locationText);
            btnSolution = itemView.findViewById(R.id.btnSolution);
            btnVideo = itemView.findViewById(R.id.btnVideo);
            btnExamOnly = itemView.findViewById(R.id.btnExamOnly);
        }
    }
}