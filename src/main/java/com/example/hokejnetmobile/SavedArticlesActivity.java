package com.example.hokejnetmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SavedArticlesActivity extends AppCompatActivity {

    private RecyclerView savedRecyclerView;
    private TextView noSavedText;
    private ArticleAdapter articleAdapter;
    private List<Article> savedArticles = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_articles);

        // Setup Toolbar z nawigacją wstecz
        Toolbar toolbarSavedArticles = findViewById(R.id.toolbarSavedArticles);
        setSupportActionBar(toolbarSavedArticles);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        savedRecyclerView = findViewById(R.id.savedRecyclerView);
        noSavedText = findViewById(R.id.noSavedText);

        sharedPreferences = getSharedPreferences("SavedArticles", MODE_PRIVATE);

        savedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleAdapter = new ArticleAdapter(this, savedArticles);
        savedRecyclerView.setAdapter(articleAdapter);

        loadSavedArticles();

        if (savedArticles.isEmpty()) {
            noSavedText.setVisibility(View.VISIBLE);
        } else {
            noSavedText.setVisibility(View.GONE);
        }
    }

    private void loadSavedArticles() {
        savedArticles.clear(); // Wyczyść listę przed ponownym wczytaniem
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            try {
                // Parsowanie danych artykulu z JSON
                JSONObject articleData = new JSONObject(entry.getValue().toString());
                String title = articleData.getString("title");
                String author = articleData.getString("author");  // Wczytaj autora
                String content = articleData.getString("content");
                String imageUrl = articleData.getString("imageUrl");

                // Tworzenie obiketu Article i dodanie do listy
                Article article = new Article();
                article.setTitle(title);
                article.setAuthor(author);  // Ustaw autora
                article.setContent(content);
                article.setImageUrl(imageUrl);
                savedArticles.add(article);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SavedArticlesActivity", "onActivityResult called");

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Artykuł został usunięty, odśwież listę
            String removedArticleTitle = data.getStringExtra("removed_article_title");
            Log.d("SavedArticlesActivity", "Article removed: " + removedArticleTitle);

            if (removedArticleTitle != null) {
                // Usunięcie artykułu z listy
                for (int i = 0; i < savedArticles.size(); i++) {
                    if (savedArticles.get(i).getTitle().equals(removedArticleTitle)) {
                        savedArticles.remove(i);
                        break;
                    }
                }

                // Powiadomienie adaptera o zmianie danych
                articleAdapter.notifyDataSetChanged();

                // Pokazanie/ukrycie komunikatu "Brak zapisanych artykułów"
                if (savedArticles.isEmpty()) {
                    noSavedText.setVisibility(View.VISIBLE);
                } else {
                    noSavedText.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSavedArticles(); // Ponowne wczytanie zapisanych artykułów
        articleAdapter.notifyDataSetChanged(); // Powiadomienie adaptera o zmianach

        // Pokazanie/ukrycie komunikatu "Brak zapisanych artykułów"
        if (savedArticles.isEmpty()) {
            noSavedText.setVisibility(View.VISIBLE);
        } else {
            noSavedText.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Zamknięcie aktywności i powrót
        return true;
    }
}