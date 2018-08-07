package edu.gatech.seclass.booksearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class QueryUtils {

    private static int mMaxResults;
      //Since this is a class containing helper methods to fetch json objects, no input is needed in constructor.
    private QueryUtils(){
    }

    public static ArrayList<Book> fetchBookData(String requestURL){
        //create url object
        URL url = createUrl(requestURL);
        //create a String to get the jsonresponse
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.e("error", "Problem making the HTTP request.", e);
        }
        ArrayList<Book> Books = extractFeatureFromJson(jsonResponse);
        return Books;

    }

    private static ArrayList<Book> extractFeatureFromJson(String jsonResponse) {
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        ArrayList<Book> Books = new ArrayList<>();

        try{
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray itemsArray = baseJsonResponse.getJSONArray("items");
            int totalItems = baseJsonResponse.getInt("totalItems");

            if(totalItems>40){
                totalItems = 40;
            }
            for(int i = 0; i < totalItems; i++){
                String mainAuthor = "No author found";
                String title = "No title found";
                String date = "No date found";
                String preview = "No preview URL available";
                String imageURL = "No thumbnail available";
                String subtitle = "";
                Bitmap bmp = null;

                JSONObject currentObject = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = currentObject.getJSONObject("volumeInfo");

                title = volumeInfo.getString("title");

                try{
                    subtitle = volumeInfo.getString("subtitle");
                }catch(JSONException e){
                    Log.e("queryUtils", "No value exists for subtitle", e);
                }

                try{
                    JSONArray authors = volumeInfo.getJSONArray("authors");
                    mainAuthor = authors.getString(0);
                }catch(JSONException e){
                    Log.e("queryUtils", "No value exists for authors", e);
                }

                try{
                    date = volumeInfo.getString("publishedDate");
                }catch(JSONException e){
                    Log.e("queryUtils", "No value exists for publishedDate", e);
                }

                try{
                    preview = volumeInfo.getString("previewLink");
                }catch(JSONException e){
                    Log.e("queryUtils", "No value exists for previewLink", e);
                }

                try{
                    JSONObject images = volumeInfo.getJSONObject("imageLinks");
                    imageURL = images.getString("thumbnail");
                    InputStream in = new URL(imageURL).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                }catch(JSONException e){
                    Log.e("queryUtils", "No thumbnails available", e);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Books.add(new Book(title, subtitle, mainAuthor, date, preview, bmp));
                Log.d("count", String.valueOf(i));

            }
        }catch(JSONException e){
            Log.e("QueryUtils", "Problem parsing the earthquake JSOn results", e);
        }

        return Books;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        //try to make a http connection
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e("error", "Error response code: " + urlConnection.getResponseCode());
            }

        }catch (IOException e){
            Log.e("error", "Problem retrieving the earthquake JSOn results.", e);
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //builds a url object checking to see if the url is not malformed
    private static URL createUrl(String requestURL) {
        URL url = null;
        try{
            url = new URL(requestURL);
        }catch(MalformedURLException e){
            Log.e("queryUtils", "Problem building the URL", e);
        }
        return url;
    }

}
