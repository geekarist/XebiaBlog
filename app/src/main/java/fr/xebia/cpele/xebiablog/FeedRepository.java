package fr.xebia.cpele.xebiablog;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Response;

class FeedRepository {

    private App.BlogApi mBlogApi;

    public FeedRepository(App.BlogApi blogApi) {

        mBlogApi = blogApi;
    }

    public void find(final Callback consumer) {

        Log.d(getClass().getSimpleName(), "Initializing FeedViewModel");

        mBlogApi.fetchFeed().enqueue(new retrofit2.Callback<Feed>() {

            @Override
            public void onResponse(@NonNull final Call<Feed> call, @NonNull final Response<Feed> response) {
                Feed feed = response.body();
                Log.d(FeedViewModel.class.getSimpleName(), "Feed: " + feed);
                consumer.accept(feed);
            }

            @Override
            public void onFailure(@NonNull final Call<Feed> call, @NonNull final Throwable t) {
                Log.e(FeedViewModel.class.getSimpleName(), "Here is an error", t);
            }
        });
    }

    public interface Callback extends Consumer<Feed> {
    }
}
