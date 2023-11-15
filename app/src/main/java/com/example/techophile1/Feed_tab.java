package com.example.techophile1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Feed_tab extends Fragment {
    RecyclerView posts;
    PostAdapter postAdapter;
    ArrayList<PostMod> postMods;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String learn_teach = "";
    String[] learn_teachArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_tab, container, false);

        posts = view.findViewById(R.id.posts);
        postMods = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("posts");
        auth = FirebaseAuth.getInstance();

        posts.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter(getContext(),postMods);
        posts.setAdapter(postAdapter);
        String userRef = auth.getUid();
        assert userRef != null;
        database.getReference().child("Users").child(userRef).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String learning = user.getLearning();
                String teaching = user.getTeaching();
                learn_teach = learning + "," + teaching;
                learn_teachArray = learn_teach.split(",");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postMods.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PostMod postMod = dataSnapshot.getValue(PostMod.class);
                    if(learn_teachArray == null){
                        return;
                    }
                    for(String s : learn_teachArray){
                        s = s.toUpperCase() ;
                        if(postMod.getTags().toUpperCase().contains(s)){
                            postMods.add(postMod);
                            break ;
                        }
                    }
                }
                Collections.reverse(postMods);
                postAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}