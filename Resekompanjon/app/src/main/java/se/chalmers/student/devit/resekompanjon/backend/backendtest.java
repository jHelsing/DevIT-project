package se.chalmers.student.devit.resekompanjon.backend;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
import java.util.ArrayList;

/**
 * Created by emmafahlen on 2015-10-06.
 */
public class backendtest {

    public static void main(String[] args) throws IOException {
        JsonObject object = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/departureBoard?authKey=83cdc6c1-0614-453e-97ec-4b0158227330&format=json&jsonpCallback=processJSON&id=9021014004090000","83cdc6c1-0614-453e-97ec-4b0158227330");
        JsonInfoExtract info = new JsonInfoExtract(object);
        ArrayList<VehicleInfo> vehiclelist = info.getAllVehiclesFromThisStop();
        System.out.println(vehiclelist.get(0).getName());
    }

    private static JsonObject downloadApiInformation(String myUrl, String key) throws IOException {
        InputStream inputStream = null;
        JsonObject apiData;

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
            inputStream = conn.getInputStream();
            String contentAsString = readIt(inputStream);
            JsonElement root = new JsonParser().parse(contentAsString.substring(13, contentAsString.length() - 2));

            apiData = root.getAsJsonObject();
            return apiData.get("DepartureBoard").getAsJsonObject();

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
    // Reads an InputStream and converts it to a String.
    private static String readIt(InputStream stream) throws IOException {
        int len;
        Reader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        char[] chars = new char[1024];
        while ((len = reader.read(chars)) != -1)
            buffer.append(chars,0,len);
        return buffer.toString();
    }
}
