package com.example.staysafeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class FragmentNews extends Fragment {
    private List<NewsItem> newsItemList;

    ListView listNews;
    private static final String NEWS_URL = "http://newsapi.org/v2/top-headlines?country=id&apiKey=11fd2ded3ce84eafb92243fe4a027693";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        listNews = view.findViewById(R.id.listNews);

        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem newsItem = newsItemList.get(position);
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse(newsItem.getUrl()));
                startActivity(openURL);
            }
        });

        newsItemList = new ArrayList<>();

        loadNews();

        return view;
    }

    private void loadNews() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, NEWS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray newsArray = jsonObject.getJSONArray("articles");

                            for (int i = 0; i < newsArray.length(); i++) {

                                JSONObject jsonPost = newsArray.getJSONObject(i);
                                NewsItem newsItem = new NewsItem(
                                        jsonPost.getString("title"),
                                        jsonPost.getString("urlToImage"),
                                        jsonPost.getString("url"),
                                        jsonPost.getString("description"));

                                newsItemList.add(newsItem);

                                AdapterNews adapter = new AdapterNews(newsItemList, getActivity());

                                listNews.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
