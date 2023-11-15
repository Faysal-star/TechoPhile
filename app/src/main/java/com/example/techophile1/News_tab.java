package com.example.techophile1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class News_tab extends Fragment {
    RecyclerView newsS ;
    NewsAdapter newsAdapter ;
    ArrayList<NewsMod> newsModArray ;
    private String NEWS_API_KEY = BuildConfig.NEWS_API_KEY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_tab, container, false);

        newsS = view.findViewById(R.id.newsS);
        newsS.setLayoutManager(new LinearLayoutManager(getContext()));
        newsModArray = new ArrayList<>();
        newsAdapter = new NewsAdapter(getContext(),newsModArray);
        newsS.setAdapter(newsAdapter);
        callNews();
        return view;
    }

    public void callNews(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://newsapi.org/v2/top-headlines?country=us&category=technology&pageSize=20&apiKey="+NEWS_API_KEY)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    String res = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        JSONArray jsonArray = jsonObject.getJSONArray("articles");
                        for(int i = 0 ; i < jsonArray.length() ; i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String title = jsonObject1.getString("title");
                            String desc = jsonObject1.getString("description");
                            String url = jsonObject1.getString("url");
                            String urlImg = jsonObject1.getString("urlToImage");
                            String date = jsonObject1.getString("publishedAt");
                            newsModArray.add(new NewsMod(title,desc,url,urlImg,date));
                        }
                        getActivity().runOnUiThread(() -> newsAdapter.notifyDataSetChanged());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}