package br.com.uol.uolnews.repository;

import br.com.uol.uolnews.dto.FeedResponse;
import io.reactivex.Single;

/**
 * Created by Anderson on 12/07/2018.
 */
public interface Repository {
    Single<FeedResponse> loadList();
}
