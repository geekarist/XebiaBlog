package fr.xebia.cpele.xebiablog.view;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URI;

import fr.xebia.cpele.xebiablog.App;
import fr.xebia.cpele.xebiablog.R;
import fr.xebia.cpele.xebiablog.model.PageRepository;

public class DetailActivity
        extends LifecycleActivity {

    private static final String EXTRA_URL = "EXTRA_URL";

    private WebView mPageView;
    private NetworkInfo mActiveNetworkInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        TextView titleView = (TextView) findViewById(R.id.detail_title);
        String pageUrl = getPageUrl();
        titleView.setText(pageUrl);

        mPageView = (WebView) findViewById(R.id.detail_page);

        initNetworkInfo();

        if (isOnline()) {
            mPageView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mPageView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        PageRepository pageRepository = App.instance().providePageRepository();
        pageRepository.findOne(getPageUrl()).observe(this, file -> {
            try {
                if (file == null) return;
                URI uri = file.toURI();
                String url = String.valueOf(uri.toURL());
                mPageView.loadUrl(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }

    private void initNetworkInfo() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        assert connectivityManager != null;
        mActiveNetworkInfo = connectivityManager.getActiveNetworkInfo();
    }

    private boolean isOnline() {
        return mActiveNetworkInfo != null && mActiveNetworkInfo.isConnected();
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
