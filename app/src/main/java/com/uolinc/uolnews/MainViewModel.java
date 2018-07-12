package com.uolinc.uolnews;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Anderson on 11/07/2018.
 */
public class MainViewModel extends ViewModel {

    private Disposable subscribe;

    private MutableLiveData<List<Feed>> _news = new MutableLiveData<>();
    public LiveData<List<Feed>> news = _news;

    private MutableLiveData<Throwable> _error = new MutableLiveData<>();
    public LiveData<Throwable> error = _error;

    public void loadList() {

        subscribe = APIClient.getClient().create(RetrofitService.class).loadList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> _news.postValue(x.getFeed()), t -> _error.postValue(t));
    }

    public void dispose() {
        subscribe.dispose();
    }
}