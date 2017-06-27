package fr.xebia.cpele.xebiablog;

import android.app.Application;
import android.util.Log;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class App extends Application {

    private static App mInstance;

    private BlogApi mBlogApi;
    private FeedRepository mFeedRepository;

    public static App instance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.d(getClass().getSimpleName(), "Life: App: create");
    }

    private BlogApi provideApi() {

        if (mBlogApi == null) {

            File cacheDir = new File(getCacheDir(), "http");
            // 10 Mib cache
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(cacheDir, cacheSize);
            OkHttpClient httpClient = new OkHttpClient.Builder().cache(cache).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://blog.xebia.fr")
                    .client(httpClient)
                    .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                    .build();

            mBlogApi = retrofit.create(BlogApi.class);
        }
        return mBlogApi;
    }

    public FeedRepository provideFeedRepository() {
        if (mFeedRepository == null) {
            mFeedRepository = new FeedRepository(provideApi());
        }
        return mFeedRepository;
    }
}
