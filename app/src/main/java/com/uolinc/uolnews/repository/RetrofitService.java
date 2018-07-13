package com.uolinc.uolnews.repository;

import com.uolinc.uolnews.dto.FeedResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Anderson on 11/07/2018.
 */
public interface RetrofitService {
    @GET("/c/api/v1/list/news/?app=uol-placar-futebol&version=2")
    Single<FeedResponse> loadList();
}
