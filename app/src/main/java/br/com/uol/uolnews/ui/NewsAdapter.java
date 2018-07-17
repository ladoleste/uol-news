package br.com.uol.uolnews.ui;

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

import br.com.uol.uolnews.R;
import br.com.uol.uolnews.dto.Feed;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Feed> feeds;
    private ItemClick onItemClick;

    NewsAdapter(List<Feed> feeds, ItemClick onItemClick) {
        this.feeds = feeds;
        this.onItemClick = onItemClick;
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM', às 'hh'h'mm", Locale.getDefault());
        String format = formatter.format(feed.getUpdated());

        holder.tvTitle.setText(feed.getTitle());
        holder.tvUpdate.setText(format);
        holder.itemView.setOnClickListener(v -> onItemClick.onItemClick(feed.getWebviewUrl(), feed.getShareUrl()));
        if (feed.getThumb() != null) {
            holder.ivImage.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext()).load(feed.getThumb()).into(holder.ivImage);
        } else {
            holder.ivImage.setVisibility(View.GONE);
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
