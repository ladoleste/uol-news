package br.com.uol.uolnews.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Anderson on 11/07/2018.
 */
public class Feed {

    @SerializedName("type")
    private String type;
    @SerializedName("title")
    private String title;
    @SerializedName("thumb")
    private String thumb;
    @SerializedName("updated")
    private Date updated;
    @SerializedName("share-url")
    private String shareUrl;
    @SerializedName("webview-url")
    private String webviewUrl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getWebviewUrl() {
        return webviewUrl;
    }

    public void setWebviewUrl(String webviewUrl) {
        this.webviewUrl = webviewUrl;
    }

}