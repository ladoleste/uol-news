package br.com.uol.uolnews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import br.com.uol.uolnews.R;

public class WebViewFallback extends AppCompatActivity {

    public static final String WEB_URL = "webUrl";
    public static final String SHARE_URL = "shareUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_fallback);
        setSupportActionBar(findViewById(R.id.toolbar));
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            WebView webView = findViewById(R.id.webview);
            webView.setWebViewClient(new UolWebViewClient());
            webView.loadUrl(getIntent().getExtras().getString(WEB_URL, ""));
        } else {
            Toast.makeText(this, R.string.error_parameter_not_found, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fallback_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            if (getIntent().getExtras() != null) {
                sendIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString(SHARE_URL, ""));
            }
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        } else {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class UolWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}