package com.example.hokejnetmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;



public class FullArticleActivity extends AppCompatActivity {
    private TextView titleText, authorText, contentText;
    private ImageView articleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_article);

        // Toolbar for back to the home page
        Toolbar toolbarFullArticle = findViewById(R.id.toolbarFullArticle);
        setSupportActionBar(toolbarFullArticle);

        // Enable back button in the toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        titleText = findViewById(R.id.fullTitleText);
        authorText = findViewById(R.id.fullAuthorText);
        contentText = findViewById(R.id.fullContentText);
        articleImage = findViewById(R.id.fullArticleImage);

        // Get data from Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("article_title");
        String author = intent.getStringExtra("article_author");
        String content = intent.getStringExtra("article_content");
        String imageUrl = intent.getStringExtra("article_image");

        // Set data to views
        titleText.setText(title);
        authorText.setText("By " + author);
        contentText.setText(content);
        Glide.with(this).load(imageUrl).into(articleImage);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Close activity and go back
        return true;
    }
}
