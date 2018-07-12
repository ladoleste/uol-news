package com.uolinc.uolnews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Feed feed = feeds.get(position);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM 'às' hh'h'mm", Locale.getDefault());
        String format1 = format.format(feed.getUpdated());

        holder.tvTitle.setText(feed.getTitle());
        holder.tvUpdate.setText(format1);
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvUpdate;

        NewsViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvUpdate = itemView.findViewById(R.id.tv_update);
        }
    }
}
