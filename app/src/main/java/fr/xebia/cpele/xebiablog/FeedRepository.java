package fr.xebia.cpele.xebiablog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Response;

class FeedRepository {

    @NonNull
    private BlogApi mBlogApi;
    @Nullable
    private Feed mCachedFeed;

    public FeedRepository(@NonNull BlogApi blogApi) {

        mBlogApi = blogApi;
        Log.d(getClass().getSimpleName(), "Life: FeedRepository: construct");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.d(getClass().getSimpleName(), "Life: FeedRepository: finalize");
    }

    public void find(final Callback consumer) {

        Log.d(getClass().getSimpleName(), "Initializing FeedViewModel");

        if (mCachedFeed != null) consumer.accept(mCachedFeed);

        mBlogApi.fetchFeed().enqueue(new retrofit2.Callback<Feed>() {

            @Override
            public void onResponse(@NonNull final Call<Feed> call, @NonNull final Response<Feed> response) {
                mCachedFeed = response.body();
                Log.d(FeedViewModel.class.getSimpleName(), "Feed: " + mCachedFeed);
                consumer.accept(mCachedFeed);
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
