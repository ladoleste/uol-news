package br.com.uol.uolnews.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import br.com.uol.uolnews.R;
import br.com.uol.uolnews.customtabs.CustomTabsHelper;
import br.com.uol.uolnews.dto.Feed;
import br.com.uol.uolnews.global.Util;
import br.com.uol.uolnews.ui.viewmodel.MainViewModel;

@SuppressLint("LogNotTimber")
public class MainActivity extends AppCompatActivity implements ItemClick {

    private static final String TAG = "MainActivity";

    private MainViewModel vModel;
    private View rootView;
    private RecyclerView rvList;
    private Toolbar toolbar;
    private ImageView ivNoConnection;
    private CustomTabsIntent customTabsIntent;
    private ProgressBar progressBar;

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
        ivNoConnection = findViewById(R.id.iv_no_connection);
        progressBar = findViewById(R.id.progress_bar);

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
        Log.e(TAG, t.getMessage(), t);
        Snackbar.make(rootView, R.string.generic_error, Snackbar.LENGTH_LONG).setAction(R.string.retry, v -> {
            vModel.loadList();
            progressBar.setVisibility(View.VISIBLE);
        }).show();
        rvList.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        ivNoConnection.setVisibility(View.VISIBLE);
    }

    private void loadFeed(List<Feed> feeds) {
        rvList.setVisibility(View.VISIBLE);
        ivNoConnection.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        NewsAdapter newsAdapter = new NewsAdapter(feeds, this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setHasFixedSize(true);
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
            progressBar.setVisibility(View.VISIBLE);
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
    public void onItemClick(String webUrl, String shareUrl) {
        CustomTabsHelper.openCustomTab(this, customTabsIntent, Uri.parse(webUrl), (context, uri) -> {
            Intent intent = new Intent(MainActivity.this, WebViewFallback.class);
            intent.putExtra(WebViewFallback.WEB_URL, uri.toString());
            intent.putExtra(WebViewFallback.SHARE_URL, uri.toString());
            startActivity(intent);
        });
    }
}
