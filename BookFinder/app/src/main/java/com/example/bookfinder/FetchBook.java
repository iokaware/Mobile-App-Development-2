package com.example.bookfinder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchBook extends AsyncTask<String, Void, String> {

    final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    final String QUERY = "q";
    final String MAX_RESULTS = "maxResults";
    final String PRINT_TYPE = "printType";

    OkHttpClient client = new OkHttpClient();

     Context context = MainActivity.mainBinding.getRoot().getContext();

    @Override
    protected String doInBackground(String... strings) {

        HttpUrl.Builder builder = HttpUrl.parse(BASE_URL).newBuilder();
        builder.addQueryParameter(QUERY, strings[0])
                .addQueryParameter(MAX_RESULTS, "10")
                .addQueryParameter(PRINT_TYPE, "books");
        String url = builder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            List<Book> bookList = new ArrayList<>();

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                try {
                    bookList.add(new Book(
                            book.getString("id"),
//                            imageLinks.getString("thumbnail"),
                            "https://books.google.com/books/content?id="
                                    + book.getString("id")
                                    + "&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                            volumeInfo.getString("title"),
                            volumeInfo.getString("authors"),
                            volumeInfo.getString("publisher"),
                            volumeInfo.getString("publishedDate"),
                            volumeInfo.getString("description")
                    ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (bookList.size() > 0) {
                MainActivity.mainBinding.progressBar.setVisibility(View.INVISIBLE);
                BookListAdapter bookListAdapter = new BookListAdapter(bookList);
                MainActivity.mainBinding.recyclerView.setAdapter(bookListAdapter);
                MainActivity.mainBinding.recyclerView.setLayoutManager(
                        new LinearLayoutManager(context));
            } else {
                MainActivity.mainBinding.progressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(MainActivity.mainBinding.searchButton, "No Results Found!", Snackbar.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            MainActivity.mainBinding.progressBar.setVisibility(View.INVISIBLE);
            Snackbar.make(MainActivity.mainBinding.searchButton, "No Results Found!", Snackbar.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        MainActivity.mainBinding.progressBar.setVisibility(View.VISIBLE);
    }
}
