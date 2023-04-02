package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

public class MyAsyncTask extends AsyncTask<Void, Integer, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        MainActivity.mainBinding.textView.setText("Napping...");
        MainActivity.mainBinding.progressBar.setProgress(0);
        MainActivity.mainBinding.textView2.setText("0/100");
    }

    @Override
    protected String doInBackground(Void... voids) {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            publishProgress(10);
        }
        return "Awake after sleeping for 10 seconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        MainActivity.mainBinding.progressBar.incrementProgressBy(values[0]);
        MainActivity.mainBinding.textView2.setText(
                MainActivity.mainBinding.progressBar.getProgress() + "/100"
        );
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MainActivity.mainBinding.textView.setText(s);
    }
}
