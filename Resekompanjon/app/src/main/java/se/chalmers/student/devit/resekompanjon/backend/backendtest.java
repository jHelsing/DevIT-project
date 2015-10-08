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
        /*JsonObject object = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/departureBoard?authKey=83cdc6c1-0614-453e-97ec-4b0158227330&format=json&jsonpCallback=processJSON&id=9021014004090000","83cdc6c1-0614-453e-97ec-4b0158227330", "DepartureBoard");
        JsonObject searchString = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/location.name?authKey=83cdc6c1-0614-453e-97ec-4b0158227330&format=json&jsonpCallback=processJSON&input=kungsports","83cdc6c1-0614-453e-97ec-4b0158227330", "LocationList");
        JsonObject stopsNear = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/location.nearbystops?authKey=83cdc6c1-0614-453e-97ec-4b0158227330&format=json&jsonpCallback=processJSON&originCoordLat=57.703834&originCoordLong=11.966404&maxNo=30","83cdc6c1-0614-453e-97ec-4b0158227330","LocationList");
        JsonObject adress = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/location.nearbyaddress?authKey=83cdc6c1-0614-453e-97ec-4b0158227330&format=json&jsonpCallback=processJSON&originCoordLat=57.703834&originCoordLong=11.966404","83cdc6c1-0614-453e-97ec-4b0158227330","LocationList");
        JsonInfoExtract info = new JsonInfoExtract(object);
        JsonInfoExtract infoString = new JsonInfoExtract(searchString);
        JsonInfoExtract infoNear = new JsonInfoExtract(stopsNear);
        JsonInfoExtract infoAdress = new JsonInfoExtract(adress);
        ArrayList<VehicleInfo> vehiclelist = info.getAllVehiclesFromThisStop();
        ArrayList<StopsFromString> stopList = infoString.getStopsFromSearchString();
        ArrayList<StopsNearby> nearList = infoNear.getStopsNearby();
        AdressNearby adressNear = infoAdress.getAdressNearby();
        System.out.println(vehiclelist.get(0).getJourneydetailref());
        System.out.println(stopList.get(0).getName());
        System.out.println(nearList.get(0).getId());
        System.out.println(adressNear.getName());*/
        JsonObject trip = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/trip?authKey=83cdc6c1-0614-453e-97ec-4b0158227330&format=json&jsonpCallback=processJSON&date=2015-10-09&time=08.18&originId=Kungsportsplatsen&destId=Brunnsparken","83cdc6c1-0614-453e-97ec-4b0158227330","TripList");
        JsonInfoExtract trips = new JsonInfoExtract(trip);
        ArrayList<SearchResaultTrips> tripList = trips.getTripAdvice();
        System.out.println(tripList.get(0).getName());
    }

    private static JsonObject downloadApiInformation(String myUrl, String key, String json) throws IOException {
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
            return apiData.get(json).getAsJsonObject();

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
