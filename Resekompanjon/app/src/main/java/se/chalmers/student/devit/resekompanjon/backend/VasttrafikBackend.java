package se.chalmers.student.devit.resekompanjon.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private JsonObject apiData;
    OnTaskCompleted listener;

    public VasttrafikBackend(Context context, OnTaskCompleted listener){
        this.listener = listener;
        connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void vastTrafikConnect(String url) throws NoConnectionException {
        if (isConnectedToInternet()) {
            new DownloadApiData().execute(url);
        } else {
            throw new NoConnectionException();
        }
    }

    private boolean isConnectedToInternet() {
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    // Reads an InputStream and converts it to a String.
    public String readInputStream(InputStream stream) throws IOException {
        int len;
        Reader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        char[] chars = new char[1024];
        while ((len = reader.read(chars)) != -1)
            buffer.append(chars,0,len);
        return buffer.toString();
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
            String contentAsString = readInputStream(inputStream);
            Log.d("char", contentAsString.length()+"");

            //UGLY FIX
            //TODO: FIgure out a way to remove unnecessary characters some other way
            JsonElement root = new JsonParser().parse(contentAsString.substring(13, contentAsString.length() - 2));

            apiData = root.getAsJsonObject();

            Log.d("http:", myUrl);

            return "Success ApiData downloaded";

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private class DownloadApiData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadApiInformation(urls[0], key);
            } catch (IOException e) {
                return "Unable to retrieve information, URL may be invalid";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            listener.onTaskCompleted();
        }
    }

    public void getStationbyName(String stop) {
        String url = "http://api.vasttrafik.se/bin/rest.exe/v1/location.name?authKey=" + key + "&format=json&jsonpCallback=processJSON&input=" + stop;
        try {
            vastTrafikConnect(url);
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }
    public void getAllStops()  {
        String url = "http://api.vasttrafik.se/bin/rest.exe/v1/location.allstops?authKey=" + key + "&format=json&jsonpCallback=processJSON";
        try {
            vastTrafikConnect(url);
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
            vastTrafikConnect(url);
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }
    public void getTripCoord(Double originLat, Double originLong, String originName, Double destLat, Double destLong, String destName){
        String url = "http://api.vasttrafik.se/bin/rest.exe/v1/trip?authKey="+ key + "&format=json&jsonpCallback=processJSON&originCoordLat=" + originLat +
                "&originCoordLong=" + originLong + " &originCoordName=" + originName + "&destCoordLat=" + destLat + "&destCoordLong=" + destLong + "&destCoordName=" +
                destName;
        try {
            vastTrafikConnect(url);
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }
    public void getAllVehiclesFromStop(int id){
            String url = "http://api.vasttrafik.se/bin/rest.exe/v1/departureBoard?authKey=" + key + "&format=json&jsonpCallback=processJSON&id=+ " + id ;
        try {
            vastTrafikConnect(url);
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }

    public JsonObject getApiData(){
        return apiData;
    }
}
