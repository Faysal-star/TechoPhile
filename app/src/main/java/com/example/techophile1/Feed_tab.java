package com.example.techophile1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_tab, container, false);

        posts = view.findViewById(R.id.posts);
        postMods = new ArrayList<>();
        database = FirebaseDatabase.getInstance();

        posts.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter(getContext(),postMods);
        posts.setAdapter(postAdapter);

        DatabaseReference ref = database.getReference().child("posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postMods.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PostMod postMod = dataSnapshot.getValue(PostMod.class);
                    postMods.add(postMod);
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