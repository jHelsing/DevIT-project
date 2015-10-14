package se.chalmers.student.devit.resekompanjon.backend.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.utils.json.AdditionalInfoRoute;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.AdressNearby;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.EntireTripRoute;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.GeometryRef;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.StopsFromString;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.StopsNearby;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.VehicleInfo;

/**
 * Created by emmafahlen on 2015-10-06.
 */
public class JsonInfoExtract {
JsonObject json;
ArrayList<VehicleInfo> viArrayList = new ArrayList<>();
    ArrayList<StopsFromString> sfsArrayList = new ArrayList<>();
    ArrayList<StopsNearby> snArrayList = new ArrayList<>();
    ArrayList<SearchResaultTrips> srtArrayList = new ArrayList<>();
    ArrayList<EntireTripRoute> etrArrayList = new ArrayList<>();
    ArrayList<AdditionalInfoRoute> airArrayList = new ArrayList<>();
    ArrayList<GeometryRef> grArrayList = new ArrayList<>();



    public JsonInfoExtract(JsonObject jobj){
        this.json = jobj;
    }

    //Gets all vehicles that departs from a specific busstop, converts them to VehicleInfo objects,
    // puts them in an arrayList. All the info about the vehicles can be found in the getters in VehicleInfo.
    //OBS! The different vehicles API for their unic route can be found by calling getJourneydetailref().
    public ArrayList<VehicleInfo> getAllVehiclesFromThisStop(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("DepartureBoard").getAsJsonObject().get("Departure").getAsJsonArray();
        if(array == null) {
            System.out.println("Wrong URL for this method");
        }
        for(int i=0; i<array.size(); i++){
            viArrayList.add(gson.fromJson(array.get(i), VehicleInfo.class));
        }
        return viArrayList;
    }

    //Gets all the stops that match a specific string, converts into StopsFromString objects, puts them in an arrayList.
    // All the info about the vehicles can be found in the getters in StopsFromString.
    public ArrayList<StopsFromString> getStopsFromSearchString(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("LocationList").getAsJsonObject().get("StopLocation").getAsJsonArray();
        if(array == null) {
            System.out.println("Wrong URL for this method");
        }
        for (int i = 0; i < array.size(); i++) {
            sfsArrayList.add(gson.fromJson(array.get(i), StopsFromString.class));
        }
        return sfsArrayList;

    }

    //Checks whats stops are nearby
    public ArrayList<StopsNearby> getStopsNearby(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("LocationList").getAsJsonObject().get("StopLocation").getAsJsonArray();
        if(array == null) {
            System.out.println("Wrong URL for this method");
        }
        for(int i=0; i<array.size(); i++){
            snArrayList.add(gson.fromJson(array.get(i), StopsNearby.class));
        }
        return snArrayList;
    }

    //Checks adress nearby
    public AdressNearby getAdressNearby(){
        Gson gson = new Gson();
        JsonObject obj = this.json.get("LocationList").getAsJsonObject().get("CoordLocation").getAsJsonObject();
        if(obj == null) {
            System.out.println("Wrong URL for this method");
        }
        return gson.fromJson(obj, AdressNearby.class);
    }

    //Use when user search for a trip (origin-dest). All information can be reached with
    //getters in SearchResaultTrips.
    public ArrayList<SearchResaultTrips> getTripAdvice(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("TripList").getAsJsonObject().get("Trip").getAsJsonArray();
        if(array == null) {
            System.out.println("Wrong URL for this method");
        }
        for(int i=0; i<array.size(); i++){
            if (array.get(i).getAsJsonObject().get("Leg").isJsonArray()) {
                JsonArray ar = array.get(i).getAsJsonObject().get("Leg").getAsJsonArray();
                for (int j = 0; j < ar.size(); j++) {
                    srtArrayList.add(gson.fromJson(ar.get(j), SearchResaultTrips.class));
                }
            } else if (array.get(i).getAsJsonObject().get("Leg").isJsonObject()){
                srtArrayList.add(gson.fromJson(array.get(i).getAsJsonObject().get("Leg").getAsJsonObject(), SearchResaultTrips.class));
            }
        }
        return srtArrayList;
    }

    //Gets all stops on the entire trip route, creates EntireTripRoute objects and puts them in an arrayList.
    //All the information about the objectes(stops) can be reached with getters in EntireTripRoute.
    public ArrayList<EntireTripRoute> getEntireTripRoute(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("JourneyDetail").getAsJsonObject().get("Stop").getAsJsonArray();
        if(array == null) {
            System.out.println("Wrong URL for this method");
        }
        for(int i=0; i<array.size(); i++){
            etrArrayList.add(gson.fromJson(array.get(i), EntireTripRoute.class));
        }
        return etrArrayList;
    }

    //Additional info to getEntireTripRoute (from same URL). Ugly code. Creates an arrayList of
    //AdditionalInfoRoutes objects. Color has get(0), GeometryRef has get(1) and so on. Will do something
    //about that later. All the info can be reached with getters and setters in AdditionalInfoRoutes.
    //OBS! GeometryRef is an URL with a list of longs and lats. To get those, use method getGeometryRef.
    public ArrayList<AdditionalInfoRoute> getAdditionalInfoRoute(){
        Gson gson = new Gson();
        JsonElement Color = this.json.get("JourneyDetail").getAsJsonObject().get("Color");
        JsonElement GeometryRef = this.json.get("JourneyDetail").getAsJsonObject().get("GeometryRef");
        JsonElement JourneyName = this.json.get("JourneyDetail").getAsJsonObject().get("JourneyName");
        JsonElement JourneyType = this.json.get("JourneyDetail").getAsJsonObject().get("JourneyType");
        JsonElement JourneyId = this.json.get("JourneyDetail").getAsJsonObject().get("JourneyId");
        JsonElement Direction = this.json.get("JourneyDetail").getAsJsonObject().get("Direction");
        airArrayList.add(gson.fromJson(Color, AdditionalInfoRoute.class));
        airArrayList.add(gson.fromJson(GeometryRef, AdditionalInfoRoute.class));
        airArrayList.add(gson.fromJson(JourneyName, AdditionalInfoRoute.class));
        airArrayList.add(gson.fromJson(JourneyType, AdditionalInfoRoute.class));
        airArrayList.add(gson.fromJson(JourneyId, AdditionalInfoRoute.class));
        airArrayList.add(gson.fromJson(Direction, AdditionalInfoRoute.class));
        return airArrayList;
    }

    //Gets longs and lats from a specific trip. Get info by using getters in GeometryRef.
    public ArrayList<GeometryRef> getGeometryRef() {
        Gson gson = new Gson();
        JsonArray array = this.json.get("Geometry").getAsJsonObject().get("Points").getAsJsonObject().get("Point").getAsJsonArray();
        if(array == null) {
            System.out.println("Wrong URL for this method");
        }
        for(int i=0; i<array.size(); i++){
            grArrayList.add(gson.fromJson(array.get(i), GeometryRef.class));
        }
        return grArrayList;
    }
}