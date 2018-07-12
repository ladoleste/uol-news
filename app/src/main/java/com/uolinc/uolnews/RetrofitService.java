package com.uolinc.uolnews;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Anderson on 11/07/2018.
 */
public interface RetrofitService {
    @GET("/c/api/v1/list/news/?app=uol-placar-futebol&version=2")
    Single<FeedResponse> loadList();
}
