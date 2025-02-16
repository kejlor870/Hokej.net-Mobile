package com.example.hokejnetmobile;

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
        holder.authorText.setText(article.getAuthor());
//        holder.timestampText.setText(article.getTimestamp());

        // Load Image (Glide or Picasso recommended)
        Glide.with(context).load(article.getImageUrl()).into(holder.articleImage);

        // Handle click event
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullArticleActivity.class);
            intent.putExtra("article_title", article.getTitle());
            intent.putExtra("article_author", article.getAuthor());
            intent.putExtra("article_content", article.getContent());
            intent.putExtra("article_image", article.getImageUrl());
            context.startActivity(intent);
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

