package com.example.techophile1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Comment_Adapter extends RecyclerView.Adapter<Comment_Adapter.CommentViewHolder>{
    Comments comments;
    ArrayList<Comment_Mod> commentMods;
    public Comment_Adapter(Comments comments, ArrayList<Comment_Mod> commentMods) {
        this.comments = comments;
        this.commentMods = commentMods;
    }

    @NonNull
    @Override
    public Comment_Adapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Comment_Adapter.CommentViewHolder holder, int position) {
        Comment_Mod comment_mod = commentMods.get(position);
        holder.ciUser.setText(comment_mod.getUserName());
        holder.ciText.setText(comment_mod.getComment());
    }

    @Override
    public int getItemCount() {
        return commentMods.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView ciUser , ciText;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ciUser = itemView.findViewById(R.id.ciUser);
            ciText = itemView.findViewById(R.id.ciText);
        }
    }
}
