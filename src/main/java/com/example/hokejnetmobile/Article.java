package com.example.hokejnetmobile;

public class Article {
    private String author, title, timestamp;
    private int imageResId;

    public Article(String author, String title, String timestamp, int imageResId) {
        this.author = author;
        this.title = title;
        this.timestamp = timestamp;
        this.imageResId = imageResId;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getImageResId() {
        return imageResId;
    }
}
