package fr.xebia.cpele.xebiablog;

import android.app.Application;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class App extends Application {

    private static App mInstance;

    private BlogApi mBlogApi;

    public static App instance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.d(getClass().getSimpleName(), "Life: App: create");
    }

    public BlogApi provideApi() {

        if (mBlogApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://blog.xebia.fr")
                    .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                    .build();

            mBlogApi = retrofit.create(BlogApi.class);
        }
        return mBlogApi;
    }
}
