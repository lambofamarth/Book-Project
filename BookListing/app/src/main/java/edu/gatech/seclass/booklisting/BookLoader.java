package edu.gatech.seclass.booklisting;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String mKeyword;


    public BookLoader(Context context, String keyword) {
        super(context);
        mKeyword=keyword;
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
        return null;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
