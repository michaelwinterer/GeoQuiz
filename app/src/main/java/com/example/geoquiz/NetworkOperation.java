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
    private String mString;
    @Override
    protected Void doInBackground(Void... params) {
        try {
            testMe();
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Void r){
        mString = mString;
    }

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
            URL url = new URL("http://info.cern.ch/hypertext/WWW/TheProject.html");
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



    private void testInternetProoven() throws Exception{
        URL oracle = new URL("https://opentdb.com/api.php?amount=10&type=multiple");

        check_connection(oracle);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }

    private int check_connection(URL url) throws Exception{
        HttpURLConnection http_url_conn = (HttpURLConnection) url.openConnection();
        //http_url_conn.setInstanceFollowRedirects(false);
        //http_url_conn.setRequestProperty("accept-language", "en-US,en;q=0.9");
        //http_url_conn.setRequestProperty("user-agent", "MyJavaApp");
        //http_url_conn.setRequestProperty( "charset", "utf-8");
        int ret = http_url_conn.getResponseCode();
        return ret;
    }

    private void testInternetOracle() throws Exception{
        System.setProperty("java.net.useSystemProxies", "true");
        URL oracle = new URL("file:///C:/xampp/htdocs/wordpress/readme.html");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }

}