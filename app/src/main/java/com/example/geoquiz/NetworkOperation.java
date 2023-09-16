package com.example.geoquiz;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.Executor;

class NetworkOperation extends AsyncTask<Void, Void, Void> {
    // AsyncResponse stuff, to get the result back!
    public AsyncResponse delegate = null;

    public interface AsyncResponse {
        public void processFinish(String output);
    }

    public NetworkOperation(AsyncResponse delegate){
        this.delegate = delegate;
    }

    // AsyncTask stuff to run stuff in background on another thread and so on
    private String mString;
    @Override
    protected Void doInBackground(Void... params) {
        try {
            testMe();
            //testInternet();
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Void... params) {
        // Nothing
    }


    @Override
    protected void onPostExecute(Void param){
        delegate.processFinish(mString);
    }

    // Functions to test the stuff
    private void testMe() throws Exception{
        URL oracle = new URL("https://opentdb.com/api.php?amount=10&type=multiple");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null){
            System.out.println(inputLine);
            mString = inputLine;
        }
        in.close();
    }

    private void testInternet(){
        try {
            URL url = new URL("https://opentdb.com/api.php?amount=10&type=multiple");
            try {
                url.openConnection();
                InputStream reader = url.openStream();
            }catch(IOException e){
                ;
            }
        }catch(MalformedURLException e){
            ;
        }
        ;
    }

}