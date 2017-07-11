package fr.xebia.cpele.xebiablog;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

        PageRepository pageRepository = App.instance().providePageRepository();

        pageRepository
                .findOne(getPageUrl())
                .observe(this, data -> mPageView.loadUrl(urlOfFile(data)));
    }

    private String getPageUrl() {
        return getIntent().getStringExtra(EXTRA_URL);
    }

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_URL, url);
        context.startActivity(intent);
    }

    private String urlOfFile(final File data) {
        URL url = null;
        try {
            url = data.toURI().toURL();
        } catch (MalformedURLException e) {
            Log.e(DetailActivity.this.getLocalClassName(), "Malformed URL", e);
        }
        return String.valueOf(url);
    }
}
