package com.example.downloadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... urls) {
        // Perform the download and processing of the image in the background
        String imageUrl = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream input = new java.net.URL(imageUrl).openStream();
//            URL url = new URL(imageUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        saveImageToDevice(bitmap);

        return bitmap;
    }

    private void saveImageToDevice(Bitmap bitmap) {
        // First, you need to check if external storage is available and writable
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {


            // Define the directory where the image will be saved
            File directory = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "MyAppFolder");

            // Create the directory if it doesn't exist
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate a unique filename for the image
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = "IMG_" + timestamp + ".jpg";

            // Create the file object for the image
            File file = new File(directory, filename);

            // Save the bitmap to the file
            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Notify the MediaScanner to scan the new image file and make it available in the Gallery
            MediaScannerConnection.scanFile(MainActivity.mainBinding.getRoot().getContext(), new String[]{file.getPath()}, null, null);
        }
}

    @Override
    protected void onPostExecute(Bitmap result) {
        // Update the UI with the downloaded image
        MainActivity.mainBinding.progressBar2.setVisibility(View.INVISIBLE);
        MainActivity.mainBinding.imageView.setImageBitmap(result);
        MainActivity.mainBinding.textView.setText("Image saved to gallery!");
    }

    @Override
    protected void onPreExecute() {
        // Show a progress bar or other UI element indicating that the image is being downloaded
        MainActivity.mainBinding.progressBar2.setVisibility(View.VISIBLE);
        MainActivity.mainBinding.textView.setText(R.string.downloading);

    }
}