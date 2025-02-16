package com.example.hokejnetmobile;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Handler;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private BottomNavigationView bottomNavigationView;
    private List<Article> articleList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ONLY LIGHT MODE!!!
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Setup RecyclerView and SwipeRefreshLayout
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleAdapter = new ArticleAdapter(this, articleList);
        recyclerView.setAdapter(articleAdapter);

        // Load initial articles
        loadArticles();

        // Swipe to refresh functionality
        swipeRefreshLayout.setOnRefreshListener(() -> refreshArticles());

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
                    articles2.add(new Article("Liza Donnelly", "Barbara Shermund, Observer of Us", "Mar 11 • 3 min read", "https://hokej.net/storage/galerie/7065/ce263a6255358357bbdcee13caf75fe0.JPG"));
                    articles2.add(new Article("Hannes Grauweihler", "Portugal And I Are Very Much In Love", "May 26 • 7 min read", "https://hokej.net/storage/galerie/7065/ffa13833d7305627e74ce34e6d6e78fa.JPG"));

                    articleAdapter = new ArticleAdapter(MainActivity.this, articles2);
                    recyclerView.setAdapter(articleAdapter);
                } else if (id == R.id.nav_home) {
                    // Handle settings navigation
                    articleAdapter = new ArticleAdapter(MainActivity.this, articleList);
                    recyclerView.setAdapter(articleAdapter);
                } else if (id == R.id.nav_profile) {
                    // Handle settings navigation
                    Toast.makeText(MainActivity.this, "Kliknieto profil", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        // Detect scroll to hide/show bottom navigation
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isBottomNavVisible = true; // To track visibility of bottom nav
            private int lastScrollY = 0; // Track last scroll position

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // If scrolling down and bottom nav is visible, hide it
                if (dy > 0 && isBottomNavVisible) {
                    bottomNavigationView.animate().translationY(bottomNavigationView.getHeight()).setDuration(300);
                    isBottomNavVisible = false;
                }
                // If scrolling up and bottom nav is hidden, show it
                else if (dy < 0 && !isBottomNavVisible) {
                    bottomNavigationView.animate().translationY(0).setDuration(300);
                    isBottomNavVisible = true;
                }
            }
        });
    }

    private void loadArticles() {
        // Simulate fetching articles (replace with real API call)
        articleList.clear();
        articleList.add(new Article("Portugal And I Are Very Much In Love", "Hannes Grauweihler", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "https://unia-oswiecim.pl/wp-content/uploads/2024/09/002.jpg"));
        articleList.add(new Article("Barbara Shermund, Observer of Us", "Liza Donnelly", "Mar 11 • 3 min read", "https://hokej.net/storage/galerie/7065/ffa13833d7305627e74ce34e6d6e78fa.JPG"));
        articleList.add(new Article("Portugal And I Are Very Much In Love", "Hannes Grauweihler", "May 26 • 7 min read", "https://hokej.net/storage/galerie/7065/ce263a6255358357bbdcee13caf75fe0.JPG"));
        // Add more articles as needed
        articleAdapter.notifyDataSetChanged();
    }

    private void refreshArticles() {
        new Handler().postDelayed(() -> {
            // Simulate fetching new articles
            articleList.add(0, new Article("New Title " + articleList.size(), "New Author", "New Content", "https://example.com/new_image.jpg"));
            articleAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false); // Stop the refreshing animation
        }, 2000); // Delay to simulate loading time
    }
}
