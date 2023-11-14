package com.example.techophile1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    Context context ;
    ArrayList<PostMod> postMods ;

    HashMap<String, Integer> tapCnt = new HashMap<String, Integer>();
    public PostAdapter(Context context, ArrayList<PostMod> postMods) {
        this.context = context;
        this.postMods = postMods;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        PostMod postMod = postMods.get(position);
//        String title , tags , descr , UserId , UserName , UserImg , likes , postID;
//        ArrayList<String> comments;
        holder.piTitle.setText(postMod.getTitle());
        holder.piTags.setText(postMod.getTags());
        holder.piText.setText(postMod.getDescr());
        holder.piName.setText(postMod.getUserName());
        holder.piCnt.setText(postMod.getLikes());
        Picasso.get().load(postMod.getUserImg()).into(holder.piImage);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("posts").child(postMod.getPostID());
        String postID = postMod.getPostID();
        holder.piCmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.piLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapCnt.put(postID, tapCnt.getOrDefault(postID, 0) + 1);
                int add = 1;
                if(tapCnt.get(postID)%2==0) add = -1;
//                Toast.makeText(v.getContext(), ""+tapCnt.get(postID) , Toast.LENGTH_SHORT).show();
                ref.child("likes").setValue(String.valueOf(Integer.parseInt(postMod.getLikes())+add));
                if(add==1) holder.piLike.setColorFilter(Color.argb(255,34,149,248));
                else holder.piLike.clearColorFilter();
            }
        });

    }

    @Override
    public int getItemCount() {
        return postMods.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView piCmnt , piLike ;
        CircleImageView piImage;
        TextView piText,piTags,piTitle,piName,piCnt;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            piCmnt = itemView.findViewById(R.id.piCmnt);
            piLike = itemView.findViewById(R.id.piLike);
            piImage = itemView.findViewById(R.id.piImage);
            piText = itemView.findViewById(R.id.piText);
            piTags = itemView.findViewById(R.id.piTags);
            piTitle = itemView.findViewById(R.id.piTitle);
            piName = itemView.findViewById(R.id.piName);
            piCnt = itemView.findViewById(R.id.piCnt);
        }
    }
}
