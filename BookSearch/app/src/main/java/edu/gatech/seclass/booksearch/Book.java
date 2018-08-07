package edu.gatech.seclass.booksearch;


import android.graphics.Bitmap;

//creates a book object that contains information related to a single book.
public class Book {

    private String mTitle;
    private String mSubtitle;
    private String mAuthor;
    private String mYear;
    private String mPreviewURL;
    private Bitmap mImage;

    public Book(String title, String subtitle, String author, String year, String previewUrl, Bitmap image){
        mTitle=title;
        mSubtitle = subtitle;
        mAuthor=author;
        mYear = year;
        mPreviewURL = previewUrl;
        mImage = image;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSubtitle() {
        return mSubtitle;
    }

    public String getmYear() {
        return mYear;
    }

    public String getmPreviewURL() {
        return mPreviewURL;
    }

    public Bitmap getmImage() {
        return mImage;
    }
}
