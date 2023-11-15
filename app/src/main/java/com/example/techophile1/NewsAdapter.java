package com.example.techophile1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    Context context;
    ArrayList<NewsMod> newsModArray;
    public NewsAdapter(Context context, ArrayList<NewsMod> newsModArray) {
        this.context = context;
        this.newsModArray = newsModArray;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        NewsMod newsMod = newsModArray.get(position);
        holder.nTitle.setText(newsMod.getTitle());
        holder.nTime.setText(newsMod.getDate());
        Picasso.get().load(newsMod.getUrlImg()).into(holder.nImg);
        holder.nLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context , News_web.class);
            intent.putExtra("url",newsMod.getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsModArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView nImg ;
        TextView nTitle , nTime ;
        LinearLayout nLayout ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nImg = itemView.findViewById(R.id.nImg);
            nTitle = itemView.findViewById(R.id.nTitle);
            nTime = itemView.findViewById(R.id.nTime);
            nLayout = itemView.findViewById(R.id.nLayout);
        }
    }
}
