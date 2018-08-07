package edu.gatech.seclass.booksearch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String mUrlString;


    public BookLoader(Context context, String urlString) {
        super(context);
        mUrlString = urlString;
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
        if(mUrlString == null || mUrlString.isEmpty()){
            return null;
        }
        ArrayList<Book> books = QueryUtils.fetchBookData(mUrlString);
        return books;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
