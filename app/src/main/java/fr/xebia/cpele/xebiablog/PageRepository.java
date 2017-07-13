package fr.xebia.cpele.xebiablog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import jonas.tool.save_page.PageSaver;

class PageRepository {

    @NonNull
    private final ExecutorService mExecutorService;
    @NonNull
    private Context mContext;

    @NonNull
    private HashMap<String, LiveData<File>> mCache;

    PageRepository(@NonNull ExecutorService executorService, @NonNull Context context) {
        mExecutorService = executorService;
        mContext = context;
        mCache = new HashMap<>();
    }

    LiveData<File> findOne(String url) {

        if (mCache.get(url) != null) return mCache.get(url);

        MutableLiveData<File> liveData = new MutableLiveData<>();

        mExecutorService.submit(() -> {

            PageSaver pageSaver = new PageSaver(new DummyPageSaveCallback());
            String outputDirPath = mContext.getCacheDir().getPath()
                    + File.separator + "pages"
                    + File.separator + toUnsigned(url.hashCode());
            pageSaver.getPage(url, outputDirPath, "index.html");

            String pageIndexPath = outputDirPath + File.separator + "index.html";
            File pageIndexFile = new File(pageIndexPath);
            Looper mainLooper = mContext.getMainLooper();
            new Handler(mainLooper).post(() -> liveData.setValue(pageIndexFile));
        });

        mCache.put(url, liveData);
        return liveData;
    }

    private static long toUnsigned(int signed) {
        return signed & 0x00000000ffffffffL;
    }

    private static class DummyPageSaveCallback implements PageSaver.EventCallback {

        @Override
        public void onProgressChanged(final int i, final int i1, final boolean b) {
        }

        @Override
        public void onProgressMessage(final String s) {
        }

        @Override
        public void onPageTitleAvailable(final String s) {
        }

        @Override
        public void onLogMessage(final String s) {
        }

        @Override
        public void onError(final Throwable throwable) {
        }

        @Override
        public void onError(final String s) {
        }

        @Override
        public void onFatalError(final Throwable throwable, final String s) {
        }
    }
}
