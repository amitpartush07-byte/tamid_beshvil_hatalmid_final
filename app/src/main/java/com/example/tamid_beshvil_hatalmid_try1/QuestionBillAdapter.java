package com.example.tamid_beshvil_hatalmid_try1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class QuestionBillAdapter extends RecyclerView.Adapter<QuestionBillAdapter.ViewHolder> {

    private List<QuestionItem> items;
    private Context context;
    private boolean isTeacher;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(QuestionItem item);
    }

    public QuestionBillAdapter(List<QuestionItem> items, Context context, boolean isTeacher, OnDeleteClickListener listener) {
        this.items = items;
        this.context = context;
        this.isTeacher = isTeacher;
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_question_bill, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionItem item = items.get(position);

        holder.subject.setText(item.getSubject());
        holder.book.setText(item.getDesc()); // Changed from getBookName() to getDesc()
        holder.examNameText.setText(item.getExamName());

        if (isTeacher && !"system".equals(item.getOwnerId())) {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnDelete.setOnClickListener(v -> deleteListener.onDeleteClick(item));
        } else {
            holder.btnDelete.setVisibility(View.GONE);
        }

        holder.btnSolution.setOnClickListener(v -> openUrl(item.getSolutionLink()));
        holder.btnExamOnly.setOnClickListener(v -> openUrl(item.getExamLink()));

        if (item.getVideoLink() != null && !item.getVideoLink().isEmpty()) {
            holder.btnVideo.setVisibility(View.VISIBLE);
            holder.btnVideo.setOnClickListener(v -> openUrl(item.getVideoLink()));
        } else {
            holder.btnVideo.setVisibility(View.GONE);
        }
    }

    private void openUrl(String url) {
        if (url == null || url.isEmpty()) return;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() { return items.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, book, examNameText;
        Button btnSolution, btnVideo, btnExamOnly;
        ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subjectText);
            book = itemView.findViewById(R.id.bookDetails);
            examNameText = itemView.findViewById(R.id.locationText);
            btnSolution = itemView.findViewById(R.id.btnSolution);
            btnVideo = itemView.findViewById(R.id.btnVideo);
            btnExamOnly = itemView.findViewById(R.id.btnExamOnly);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
