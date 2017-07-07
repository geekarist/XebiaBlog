package fr.xebia.cpele.xebiablog;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.TextView;

public class DetailActivity extends LifecycleActivity {

    private static final String EXTRA_URL = "EXTRA_URL";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        TextView titleView = (TextView) findViewById(R.id.detail_title);
        String pageUrl = getIntent().getStringExtra(EXTRA_URL);
        titleView.setText(pageUrl);

        WebView pageView = (WebView) findViewById(R.id.detail_page);
        pageView.loadUrl(pageUrl);
    }

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_URL, url);
        context.startActivity(intent);
    }
}
