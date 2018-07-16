package br.com.uol.uolnews.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import br.com.uol.uolnews.dto.Feed;
import br.com.uol.uolnews.repository.Repository;
import br.com.uol.uolnews.repository.RepositoryImpl;
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