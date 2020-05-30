package com.example.staysafeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterNews extends ArrayAdapter<NewsItem> {

    private List<NewsItem> NewsItemList;

    private Context context;

    public AdapterNews(List<NewsItem> newsItemList, Context context) {
        super(context, R.layout.list_news, newsItemList);
        this.NewsItemList = newsItemList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View listViewItem = inflater.inflate(R.layout.list_news, null, true);

        TextView textTitle    = listViewItem.findViewById(R.id.txtTitle);
        TextView textDesc     = listViewItem.findViewById(R.id.txtDesc);
        ImageView textImageToUrl = listViewItem.findViewById(R.id.txtImageToUrl);

        NewsItem newsItem = NewsItemList.get(position);

        textTitle.setText(newsItem.getTitle());
        textDesc.setText(newsItem.getDescription());

        String path = newsItem.getUrlToImage();
        Picasso.get().load(path).into(textImageToUrl);

        return listViewItem;
    }
}