package com.example.techophile1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comments extends AppCompatActivity {
    TextInputLayout cCmnt;
    ImageView cSend;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String UserName ;
    RecyclerView cmRecycler;
    Comment_Adapter comment_adapter;
    ArrayList<Comment_Mod> comment_mods = new ArrayList<>();
    String postID;
    CircleImageView cpiImage ;
    TextView cpiText,cpiTags,cpiTitle,cpiTime,cpiName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        postID = getIntent().getStringExtra("postID");
        cCmnt = findViewById(R.id.cCmnt);
        cSend = findViewById(R.id.cSend);
        cmRecycler = findViewById(R.id.cmRecycler);
        cmRecycler.setLayoutManager(new LinearLayoutManager(this));
        comment_adapter = new Comment_Adapter(this,comment_mods);
        cmRecycler.setAdapter(comment_adapter);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String userRef = mAuth.getUid();

        database.getReference().child("Users").child(userRef).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                UserName = user.getUserName();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Comment_Mod(String comment, String userName, String userId)
        cSend.setOnClickListener(v -> {
            String cmnt = cCmnt.getEditText().getText().toString();
            if(cmnt.isEmpty()){
                cCmnt.setError("Comment is required");
                return;
            }
            String time = String.valueOf(System.currentTimeMillis());
            Comment_Mod comment_mod = new Comment_Mod(cmnt,UserName,userRef);
            database.getReference().child("comments").child(postID).child(time).setValue(comment_mod);
            cCmnt.getEditText().setText("");
        });

        database.getReference().child("comments").child(postID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comment_mods.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Comment_Mod comment_mod = dataSnapshot.getValue(Comment_Mod.class);
                    comment_mods.add(comment_mod);
                }
                comment_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Post Load
        cpiImage = findViewById(R.id.cpiImage);
        cpiText = findViewById(R.id.cpiText);
        cpiTags = findViewById(R.id.cpiTags);
        cpiTitle = findViewById(R.id.cpiTitle);
        cpiTime = findViewById(R.id.cpiTime);
        cpiName = findViewById(R.id.cpiName);

        database.getReference().child("posts").child(postID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostMod postMod = snapshot.getValue(PostMod.class);
                cpiText.setText(postMod.getDescr());
                cpiTags.setText(postMod.getTags());
                cpiTitle.setText(postMod.getTitle());
                cpiName.setText(postMod.getUserName());
                Date date = new Date(Long.parseLong(postID));
                SimpleDateFormat df2 = new SimpleDateFormat("EEE dd MMM,yyyy HH:mm");
                String dateText = df2.format(date);
                cpiTime.setText(dateText);
                Picasso.get().load(postMod.getUserImg()).into(cpiImage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}