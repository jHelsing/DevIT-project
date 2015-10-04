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
    //Can't have key in program as it ends up publically on github
    //TODO: Figure out a way to read api-key? or we have to enter it manually before running
    private static final String key= "83cdc6c1-0614-453e-97ec-4b0158227330";
    ConnectivityManager connMgr;
    String apiData;
    OnTaskCompleted listener;

    public VasttrafikBackend(Context context, OnTaskCompleted listener){
        this.listener = listener;
        connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void vastTrafikConnect(String url, OnTaskCompleted listener) throws NoConnectionException {
        if (isConnectedToInternet()) {
            new DownloadApiData(listener).execute(url);
        } else {
            throw new NoConnectionException();
        }
    }

    private boolean isConnectedToInternet() {
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
            Log.d(DEBUG_TAG, "The response is: " + response); // DEBUG
            inputStream = conn.getInputStream();

            //Not sure if needed? We want the JSON
            String contentAsString = readIt(inputStream);
            return contentAsString;

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private class DownloadApiData extends AsyncTask<String, Void, String> {
        private OnTaskCompleted listener;

        public DownloadApiData(OnTaskCompleted listener){
            this.listener = listener;
        }

        //Can't have key in program as it ends up publically on github
        //TODO: Figure out a way to read api-key? or we have to enter it manually before running

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadApiInformation(urls[0], key);
            } catch (IOException e) {
                return "Unable to retrieve information, URL may be invalid";
            }
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            apiData = result;
            listener.onTaskCompleted();
            //Log.d("result:", result);

        }
    }
    public void getStationbyName(String stop) {
        String url = "http://api.vasttrafik.se/bin/rest.exe/v1/location.name?authKey=" + key + "&format=json&jsonpCallback=processJSON&input=" + stop;
        try {
            vastTrafikConnect(url, listener);
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }
    public void getAllStops()  {
        String url = "http://api.vasttrafik.se/bin/rest.exe/v1/location.allstops?authKey=" + key + "&format=json&jsonpCallback=processJSON";
        try {
            vastTrafikConnect(url, listener);
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Not sure if Origin and dest should be strings, ints or doubles?
     * @param origin
     * @param dest
     */
    public void getTripID(String origin, String dest) {
        String url = "http://api.vasttrafik.se/bin/rest.exe/v1/trip?authKey=" + key + "&format=json&jsonpCallback=processJSON" + "&originId=" +
                origin + "&destId=" + dest;
        try {
            vastTrafikConnect(url, listener);
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }
    public void getTripCoord(Double originLat, Double originLong, String originName, Double destLat, Double destLong, String destName){
        String url = "http://api.vasttrafik.se/bin/rest.exe/v1/trip?authKey="+ key + "&format=json&jsonpCallback=processJSON&originCoordLat=" + originLat +
                "&originCoordLong=" + originLong + " &originCoordName=" + originName + "&destCoordLat=" + destLat + "&destCoordLong=" + destLong + "&destCoordName=" +
                destName;
        try {
            vastTrafikConnect(url, listener);
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }
    public String getApiData(){
        return apiData;
    }
}
