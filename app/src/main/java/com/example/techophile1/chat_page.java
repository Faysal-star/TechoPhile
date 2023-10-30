package com.example.techophile1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class chat_page extends AppCompatActivity {
    String useriName,useriId,useriPic,myUID;
    TextView receiverName ;
    ImageView infoBtn,backBtn;
    CardView sendBtn;
    EditText msgBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        useriName = getIntent().getStringExtra("useriName");
        useriId = getIntent().getStringExtra("useriId");
        useriPic = getIntent().getStringExtra("useriPic");

        receiverName = findViewById(R.id.receiverName);
        sendBtn = findViewById(R.id.sendBtn);
        infoBtn = findViewById(R.id.infoBtn);
        backBtn = findViewById(R.id.backBtn);
        msgBox = findViewById(R.id.msgBox);

        receiverName.setText(useriName);

        sendBtn.setOnClickListener(v -> {
            String msg = msgBox.getText().toString();
            if(!msg.isEmpty()){

            }
            else{
                Toast.makeText(this, "Empty Msg", Toast.LENGTH_SHORT).show();
            }
        });
    }
}