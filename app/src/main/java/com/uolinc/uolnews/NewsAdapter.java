package com.uolinc.uolnews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Feed> feeds;

    NewsAdapter(List<Feed> feeds) {
        this.feeds = feeds;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType == 0 ? R.layout.item_news : R.layout.item_news_with_image, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return feeds.get(position).getThumb() == null ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Feed feed = feeds.get(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM', às 'hh'h'mm", Locale.getDefault());
        String format = formatter.format(feed.getUpdated());

        holder.tvTitle.setText(feed.getTitle());
        holder.tvUpdate.setText(format);
        if (getItemViewType(position) == 1) {
            Glide.with(holder.itemView.getContext()).load(feed.getThumb()).into(holder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvUpdate;
        ImageView ivImage;

        NewsViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvUpdate = itemView.findViewById(R.id.tv_update);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }
}
