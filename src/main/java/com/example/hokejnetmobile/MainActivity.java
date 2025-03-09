package com.example.hokejnetmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
                    startActivity(new Intent(MainActivity.this, SavedArticlesActivity.class));
                } else if (id == R.id.nav_home) {
                    articleAdapter = new ArticleAdapter(MainActivity.this, articleList);
                    recyclerView.setAdapter(articleAdapter);
                } else if (id == R.id.nav_profile) {
                    Toast.makeText(MainActivity.this, "Kliknięto profil", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        // Detect scroll to hide/show bottom navigation
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isBottomNavVisible = true;
            private int lastScrollY = 0;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0 && isBottomNavVisible) {
                    bottomNavigationView.animate().translationY(bottomNavigationView.getHeight()).setDuration(300);
                    isBottomNavVisible = false;
                } else if (dy < 0 && !isBottomNavVisible) {
                    bottomNavigationView.animate().translationY(0).setDuration(300);
                    isBottomNavVisible = true;
                }
            }
        });
    }

    private void loadArticles() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        NewsApiService newsApiService = retrofit.create(NewsApiService.class);

        // Twój klucz API z NewsAPI
        String apiKey = "3433561f3408493d8c2f3f881260778c";
        // Słowa kluczowe dla hokeja
        String query = "hockey OR NHL";

        Call<NewsResponse> call = newsApiService.getHockeyArticles(query, apiKey);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    articleList.clear();
                    articleList.addAll(articles);
                    articleAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Błąd podczas ładowania danych", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Błąd połączenia z serwerem", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ustaw domyślne zaznaczenie na "Home"
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private void refreshArticles() {
        // Pobierz nowe artykuły z API, wywołując loadArticles()
        loadArticles();
    }
}