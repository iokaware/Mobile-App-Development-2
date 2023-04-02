package com.example.bookfinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadImage extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public LoadImage(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        // Perform the download and processing of the image in the background
        String imageUrl = urls[0];
        Bitmap bitmap = null;
        try {
//            InputStream input = new java.net.URL(imageUrl).openStream();
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        // Update the UI with the downloaded image
        imageView.setImageBitmap(result);
    }
}