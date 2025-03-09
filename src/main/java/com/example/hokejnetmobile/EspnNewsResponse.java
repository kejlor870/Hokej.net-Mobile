package com.example.hokejnetmobile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EspnNewsResponse {
    @SerializedName("articles")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}