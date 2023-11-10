package com.example.techophile1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.textclassifier.TextLinks;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Ai_chat_window extends AppCompatActivity {
    CardView AisendBtn ;
    EditText AimsgBox ;
    RecyclerView AimsgList ;
    ArrayList<AiMsgMod> msgModArray ;
    AiMsgAdapter msgAdapter ;
    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();
    private String API_KEY = BuildConfig.API_KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chat_window);

        AisendBtn = findViewById(R.id.AisendBtn);
        AimsgBox = findViewById(R.id.AimsgBox);
        AimsgList = findViewById(R.id.AimsgList);
        msgModArray = new ArrayList<>();

        msgAdapter = new AiMsgAdapter(msgModArray);
        AimsgList.setAdapter(msgAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        AimsgList.setLayoutManager(layoutManager);


        AisendBtn.setOnClickListener(v -> {
            String msg = AimsgBox.getText().toString();
            if(msg.isEmpty()){
                Toast.makeText(this, "Empty Question", Toast.LENGTH_SHORT).show();
                return;
            }
            AimsgBox.setText("");
            //send msg
            addMsg(msg.trim(),AiMsgMod.USER);
            callGPT(msg.trim());
        });
    }

    void addMsg(String msg , String sentBy){
        runOnUiThread(() -> {
            msgModArray.add(new AiMsgMod(msg,sentBy));
            msgAdapter.notifyDataSetChanged();
            AimsgList.smoothScrollToPosition(msgModArray.size()-1);
        });
    }

    void callGPT(String msg){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model","gpt-3.5-turbo-instruct");
            jsonObject.put("prompt",msg);
            jsonObject.put("max_tokens",1000);
            jsonObject.put("temperature",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer "+API_KEY)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addMsg("Error : "+e.getMessage(),AiMsgMod.AI);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    String res = response.body().string();
                    try {
                        JSONObject jsonObject1 = new JSONObject(res);
                        String text = jsonObject1.getJSONArray("choices").getJSONObject(0).getString("text");
                        addMsg(text.trim(),AiMsgMod.AI);
                    } catch (JSONException e) {
                        addMsg("Error : "+e.getMessage(),AiMsgMod.AI);
                    }
                }else{
                    addMsg("Error : "+response.body().string(),AiMsgMod.AI);
                }
            }
        });
    }
}