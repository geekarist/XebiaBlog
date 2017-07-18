package fr.xebia.cpele.xebiablog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

public class FeedViewModel extends ViewModel {

    private MutableLiveData<Feed> mFeed;
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

    LiveData<Feed> get() {
        if (mFeed == null) mFeed = new MutableLiveData<>();
        mFeedRepository.find(mFeed::setValue);
        return mFeed;
    }
}
