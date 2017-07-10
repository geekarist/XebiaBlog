package fr.xebia.cpele.xebiablog;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import jonas.tool.save_page.PageSaver;

public class DetailActivity
        extends LifecycleActivity
        implements LoaderManager.LoaderCallbacks<File> {

    private static final String EXTRA_URL = "EXTRA_URL";
    private static final int LOADER_ID = 51;

    private WebView mPageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        TextView titleView = (TextView) findViewById(R.id.detail_title);
        String pageUrl = getPageUrl();
        titleView.setText(pageUrl);

        mPageView = (WebView) findViewById(R.id.detail_page);

        getSupportLoaderManager()
                .initLoader(LOADER_ID, null, this)
                .forceLoad();
    }

    private String getPageUrl() {
        return getIntent().getStringExtra(EXTRA_URL);
    }

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override
    public Loader<File> onCreateLoader(int id, Bundle args) {
        String pageUrl = getPageUrl();
        String outputDirPath = getCacheDir().getPath() + File.separator + "pages";
        return new PageLoader(DetailActivity.this, pageUrl, outputDirPath);
    }

    @Override
    public void onLoadFinished(Loader<File> loader, File data) {
        String path = urlOfFile(data);
        mPageView.loadUrl(path);
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

    @Override
    public void onLoaderReset(Loader<File> loader) {
        // TODO
    }

    private static class PageLoader extends AsyncTaskLoader<File> {

        private String mUrl;
        private String mOutputDirPath;

        PageLoader(Context context, final String url, final String outputDirPath) {
            super(context);
            mUrl = url;
            mOutputDirPath = outputDirPath;
        }

        @Override
        public File loadInBackground() {
            PageSaver pageSaver = new PageSaver(new PageSaveCallback());
            pageSaver.getPage(mUrl, mOutputDirPath, "index.html");
            String pageIndexPath = mOutputDirPath + File.separator + "index.html";
            return new File(pageIndexPath);
        }
    }

    private static class PageSaveCallback implements PageSaver.EventCallback {

        @Override
        public void onProgressChanged(final int i, final int i1, final boolean b) {

        }

        @Override
        public void onProgressMessage(final String s) {

        }

        @Override
        public void onPageTitleAvailable(final String s) {

        }

        @Override
        public void onLogMessage(final String s) {

        }

        @Override
        public void onError(final Throwable throwable) {

        }

        @Override
        public void onError(final String s) {

        }

        @Override
        public void onFatalError(final Throwable throwable, final String s) {

        }
    }
}
