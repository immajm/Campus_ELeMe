package com.example.eleme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.R;
import com.example.eleme.data.OneComment;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List commentsList;

    class CommentsViewHolder extends RecyclerView.ViewHolder {
        TextView commentsUser;
        TextView commentsContent;
        TextView star;
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            commentsUser=(TextView) itemView.findViewById(R.id.comment_user);
            commentsContent=(TextView) itemView.findViewById(R.id.comment_content);
            star=(TextView) itemView.findViewById(R.id.comment_star);
        }


    }

    public CommentsAdapter(List<Object> l) {
        commentsList = l;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onecomment, parent, false);
        final CommentsViewHolder holder = new CommentsViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        OneComment c= (OneComment) commentsList.get(position);
        ((CommentsViewHolder) holder).commentsUser.setText(c.getCustomerName());
        ((CommentsViewHolder) holder).commentsContent.setText(c.getCommentContent());
        ((CommentsViewHolder) holder).star.setText(c.getStar()+"");

    }
    @Override
    public int getItemCount() {
        return commentsList.size();
    }

}
