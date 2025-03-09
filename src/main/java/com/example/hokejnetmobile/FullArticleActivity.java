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

        // Setup Toolbar z nawigacją wstecz
        Toolbar toolbarFullArticle = findViewById(R.id.toolbarFullArticle);
        setSupportActionBar(toolbarFullArticle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        // Inicjalizacja komponentów UI
        titleText = findViewById(R.id.fullTitleText);
        authorText = findViewById(R.id.fullAuthorText);
        contentText = findViewById(R.id.fullContentText);
        articleImage = findViewById(R.id.fullArticleImage);
        bookmarkButton = findViewById(R.id.bookmarkButton);

        // Pobranie danych z Intent
        Intent intent = getIntent();
        articleTitle = intent.getStringExtra("article_title");
        articleAuthor = intent.getStringExtra("article_author");
        articleContent = intent.getStringExtra("article_content");
        articleImageUrl = intent.getStringExtra("article_image");

        // Ustawienie danych w widokach
        titleText.setText(articleTitle);
        authorText.setText("By " + articleAuthor);
//        contentText.setText(articleContent);

        String moreContent = "";
        for(int i=0; i<10; i++){
            moreContent += articleContent + " ";
        }
        contentText.setText(moreContent);

        Glide.with(this).load(articleImageUrl).into(articleImage);

        // SharedPreferences do zapisywania artykułów
        sharedPreferences = getSharedPreferences("SavedArticles", Context.MODE_PRIVATE);
        isSaved = sharedPreferences.contains(articleTitle); // Sprawdzenie, czy artykuł jest zapisany

        // Ustawienie początkowej ikony zakładki
        updateBookmarkIcon();

        // Obsługa kliknięcia przycisku zakładki
        bookmarkButton.setOnClickListener(v -> toggleBookmark());
    }

    private void toggleBookmark() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isSaved) {
            editor.remove(articleTitle); // Usunięcie artykułu z zapisanych
            isSaved = false;

            // Powiadomienie SavedArticlesActivity o usunięciu artykułu
            Intent resultIntent = new Intent();
            resultIntent.putExtra("removed_article_title", articleTitle);
            setResult(RESULT_OK, resultIntent);
            Log.d("FullArticleActivity", "Article removed: " + articleTitle);
        } else {
            // Zapisanie danych artykułu jako string JSON
            String articleData = "{\"title\":\"" + articleTitle + "\",\"author\":\"" + articleAuthor + "\",\"content\":\"" + articleContent + "\",\"imageUrl\":\"" + articleImageUrl + "\"}";
            editor.putString(articleTitle, articleData);
            isSaved = true;
        }
        editor.apply();
        updateBookmarkIcon();
    }

    private void updateBookmarkIcon() {
        if (isSaved) {
            bookmarkButton.setImageResource(R.drawable.saved_bookmark); // Wypełniona ikona zakładki
        } else {
            bookmarkButton.setImageResource(R.drawable.bookmark); // Pusta ikona zakładki
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Zamknięcie aktywności i powrót
        return true;
    }
}