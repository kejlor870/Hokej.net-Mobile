package com.example.hokejnetmobile;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ONLY LIGHT MODE!!!
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load Sample Data
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("Hannes Grauweihler", "Portugal And I Are Very Much In Love", "May 26 • 7 min read", R.drawable.home));
        articles.add(new Article("Liza Donnelly", "Barbara Shermund, Observer of Us", "Mar 11 • 3 min read", R.drawable.bell));
        articles.add(new Article("Hannes Grauweihler", "Portugal And I Are Very Much In Love", "May 26 • 7 min read", R.drawable.home));
        articles.add(new Article("Liza Donnelly", "Barbara Shermund, Observer of Us", "Mar 11 • 3 min read", R.drawable.bell));
        articles.add(new Article("Hannes Grauweihler", "Portugal And I Are Very Much In Love", "May 26 • 7 min read", R.drawable.home));
        articles.add(new Article("Liza Donnelly", "Barbara Shermund, Observer of Us", "Mar 11 • 3 min read", R.drawable.bell));
        articles.add(new Article("Hannes Grauweihler", "Portugal And I Are Very Much In Love", "May 26 • 7 min read", R.drawable.home));
        articles.add(new Article("Liza Donnelly", "Barbara Shermund, Observer of Us", "Mar 11 • 3 min read", R.drawable.bell));
        articles.add(new Article("Hannes Grauweihler", "Portugal And I Are Very Much In Love", "May 26 • 7 min read", R.drawable.home));
        articles.add(new Article("Liza Donnelly", "Barbara Shermund, Observer of Us", "Mar 11 • 3 min read", R.drawable.bell));
        articleAdapter = new ArticleAdapter(articles);
        recyclerView.setAdapter(articleAdapter);

        // Bottom Navigation Clicks
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_bookmark) {
                    // Handle bookmark navigation
                    List<Article> articles2 = new ArrayList<>();
                    articles2.add(new Article("Liza Donnelly", "Barbara Shermund, Observer of Us", "Mar 11 • 3 min read", R.drawable.bell));
                    articles2.add(new Article("Hannes Grauweihler", "Portugal And I Are Very Much In Love", "May 26 • 7 min read", R.drawable.home));

                    articleAdapter = new ArticleAdapter(articles2);
                    recyclerView.setAdapter(articleAdapter);
                } else if (id == R.id.nav_home) {
                    // Handle settings navigation
                    articleAdapter = new ArticleAdapter(articles);
                    recyclerView.setAdapter(articleAdapter);
                }else if (id == R.id.nav_profile) {
                    // Handle settings navigation
                    Toast.makeText(MainActivity.this, "Kliknieto profil", Toast.LENGTH_SHORT).show();
                }

                return true;
            }

        });
    }
}
