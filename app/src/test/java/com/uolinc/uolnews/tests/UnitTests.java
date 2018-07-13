package com.uolinc.uolnews.tests;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.uolinc.uolnews.dto.Feed;
import com.uolinc.uolnews.dto.FeedResponse;
import com.uolinc.uolnews.repository.Repository;
import com.uolinc.uolnews.repository.RepositoryImpl;
import com.uolinc.uolnews.repository.RetrofitService;
import com.uolinc.uolnews.ui.viewmodel.MainViewModel;
import com.uolinc.uolnews.util.Util;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class UnitTests {

    @Rule
    public TestRule testRule = new InstantTaskExecutorRule();
    @Mock
    private RetrofitService api;
    private MainViewModel viewModel;
    private Repository repo;

    @Before
    public void setUp() {
        viewModel = new MainViewModel();
        repo = new RepositoryImpl(api);
    }

    @Test
    public void test_view_model() {
        when(repo.loadList()).thenReturn(getSuccessResponse());

        viewModel.loadList();

        Feed item = repo.loadList().blockingGet().getFeed().get(0);

        assertEquals("Jogador de futebol larga carreira e vira spec na UOL", item.getTitle());
        assertEquals("https://esporte.uol.com.br/futebol/ultimas-noticias/2018/07/13/balbuena-se-despede-do-corinthians-o-sangue-no-olho-e-para-sempre.htm", item.getShareUrl());
        assertEquals("https://imguol.com/c/esporte/8a/2018/06/06/balbuena-protege-a-bola-de-rodrygo-no-classico-entre-corinthians-e-santos-1528334370813_142x100.jpg", item.getThumb());
        assertEquals("https://esporte.uol.com.br/futebol/ultimas-noticias/2018/07/13/balbuena-se-despede-do-corinthians-o-sangue-no-olho-e-para-sempre.htm?app=uol-placar-futebol&plataforma=iphone&template=v2", item.getWebviewUrl());
        assertEquals("Fri Jul 13 15:12:35 BRT 2018", item.getUpdated().toString());
    }

    private Single<FeedResponse> getSuccessResponse() {
        return Single.just(Util.getGson().fromJson(Util.readFile("response.json"), FeedResponse.class));
    }
}