package fr.xebia.cpele.xebiablog;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity
        extends LifecycleActivity {

    private static final String EXTRA_URL = "EXTRA_URL";

    private WebView mPageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        TextView titleView = (TextView) findViewById(R.id.detail_title);
        String pageUrl = getPageUrl();
        titleView.setText(pageUrl);

        mPageView = (WebView) findViewById(R.id.detail_page);

        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        assert connectivityManager != null;
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
            mPageView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mPageView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        mPageView.loadUrl(getPageUrl());
    }

    private String getPageUrl() {
        return getIntent().getStringExtra(EXTRA_URL);
    }

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_URL, url);
        context.startActivity(intent);
    }
}
