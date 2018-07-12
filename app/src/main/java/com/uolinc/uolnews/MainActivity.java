package com.uolinc.uolnews;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel vModel;
    private View rootView;
    private RecyclerView rvList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> vModel.loadList());

        vModel.getNews().observe(this, this::loadFeed);
        vModel.getError().observe(this, this::handleError);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        rootView = findViewById(R.id.root_view);
        rvList = findViewById(R.id.rv_list);
        vModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void handleError(Throwable t) {
        Log.e("UOLNews", t.getMessage(), t);
        Snackbar.make(rootView, R.string.generic_error, Snackbar.LENGTH_LONG).setAction(R.string.retry, null).show();
    }

    private void loadFeed(List<Feed> feeds) {
        NewsAdapter newsAdapter = new NewsAdapter(feeds);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(newsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        vModel.loadList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reload) {
            vModel.loadList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        vModel.dispose();
        super.onDestroy();
    }
}
