package fr.xebia.cpele.xebiablog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

public class FeedViewModel extends ViewModel {

    private MutableLiveData<Feed> mFeed = new MutableLiveData<>();

    public LiveData<Feed> getFeed() {
        return mFeed;
    }

    public void init() {

        if (mFeed.getValue() != null) {
            Log.d(getClass().getSimpleName(), "Feed already fetched, no initialization needed");
            return;
        }

        FeedRepository repo = new FeedRepository(App.instance().provideApi());
        repo.find(new FeedRepository.Callback() {
            @Override
            public void accept(Feed feed) {
                mFeed.setValue(feed);
            }
        });
    }
}
