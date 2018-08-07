package edu.gatech.seclass.booksearch;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    private ProgressBar mProgressBar;
    private ListView mListView;
    private String mURL;

    private BookAdapter mAdapter;
    private Context mCallBacks;
    private EditText searchBox;
    private android.app.LoaderManager loaderManager;
    private TextView mTextView;


    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get all Views
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mListView = (ListView) findViewById(R.id.list_view);
        Button searchButton = (Button) findViewById(R.id.search_button);
        mCallBacks = this;
        searchBox = (EditText) findViewById(R.id.search_box);
        mProgressBar.setVisibility(View.GONE);
        mTextView = (TextView) findViewById(R.id.text_view);

        mAdapter = new BookAdapter(mCallBacks,0,  new ArrayList<Book>());
        mListView.setAdapter(mAdapter);

        loaderManager = getLoaderManager();
        loaderManager.initLoader(i, null, this);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        mListView.setEmptyView(mTextView);

        if(!isConnected){
            mProgressBar.setVisibility(View.GONE);
            mTextView.setText("No network connection");
        }

        //get data from EditText
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUI();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = mAdapter.getItem(i);

                Uri bookUri = Uri.parse(book.getmPreviewURL());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                startActivity(websiteIntent);
            }
        });
    }

    public void updateUI(){
        String keyword = searchBox.getText().toString();

        mURL = "https://www.googleapis.com/books/v1/volumes?q=" + keyword + "&maxResults=40";
        //the loaderManager needs to be destroyed in order to initialize it again and maintain
        loaderManager.destroyLoader(i);
        loaderManager.initLoader(i, null, MainActivity.this);
    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int i, @Nullable Bundle bundle) {
        i++;
        mProgressBar.setVisibility(View.VISIBLE);
        return new BookLoader(this, mURL);
    }

    //in order to override this method make sure when implementing LoaderCallbacks
    //to also write down the type of Loader that will be used in the implements statement above
    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        if(books != null && !books.isEmpty()){
            mAdapter.addAll(books);
        }else{
            mTextView.setText("No books found");
        }

        mProgressBar.setVisibility(View.GONE);
        return;
    }


    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mAdapter.clear();
    }
}
