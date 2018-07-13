package com.uolinc.uolnews.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.uolinc.uolnews.dto.Feed;
import com.uolinc.uolnews.repository.Repository;
import com.uolinc.uolnews.repository.RepositoryImpl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Anderson on 11/07/2018.
 */
public class MainViewModel extends ViewModel {

    private Disposable subscribe;
    private MutableLiveData<List<Feed>> news = new MutableLiveData<>();
    private MutableLiveData<Throwable> error = new MutableLiveData<>();
    private Repository repo = new RepositoryImpl();

    public LiveData<List<Feed>> getNews() {
        return news;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public void loadList() {

        try {
            subscribe = repo.loadList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(x -> news.postValue(x.getFeed()), t -> error.postValue(t));
        } catch (Exception e) {
            error.postValue(e);
        }
    }

    public void dispose() {
        subscribe.dispose();
    }
}