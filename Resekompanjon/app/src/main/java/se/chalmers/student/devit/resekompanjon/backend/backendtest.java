package se.chalmers.student.devit.resekompanjon.backend;

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

import se.chalmers.student.devit.resekompanjon.backend.utils.JsonInfoExtract;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResultTrips;

/**
 * Created by emmafahlen on 2015-10-06.
 */
public class backendtest {

    public static void main(String[] args) throws IOException {
       /* JsonObject object = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/departureBoard?authKey=83cdc6c1-0614-453e-97ec-4b0158227330&format=json&jsonpCallback=processJSON&id=9021014004090000","83cdc6c1-0614-453e-97ec-4b0158227330", "DepartureBoard");
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
        JsonObject trip = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/trip?authKey=83cdc6c1-0614-453e-97ec-4b0158227330&format=json&jsonpCallback=processJSON&date=2015-10-08&time=19:07&originId=kungs&destId=kors", "83cdc6c1-0614-453e-97ec-4b0158227330", "TripList");
        JsonInfoExtract trips = new JsonInfoExtract(trip);
        ArrayList<SearchResultTrips> tripList = trips.getTripAdvice();
        System.out.println(tripList.get(0).getName());
        System.out.println(tripList.get(0).getType());
        System.out.println(tripList.get(0).getOriginType());
        // System.out.println(tripList.get(0).getOrigin$());
        //System.out.println(tripList.get(0).getDepartureDate());
        //System.out.println(tripList.get(0).getOriginId());
        //System.out.println(tripList.get(0).getOriginRouteIdx());
        //System.out.println(tripList.get(0).getOriginRtDate());
        //System.out.println(tripList.get(0).getDepartureTime());
        //System.out.println(tripList.get(0).getOriginRtTime());
        //System.out.println(tripList.get(0).getOriginTrack());
        //System.out.println(tripList.get(0).getDestination$());
        //System.out.println(tripList.get(0).getDestinationDate());
        //System.out.println(tripList.get(0).getDestinationId());

        //System.out.println(tripList.get(0).getOriginName());
        /*JsonObject route = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/journeyDetail?ref=294603%2F99224%2F961286%2F382455%2F80%3Fdate%3D2015-10-08%26station_evaId%3D4090004%26station_type%3Ddep%26authKey%3D83cdc6c1-0614-453e-97ec-4b0158227330%26format%3Djson%26jsonpCallback%3DprocessJSON%26","83cdc6c1-0614-453e-97ec-4b0158227330","JourneyDetail");
        JsonInfoExtract routes = new JsonInfoExtract(route);
        ArrayList<EntireTripRoute> routeList = routes.getEntireTripRoute();
        System.out.println(routeList.get(0).getName());
        JsonObject ai = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/journeyDetail?ref=294603%2F99224%2F961286%2F382455%2F80%3Fdate%3D2015-10-08%26station_evaId%3D4090004%26station_type%3Ddep%26authKey%3D83cdc6c1-0614-453e-97ec-4b0158227330%26format%3Djson%26jsonpCallback%3DprocessJSON%26","83cdc6c1-0614-453e-97ec-4b0158227330","JourneyDetail");
        JsonInfoExtract air = new JsonInfoExtract(ai);
        ArrayList<AdditionalInfoRoute> airList = air.getAdditionalInfoRoute();
        System.out.println(airList.get(0).getStroke());
        JsonObject a = downloadApiInformation("http://api.vasttrafik.se/bin/rest.exe/v1/geometry?ref=663840%2F222303%2F585680%2F71573%2F80%26authKey%3D83cdc6c1-0614-453e-97ec-4b0158227330%26format%3Djson%26jsonpCallback%3DprocessJSON%26","83cdc6c1-0614-453e-97ec-4b0158227330","Geometry");
        JsonInfoExtract aa = new JsonInfoExtract(a);
        ArrayList<GeometryRef> gfList = aa.getGeometryRef();
        System.out.println(gfList.get(0).getLat());*/
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

            //JsonElement jsonResponse = new JsonParser().parse(contentAsString.substring(13, contentAsString.length() - 2));

            //apiData = jsonResponse.getAsJsonObject();

            apiData = root.getAsJsonObject();
            return apiData;

            ///.get(json).getAsJsonObject();

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
            buffer.append(chars, 0, len);
        return buffer.toString();
    }
}
