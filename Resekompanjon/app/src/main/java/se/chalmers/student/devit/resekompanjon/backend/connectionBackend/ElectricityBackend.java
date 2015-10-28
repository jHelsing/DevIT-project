package se.chalmers.student.devit.resekompanjon.backend.connectionBackend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import se.chalmers.student.devit.resekompanjon.backend.utils.OnTaskCompleted;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.AssetsPropertyReader;

/**
 * @author Jonathan
 * @version 0.1
 */
public class ElectricityBackend  {

    private static final String DEBUG_TAG = "HttpExample";
    ConnectivityManager connMgr;
    private JsonArray apiData;
    OnTaskCompleted listener;
    private Context context;
    private static String key;

    public ElectricityBackend(Context context, OnTaskCompleted listener){
        this.listener = listener;
        this.context = context;
        connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        AssetsPropertyReader p = new AssetsPropertyReader(context);
        key = p.getPassword("electricityPassword");
    }

    private boolean isConnectedToInternet() {
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void electricityConnect(String url) throws NoConnectionException {
        if (isConnectedToInternet()) {
            new DownloadApiData().execute(url);
        } else {
            throw new NoConnectionException();
        }
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
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization ", key);
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response); // DEBUG

            inputStream = conn.getInputStream();
            String contentAsString = readInputStream(inputStream);
            Log.d("char", contentAsString.length() + "");

            JsonElement jsonResponse = new JsonParser().parse(contentAsString);

            apiData = jsonResponse.getAsJsonArray();

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

    public JsonArray getApiData(){
        return apiData;
    }

    public void getJourneyInfo() throws NoConnectionException{
        long t2 = System.currentTimeMillis();
        long t1 = t2 - (1000 * 120);
        String url = "https://ece01.ericsson.net:4443/ecity?dgw=Ericsson$Vin_Num_001&sensorSpec=Ericsson$Journey_Info&t1="
                + t1 + "&t2=" + t2;
        Log.d("Connection", "Attempting to connect to" + url);
        electricityConnect(url);
    }

    public void getNextStopInfo() throws NoConnectionException{
        long t2 = System.currentTimeMillis();
        long t1 = t2 - (1000 * 120);
        String url = "https://ece01.ericsson.net:4443/ecity?dgw=Ericsson$Vin_Num_001&sensorSpec=Ericsson$Next_Stop&t1="
                + t1 + "&t2=" + t2;
        Log.d("Connection", "Attempting to connect to" + url);
        electricityConnect(url);
    }

    public void getStopPressedInfo() throws NoConnectionException{
        long t2 = System.currentTimeMillis();
        long t1 = t2 - (1000 * 120);
        String url = "https://ece01.ericsson.net:4443/ecity?dgw=Ericsson$Vin_Num_001&sensorSpec=Ericsson$Stop_Pressed&t1="
                + t1 + "&t2=" + t2;
        Log.d("Connection", "Attempting to connect to" + url);
        electricityConnect(url);
    }
}
