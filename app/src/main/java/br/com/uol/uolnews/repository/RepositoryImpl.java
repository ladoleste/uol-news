package br.com.uol.uolnews.repository;

import br.com.uol.uolnews.dto.FeedResponse;
import io.reactivex.Single;

/**
 * Created by Anderson on 12/07/2018.
 */
public class RepositoryImpl implements Repository {

    private RetrofitService api;

    public RepositoryImpl() {
        api = APIClient.getClient().create(RetrofitService.class);
    }

    public RepositoryImpl(RetrofitService api) {
        this.api = api;
    }

    public Single<FeedResponse> loadList() {
        return api.loadList();
    }
}