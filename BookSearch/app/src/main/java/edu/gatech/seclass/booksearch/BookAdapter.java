package edu.gatech.seclass.booksearch;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    //constructor
    public BookAdapter(@NonNull Context context, int resource, ArrayList<Book> Books) {
        super(context, resource, Books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);
        TextView authorView = (TextView) listItemView.findViewById(R.id.author_box);
        TextView titleView = (TextView) listItemView.findViewById(R.id.title_box);
        TextView yearView = (TextView) listItemView.findViewById(R.id.year_box);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_box);
        TextView subtitleview = (TextView) listItemView.findViewById(R.id.subtitle_box);

        if(currentBook.getmSubtitle()!= ""){
            subtitleview.setText(currentBook.getmSubtitle());
        }else{
            subtitleview.setVisibility(View.GONE);
        }

        authorView.setText(currentBook.getmAuthor());
        titleView.setText(currentBook.getmTitle());
        yearView.setText(currentBook.getmYear());
        if(currentBook.getmImage() != null){
            imageView.setImageBitmap(currentBook.getmImage());
        }else{
            imageView.setImageResource(R.drawable.no_thumb);
        }


        return listItemView;
    }
}
