package com.uolinc.uolnews;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.uolinc.uolnews.customtabs.CustomTabsHelper;

import java.util.List;

@SuppressLint("LogNotTimber")
public class MainActivity extends AppCompatActivity implements ItemClick {

    private MainViewModel vModel;
    private View rootView;
    private RecyclerView rvList;
    private Toolbar toolbar;

    private CustomTabsIntent customTabsIntent;
//    private CustomTabsHelper customTabsHelper = new CustomTabsHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setSupportActionBar(toolbar);

        vModel.getNews().observe(this, this::loadFeed);
        vModel.getError().observe(this, this::handleError);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        rootView = findViewById(R.id.root_view);
        rvList = findViewById(R.id.rv_list);
        vModel = ViewModelProviders.of(this).get(MainViewModel.class);

        Bitmap backArrow = Util.getBitmapFromVectorDrawable(R.drawable.ic_arrow_back_white, this);

        customTabsIntent = new CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setShowTitle(true)
                .setCloseButtonIcon(backArrow)
                .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right)
                .build();
    }

    private void handleError(Throwable t) {
        Log.e("UOLNews", t.getMessage(), t);
        Snackbar.make(rootView, R.string.generic_error, Snackbar.LENGTH_LONG).setAction(R.string.retry, null).show();
    }

    private void loadFeed(List<Feed> feeds) {
        NewsAdapter newsAdapter = new NewsAdapter(feeds, this);
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

    @Override
    public void onItemClick(String webUrl) {
        CustomTabsHelper.openCustomTab(this, customTabsIntent, Uri.parse(webUrl), null);
    }
}
