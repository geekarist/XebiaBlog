package fr.xebia.cpele.xebiablog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public class FeedViewModel extends ViewModel {

    private static final String FEED_URL = "http://blog.xebia.fr";

    private MutableLiveData<Feed> mFeed = new MutableLiveData<>();

    public LiveData<Feed> getFeed() {
        return mFeed;
    }

    public void init() {

        if (mFeed.getValue() != null) {
            Log.d(getClass().getSimpleName(), "Feed already fetched, no initialization needed");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FEED_URL)
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .build();

        BlogApi blogApi = retrofit.create(BlogApi.class);

        Log.d(getClass().getSimpleName(), "Initializing FeedViewModel");

        blogApi.fetchFeed().enqueue(new Callback<Feed>() {

            @Override
            public void onResponse(@NonNull final Call<Feed> call, @NonNull final Response<Feed> response) {
                Feed feed = response.body();
                Log.d(FeedViewModel.class.getSimpleName(), "Feed: " + feed);
                mFeed.setValue(feed);
            }

            @Override
            public void onFailure(@NonNull final Call<Feed> call, @NonNull final Throwable t) {
                Log.e(FeedViewModel.class.getSimpleName(), "Here is an error", t);
            }
        });
    }

    private interface BlogApi {
        @GET("/feed")
        Call<Feed> fetchFeed();
    }
}
