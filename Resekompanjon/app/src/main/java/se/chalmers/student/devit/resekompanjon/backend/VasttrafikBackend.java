package se.chalmers.student.devit.resekompanjon.backend;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author Marcus
 * @version 0.1
 */
public class VasttrafikBackend {
    private static final String DEBUG_TAG = "HttpExample";
    ConnectivityManager connMgr;

    public VasttrafikBackend(Context context){
        connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void vastTrafikConnect() throws NoConnectionException {

        String url = "http://api.vasttrafik.se/bin/rest.exe/v1/location.name?" +
                "authKey=83cdc6c1-0614-453e-97ec-4b0158227330&format=json&" +
                "jsonpCallback=processJSON&input=kungsports";

        if (isConnectedToInternet()) {
            new DownloadApiData().execute(url);
        } else {
            throw new NoConnectionException();
        }
    }

    private boolean isConnectedToInternet() {
        Log.i("Backend" , "Connected to interwebs");
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream) throws IOException {
        int len = 500;
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    private String downloadApiInformation(String myUrl, String key) throws IOException {
        InputStream inputStream = null;

        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", key);
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            inputStream = conn.getInputStream();

            String contentAsString = readIt(inputStream);
            return contentAsString;

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private class DownloadApiData extends AsyncTask<String, Void, String> {
        private String key = "83cdc6c1-0614-453e-97ec-4b0158227330";

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadApiInformation(urls[0], key);
            } catch (IOException e) {
                return "Unable to retrieve information, URL may be invalid";
            }
        }

        protected void onPostExecute(String result) {
            Log.d("result:", result);
        }
    }
}
