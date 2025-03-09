package com.example.hokejnetmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.bumptech.glide.Glide;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private List<Article> articleList;
    private Context context;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articleList = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.titleText.setText(article.getTitle());
        // Zabezpieczenie przed null - wyświetla "Brak autora", jeśli author jest null
        holder.authorText.setText(article.getAuthor() != null ? article.getAuthor() : "Brak autora");

        // Load image using Glide
        if (article.getImageUrl() != null && !article.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(article.getImageUrl())
                    .error(R.drawable.error_image)
                    .into(holder.articleImage);
        } else {
            holder.articleImage.setImageResource(R.drawable.error_image);
        }

        // Handle click event
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullArticleActivity.class);
            intent.putExtra("article_title", article.getTitle());
            intent.putExtra("article_author", article.getAuthor());
            intent.putExtra("article_content", article.getContent());
            intent.putExtra("article_image", article.getImageUrl());
            ((Activity) context).startActivityForResult(intent, 1); // Use startActivityForResult
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, authorText, timestampText;
        ImageView articleImage;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            authorText = itemView.findViewById(R.id.authorText);
            timestampText = itemView.findViewById(R.id.timestampText);
            articleImage = itemView.findViewById(R.id.articleImage);
        }
    }
}