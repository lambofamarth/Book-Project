package edu.gatech.seclass.booksearch;


//creates a book object that contains information related to a single book.
public class Book {

    private String mTitle;
    private String mAuthor;
    private String mYear;

    public Book(String title, String author, String year){
        mTitle=title;
        mAuthor=author;
        mYear = year;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmYear() {
        return mYear;
    }
}
