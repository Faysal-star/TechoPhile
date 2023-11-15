package com.example.techophile1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class Post_tab extends Fragment {
    private TextInputLayout pTitle,pTags,pDescr;
    private Button pPost ;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private String UserName , UserImg , UID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_tab, container, false);

        // hooks
        pTitle = view.findViewById(R.id.pTitle);
        pTags = view.findViewById(R.id.pTags);
        pDescr = view.findViewById(R.id.pDescr);
        pPost = view.findViewById(R.id.pPost);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        UID = mAuth.getCurrentUser().getUid();

        database.getReference().child("Users").child(UID).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                UserName = user.getUserName();
                UserImg = user.getProfilePic();
            }
        });

        pPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = pTitle.getEditText().getText().toString();
                String tags = pTags.getEditText().getText().toString();
                String descr = pDescr.getEditText().getText().toString();
                String time = String.valueOf(System.currentTimeMillis());
                if(title.isEmpty()){
                    pTitle.setError("Title is required");
                    return;
                }
                if(tags.isEmpty()){
                    pTags.setError("Tags are required");
                    return;
                }
                if(descr.isEmpty()){
                    pDescr.setError("Description is required");
                    return;
                }
//                public PostMod(String title, String tags, String descr, String userId, String userName, String userImg, String likes, String postID, String comments)
                PostMod postMod = new PostMod(title,tags,descr,UID,UserName,UserImg,"0",time);
                database.getReference().child("posts").child(time).setValue(postMod).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(), "Post Added", Toast.LENGTH_SHORT).show();
                        pTitle.getEditText().setText("");
                        pTags.getEditText().setText("");
                        pDescr.getEditText().setText("");
                    }
                    else{
                        Toast.makeText(getContext(), "Error : "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}