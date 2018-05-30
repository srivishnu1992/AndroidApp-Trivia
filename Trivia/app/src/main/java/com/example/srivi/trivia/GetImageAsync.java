package com.example.srivi.trivia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by srivi on 18-02-2018.
 *
 */

public class GetImageAsync extends AsyncTask<String, Void, Bitmap> {

    HttpURLConnection httpURLConnection = null;
    Bitmap questionImage = null;
    IImage iImage;
    TriviaActivity triviaActivity;

    GetImageAsync(TriviaActivity triviaActivity) {
        this.triviaActivity = triviaActivity;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL( strings[0] );
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                questionImage = BitmapFactory.decodeStream( httpURLConnection.getInputStream() );
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionImage;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        triviaActivity.handleData( questionImage );
    }

    public static interface IImage {
        public void handleData(Bitmap image);
    }

}
