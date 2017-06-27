package fr.xebia.cpele.xebiablog;

import android.app.Application;
import android.util.Log;

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
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://blog.xebia.fr")
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
