package com.example.hokejnetmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class SavedArticlesActivity extends AppCompatActivity {
    private RecyclerView savedRecyclerView;
    private TextView noSavedText;
    private ArticleAdapter articleAdapter;
    private ArrayList<Article> savedArticles = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_articles);

        // Setup Toolbar with back navigation
        Toolbar toolbarSavedArticles = findViewById(R.id.toolbarSavedArticles);
        setSupportActionBar(toolbarSavedArticles);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }


        savedRecyclerView = findViewById(R.id.savedRecyclerView);
        noSavedText = findViewById(R.id.noSavedText);

        sharedPreferences = getSharedPreferences("SavedArticles", Context.MODE_PRIVATE);
        loadSavedArticles();

        savedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleAdapter = new ArticleAdapter(this, savedArticles);
        savedRecyclerView.setAdapter(articleAdapter);

        if (savedArticles.isEmpty()) {
            noSavedText.setVisibility(View.VISIBLE);
        }
    }

    private void loadSavedArticles() {
        savedArticles.clear(); // Clear the list before reloading
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            try {
                // Parse the JSON string to extract title, content, and imageUrl
                JSONObject articleData = new JSONObject(entry.getValue().toString());
                String title = articleData.getString("title");
                String content = articleData.getString("content");
                String imageUrl = articleData.getString("imageUrl");

                // Add the article to the list
                savedArticles.add(new Article(title, "Unknown Author", content, imageUrl));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SavedArticlesActivity", "onActivityResult called");

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // An article was removed, refresh the list
            String removedArticleTitle = data.getStringExtra("removed_article_title");
            Log.d("SavedArticlesActivity", "Article removed: " + removedArticleTitle);

            if (removedArticleTitle != null) {
                // Remove the article from the list
                for (int i = 0; i < savedArticles.size(); i++) {
                    if (savedArticles.get(i).getTitle().equals(removedArticleTitle)) {
                        savedArticles.remove(i);
                        break;
                    }
                }

                // Notify the adapter that the data has changed
                articleAdapter.notifyDataSetChanged();

                // Show/hide the "No saved articles" message
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
        loadSavedArticles(); // Reload saved articles
        articleAdapter.notifyDataSetChanged(); // Notify the adapter of changes

        // Show/hide the "No saved articles" message
        if (savedArticles.isEmpty()) {
            noSavedText.setVisibility(View.VISIBLE);
        } else {
            noSavedText.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Close activity and go back
        return true;
    }

}
