package com.example.hokejnetmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> articles;

    public ArticleAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.authorText.setText(article.getAuthor());
        holder.titleText.setText(article.getTitle());
        holder.timestampText.setText(article.getTimestamp());
        holder.articleImage.setImageResource(article.getImageResId());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView authorText, titleText, timestampText;
        ImageView articleImage;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            authorText = itemView.findViewById(R.id.authorText);
            titleText = itemView.findViewById(R.id.titleText);
            timestampText = itemView.findViewById(R.id.timestampText);
            articleImage = itemView.findViewById(R.id.articleImage);
        }
    }
}
