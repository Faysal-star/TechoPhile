package com.example.techophile1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class chat_page extends AppCompatActivity {
    String useriName, recUid, useriPic, senderUid;
    TextView receiverName ;
    ImageView infoBtn,backBtn;
    CardView sendBtn;
    EditText msgBox;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    RecyclerView msgList ;
    String senderNode , recNode ;
    ArrayList<MsgMod> msgModArray ;
    MsgAdapter msgAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        useriName = getIntent().getStringExtra("useriName");
        recUid = getIntent().getStringExtra("useriId");
        useriPic = getIntent().getStringExtra("useriPic");

        receiverName = findViewById(R.id.receiverName);
        sendBtn = findViewById(R.id.sendBtn);
        infoBtn = findViewById(R.id.infoBtn);
        backBtn = findViewById(R.id.backBtn);
        msgBox = findViewById(R.id.msgBox);
        msgList = findViewById(R.id.msgList);
        msgModArray = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        msgList.setLayoutManager(layoutManager);
        msgAdapter = new MsgAdapter(chat_page.this,msgModArray);
        msgList.setAdapter(msgAdapter);

        receiverName.setText(useriName);

        mAuth = FirebaseAuth.getInstance();
        senderUid = mAuth.getCurrentUser().getUid();

        senderNode = senderUid + recUid ;
        recNode = recUid + senderUid ;

        database = FirebaseDatabase.getInstance();
        DatabaseReference chatRef = database.getReference().child("chats").child(senderNode).child("messages");

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msgModArray.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MsgMod msgMod = dataSnapshot.getValue(MsgMod.class);
                    msgModArray.add(msgMod);
//                    Toast.makeText(chat_page.this, msgMod.getMsg(), Toast.LENGTH_SHORT).show();
                }
                msgAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sendBtn.setOnClickListener(v -> {
            String msg = msgBox.getText().toString();
            if(msg.isEmpty()){
                Toast.makeText(this, "Empty Msg", Toast.LENGTH_SHORT).show();
            }
            else{
                msgBox.setText("");
                Date date = new Date();
                MsgMod msgMod = new MsgMod(msg, senderUid,date.getTime());
                database = FirebaseDatabase.getInstance();
                database.getReference()
                        .child("chats")
                        .child(senderNode)
                        .child("messages")
                        .push().setValue(msgMod).addOnCompleteListener(task -> {
                            database.getReference()
                                    .child("chats")
                                    .child(recNode)
                                    .child("messages")
                                    .push().setValue(msgMod).addOnCompleteListener(task1 -> {});
                        });
            }
        });
    }
}