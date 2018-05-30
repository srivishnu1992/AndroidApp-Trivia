package com.example.srivi.trivia;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by srivi on 16-02-2018.
 */

public class GetDataAsync extends AsyncTask<String, Void, String> {

    ArrayList<String> questions = new ArrayList<>(  );
    HttpURLConnection httpURLConnection = null;
    IData iData;
    String[] options;
    BufferedReader bufferedReader;

    GetDataAsync(IData iData) {
        this.iData = iData;
    }

    @Override
    protected void onPostExecute(String s) {
        for(String temp: questions)
            Log.d("API", temp);
        Log.d( "API", "Retrieving over");
        iData.handleData( questions );
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL( strings[0] );
            httpURLConnection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader( new InputStreamReader( httpURLConnection.getInputStream() ) );
            String line = "";
            while((line = bufferedReader.readLine())!=null)
                questions.add( line );

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(httpURLConnection!=null)
                httpURLConnection.disconnect();
            if(bufferedReader!=null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    public static interface IData {
        public void handleData(ArrayList<String> data);
    }

}
