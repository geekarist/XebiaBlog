package fr.xebia.cpele.xebiablog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

class FeedViewModel extends ViewModel {

    @NonNull
    private final MutableLiveData<Feed> mFeed = new MutableLiveData<>();
    @NonNull
    private final FeedRepository mFeedRepository;

    public FeedViewModel() {
        super();
        Log.d(getClass().getSimpleName(), "Life: FeedViewModel: construct");
        mFeedRepository = App.instance().provideFeedRepository();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.d(getClass().getSimpleName(), "Life: FeedViewModel: finalize");
    }

    LiveData<Feed> getFeed() {
        return mFeed;
    }

    void init() {
        mFeedRepository.find(mFeed::setValue);
    }
}
