package com.example.techophile1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserCollection extends RecyclerView.Adapter<UserCollection.ViewHolder> {
    ArrayList<User> users;
    Context context;
    public UserCollection(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserCollection.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCollection.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.user_name.setText(user.getUserName());
        holder.user_teaching.setText(user.getTeaching());
        Picasso.get().load(user.getProfilePic()).into(holder.user_pic);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,chat_page.class);
                intent.putExtra("useriName",user.getUserName());
                intent.putExtra("useriId",user.getUserID());
                intent.putExtra("useriPic",user.getProfilePic());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView user_pic;
        TextView user_teaching,user_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_pic = itemView.findViewById(R.id.user_pic);
            user_teaching = itemView.findViewById(R.id.user_teaching);
            user_name = itemView.findViewById(R.id.user_name);

        }
    }
}
