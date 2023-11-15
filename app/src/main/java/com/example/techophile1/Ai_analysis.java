package com.example.techophile1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Ai_analysis extends AppCompatActivity {
    TextView aAnalysis ;
    Button aBtn ;
    private String API_KEY = BuildConfig.API_KEY;
    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_analysis);

        aAnalysis = findViewById(R.id.aAnalysis);
        aBtn = findViewById(R.id.aBtn);
        String total_convo = getIntent().getStringExtra("total_convo");

        String prompt = "Given a conversation in the form of:\n" +
                "Student: <student's asking>\n" +
                "Teacher: <teacher's response>\n" +
                "Take on the role of Judge and give a formatted analysis of the conversation as below.\n" +
                "Topic: <Title>\n" +
                "Analysis: <Summary>\n" +
                "Improvement: <Improvement>\n" +
                "Mislead: <Misinformation>\n" +
                "Performance: <Performance>\n" +
                "\n" +
                "Example:\n" +
                "Topic: \"C++ Pointer\"\n" +
                "Analysis: <Summary of the conversation>\n" +
                "Improvement: <Improvement>\n" +
                "Mislead: \"The conversation seems accurate\"\n" +
                "Performance: <Performance>\n" +
                "\n" +
                "You must follow the Rules:\n" +
                "1. The output is not wrapped in any other text.Insert two new lines between each part and topic.\n" +
                "2. The Topic is at least 5 words and at most 15 words.\n" +
                "3. The Analysis should be as detailed as needed to make the analysis comprehensive.\n" +
                "4. Indicate which additional topics could be covered to make this conversation more knowledgeable and what knowledge the student is missing in the improvement section.Make recommendations for the teacher and the student.\n" +
                "5. If any misinformation found, provide the correct info otherwise complement the teacher.\n" +
                "6. In the Performance section conduct a two-line analysis of the student's and teacher's performance. as well as the student's comprehension of the subject and the teacher's effectiveness.\n" +
                "Conversation:" + total_convo;

        prompt = prompt.replace("\n"," ");
        String finalPrompt = prompt;
        aBtn.setOnClickListener(v -> {
            aAnalysis.setText("Analyzing....");
            callGpt(finalPrompt);
        });
    }

    private void callGpt(String prompt) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model","gpt-3.5-turbo-instruct");
            jsonObject.put("prompt",prompt);
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
            public void onFailure(@NonNull Call call, @NonNull java.io.IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws java.io.IOException {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    String res = response.body().string();
                    try {
                        JSONObject jsonObject1 = new JSONObject(res);
                        String text = jsonObject1.getJSONArray("choices").getJSONObject(0).getString("text");
                        runOnUiThread(() -> {
                            Toast.makeText(Ai_analysis.this, "got it", Toast.LENGTH_SHORT).show();
                            aAnalysis.setText(text);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}