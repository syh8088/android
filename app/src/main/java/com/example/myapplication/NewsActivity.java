package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] myDataset = {"1", "2"};
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        queue = Volley.newRequestQueue(this);
        getNews();
        // 1. 화면이 로딩 -> 뉴스 정보를 받아온다.
        // 2. 정보 -> 아뎁터 넘겨준다
        // 3. 아뎁터 -> 셋팅
    }

    public void getNews() {
        // Instantiate the RequestQueue.

        String url ="https://newsapi.org/v2/top-headlines?country=kr&apiKey=4fe90bbb8abe48579329d6bf6ff224c6";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("NEWS", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArrayArticles = jsonObject.getJSONArray("articles");

                            List<NewsData> newsDataList = new ArrayList<>();

                            for (int i = 0, j = jsonArrayArticles.length(); i < j; j++) {
                                JSONObject object = jsonArrayArticles.getJSONObject(i);

                                Log.d("object", object.toString());

                                NewsData newsData = new NewsData();
                                newsData.setTitle(object.getString("title"));
                                newsData.setUrlToImage(object.getString("urlToImage"));
                                newsData.setContent(object.getString("content"));
                                newsDataList.add(newsData);
                            }

                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(newsDataList, NewsActivity.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(view.getTag() != null) {
                                        int position = (int) view.getTag();
                                        ((MyAdapter) mAdapter).getNews(position);
                                        // Intent intent = new Intent();
                                    }
                                }
                            });
                            recyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
