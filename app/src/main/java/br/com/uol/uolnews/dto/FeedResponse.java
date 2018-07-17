package br.com.uol.uolnews.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anderson on 11/07/2018.
 */
public class FeedResponse {

    @SerializedName("feed")
    private List<Feed> feed = null;

    public List<Feed> getFeed() {
        return feed;
    }

    public void setFeed(List<Feed> feed) {
        this.feed = feed;
    }
}