package com.example.hokejnetmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class FullArticleActivity extends AppCompatActivity {

    private TextView titleText, authorText, contentText;
    private ImageView articleImage;
    private ImageButton bookmarkButton;
    private SharedPreferences sharedPreferences;
    private String articleTitle, articleContent, articleImageUrl, articleAuthor;
    private boolean isSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_article);

        // Setup Toolbar with back navigation
        Toolbar toolbarFullArticle = findViewById(R.id.toolbarFullArticle);
        setSupportActionBar(toolbarFullArticle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        // Initialize UI components
        titleText = findViewById(R.id.fullTitleText);
        authorText = findViewById(R.id.fullAuthorText);
        contentText = findViewById(R.id.fullContentText);
        articleImage = findViewById(R.id.fullArticleImage);
        bookmarkButton = findViewById(R.id.bookmarkButton);

        // Get data from Intent
        Intent intent = getIntent();
        articleTitle = intent.getStringExtra("article_title");
        articleAuthor = intent.getStringExtra("article_author");
        articleContent = intent.getStringExtra("article_content");
        articleImageUrl = intent.getStringExtra("article_image");

        // Set data to views
        titleText.setText(articleTitle);
        authorText.setText("By " + articleAuthor);
        contentText.setText(articleContent);
        Glide.with(this).load(articleImageUrl).into(articleImage);

        // SharedPreferences for saving articles
        sharedPreferences = getSharedPreferences("SavedArticles", Context.MODE_PRIVATE);
        isSaved = sharedPreferences.contains(articleTitle); // Check if article is saved

        // Set initial bookmark icon
        updateBookmarkIcon();

        // Handle bookmark button click
        bookmarkButton.setOnClickListener(v -> toggleBookmark());
    }

    private void toggleBookmark() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isSaved) {
            editor.remove(articleTitle); // Remove article from saved
            isSaved = false;

            // Notify SavedArticlesActivity that an article was removed
            Intent resultIntent = new Intent();
            resultIntent.putExtra("removed_article_title", articleTitle);
            setResult(RESULT_OK, resultIntent); // Ensure this is called
            Log.d("FullArticleActivity", "Article removed: " + articleTitle);
        } else {
            // Save article title, content, and image URL as a JSON string
            String articleData = "{\"title\":\"" + articleTitle + "\",\"content\":\"" + articleContent + "\",\"imageUrl\":\"" + articleImageUrl + "\"}";
            editor.putString(articleTitle, articleData); // Save article data
            isSaved = true;
        }
        editor.apply();
        updateBookmarkIcon();
    }

    private void updateBookmarkIcon() {
        if (isSaved) {
            bookmarkButton.setImageResource(R.drawable.saved_bookmark); // Use filled bookmark
        } else {
            bookmarkButton.setImageResource(R.drawable.bookmark); // Use outlined bookmark
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Close activity and go back
        return true;
    }
}
