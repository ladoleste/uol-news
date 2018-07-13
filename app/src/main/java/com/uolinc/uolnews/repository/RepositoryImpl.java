package com.uolinc.uolnews.repository;

import com.uolinc.uolnews.dto.FeedResponse;

import io.reactivex.Single;

/**
 * Created by Anderson on 12/07/2018.
 */
public class RepositoryImpl implements Repository {

    private RetrofitService api;

    public RepositoryImpl() {
        this.api = APIClient.getClient().create(RetrofitService.class);
    }

    public RepositoryImpl(RetrofitService api) {
        this.api = api;
    }

    public Single<FeedResponse> loadList() {
        return api.loadList();
    }
}