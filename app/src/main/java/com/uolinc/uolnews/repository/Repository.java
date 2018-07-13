package com.uolinc.uolnews.repository;

import com.uolinc.uolnews.dto.FeedResponse;

import io.reactivex.Single;

/**
 * Created by Anderson on 12/07/2018.
 */
public interface Repository {
    Single<FeedResponse> loadList();
}
