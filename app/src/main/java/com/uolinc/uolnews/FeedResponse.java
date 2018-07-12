package com.uolinc.uolnews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anderson on 11/07/2018.
 */
public class FeedResponse {

    @SerializedName("feed")
    @Expose
    private List<Feed> feed = null;

    public List<Feed> getFeed() {
        return feed;
    }

    public void setFeed(List<Feed> feed) {
        this.feed = feed;
    }
}