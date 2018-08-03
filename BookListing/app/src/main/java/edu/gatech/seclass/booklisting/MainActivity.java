package edu.gatech.seclass.booklisting;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    private ProgressBar mProgressBar;
    private ListView mListView;
    private String mKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get all Views
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mListView = (ListView) findViewById(R.id.list_view);
        Button searchButton = (Button) findViewById(R.id.search_button);
        final EditText searchBox = (EditText) findViewById(R.id.search_box);

        mProgressBar.setVisibility(View.GONE);

        //get data from EditText
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKeyword = searchBox.getText().toString();
            }
        });

    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int i, @Nullable Bundle bundle) {
        mProgressBar.setVisibility(View.VISIBLE);
        return new BookLoader(this, mKeyword);
    }

    //in order to override this method make sure when implementing LoaderCallbacks
    //to also write down the type of Loader that will be used in the implements statement above
    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        return;
    }


    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
