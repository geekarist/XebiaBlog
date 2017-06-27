package fr.xebia.cpele.xebiablog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Response;

class FeedRepository {

    private static final int CACHE_EXPIRATION_IN_MIN = BuildConfig.DEBUG ? 1 : 60;

    @NonNull
    private final BlogApi mBlogApi;
    @Nullable
    private Feed mCachedFeed;
    private long mFetchDate;

    FeedRepository(@NonNull BlogApi blogApi) {

        mBlogApi = blogApi;
        Log.d(getClass().getSimpleName(), "Life: FeedRepository: construct");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.d(getClass().getSimpleName(), "Life: FeedRepository: finalize");
    }

    void find(final Callback consumer) {

        Log.d(getClass().getSimpleName(), "Finding feed");

        if (isFeedCached()) {
            Log.d(getClass().getSimpleName(), "Feed is in cache");
            consumer.accept(mCachedFeed);
            return;
        }

        Log.d(getClass().getSimpleName(), "Cache has expired");

        mBlogApi.fetchFeed().enqueue(new retrofit2.Callback<Feed>() {

            @Override
            public void onResponse(@NonNull final Call<Feed> call, @NonNull final Response<Feed> response) {
                mCachedFeed = response.body();
                Log.d(FeedRepository.this.getClass().getSimpleName(), "Feed: " + mCachedFeed);
                mFetchDate = System.currentTimeMillis();
                consumer.accept(mCachedFeed);
            }

            @Override
            public void onFailure(@NonNull final Call<Feed> call, @NonNull final Throwable t) {
                Log.e(FeedRepository.this.getClass().getSimpleName(), "Here is an error", t);
            }
        });
    }

    private boolean isFeedCached() {
        long millisSinceFetchDate = System.currentTimeMillis() - mFetchDate;
        long expirationDelayInMillis = TimeUnit.MINUTES.toMillis(CACHE_EXPIRATION_IN_MIN);
        boolean cacheValid = millisSinceFetchDate < expirationDelayInMillis;
        return mCachedFeed != null && cacheValid;
    }

    interface Callback extends Consumer<Feed> {
    }
}
