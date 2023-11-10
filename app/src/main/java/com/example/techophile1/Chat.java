package com.example.techophile1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class Chat extends Fragment {
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    RecyclerView chatRecycler;
    FirebaseDatabase database;
    UserCollection coll ;
    ArrayList<User> users;
    LinearLayout aiChat ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        users = new ArrayList<>();
        DatabaseReference ref = database.getReference().child("Users");
        chatRecycler = view.findViewById(R.id.chatRecycler);
        chatRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        coll = new UserCollection(users,getContext());
        chatRecycler.setAdapter(coll);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    User user = snap.getValue(User.class);
                    assert user != null;
                    if(!user.getUserID().equals(mAuth.getUid())) users.add(user);
                }
                coll.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        aiChat = view.findViewById(R.id.aiChat);
        aiChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Ai_chat_window.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}