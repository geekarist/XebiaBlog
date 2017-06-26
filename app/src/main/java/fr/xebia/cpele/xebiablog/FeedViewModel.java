package fr.xebia.cpele.xebiablog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

public class FeedViewModel extends ViewModel {

    @NonNull
    private MutableLiveData<Feed> mFeed = new MutableLiveData<>();
    @NonNull
    private FeedRepository mFeedRepository;

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

    public LiveData<Feed> getFeed() {
        return mFeed;
    }

    public void init() {

        mFeedRepository.find(new FeedRepository.Callback() {
            @Override
            public void accept(Feed feed) {
                mFeed.setValue(feed);
            }
        });
    }
}
