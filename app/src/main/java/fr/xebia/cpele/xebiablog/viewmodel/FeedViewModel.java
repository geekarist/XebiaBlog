package fr.xebia.cpele.xebiablog.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import fr.xebia.cpele.xebiablog.App;
import fr.xebia.cpele.xebiablog.model.api.Feed;
import fr.xebia.cpele.xebiablog.model.FeedRepository;

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

    public LiveData<Feed> get() {
        if (mFeed == null) mFeed = new MutableLiveData<>();
        mFeedRepository.find(mFeed::setValue);
        return mFeed;
    }
}
