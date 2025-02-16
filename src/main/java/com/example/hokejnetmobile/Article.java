package com.example.hokejnetmobile;

public class Article {
    private String title;
    private String author;
    private String content;
    private String imageUrl;

    public Article(String title, String author, String content, String imageUrl) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

