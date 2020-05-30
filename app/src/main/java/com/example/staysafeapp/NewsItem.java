package com.example.staysafeapp;

import java.io.Serializable;

public class NewsItem implements Serializable {
    String title, urlToImage, url, description;

    public NewsItem(String title, String urlToImage, String url, String description) {
        this.title = title;
        this.urlToImage = urlToImage;
        this.url = url;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription(){
        return description;
    }

}
