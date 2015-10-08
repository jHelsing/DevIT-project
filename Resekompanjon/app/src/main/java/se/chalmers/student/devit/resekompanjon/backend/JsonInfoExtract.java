package se.chalmers.student.devit.resekompanjon.backend;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by emmafahlen on 2015-10-06.
 */
public class JsonInfoExtract {
JsonObject json;
ArrayList<VehicleInfo> viArrayList = new ArrayList<>();
    ArrayList<StopsFromString> sfsArrayList = new ArrayList<>();
    ArrayList<StopsNearby> snArrayList = new ArrayList<>();
    ArrayList<SearchResaultTrips> srtArrayList = new ArrayList<>();


    public JsonInfoExtract(JsonObject jobj){
        this.json = jobj;
    }

    //public String getSpecificBusstop(){
     //   this.json.getAsJsonObject("TripList").
    //}

    //Gets all vehicles that departs from a specific busstop, converts them to VehicleInfo objects,
    // puts them in an arrayList. All the info about the vehicles can be found in the getters in VehicleInfo.
    //OBS! The different vehicles API for their unic route can be found by calling getJourneydetailref().
    public ArrayList<VehicleInfo> getAllVehiclesFromThisStop(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("Departure").getAsJsonArray();
        for(int i=0; i<array.size(); i++){
            viArrayList.add(gson.fromJson(array.get(i), VehicleInfo.class));
        }
        return viArrayList;
    }

    //Gets all the stops that match a specific string, converts into StopsFromString objects, puts them in an arrayList.
    // All the info about the vehicles can be found in the getters in StopsFromString.
    public ArrayList<StopsFromString> getStopsFromSearchString(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("StopLocation").getAsJsonArray();
        for(int i=0; i<array.size(); i++){
            sfsArrayList.add(gson.fromJson(array.get(i), StopsFromString.class));
        }
        return sfsArrayList;
    }

    public ArrayList<StopsNearby> getStopsNearby(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("StopLocation").getAsJsonArray();
        for(int i=0; i<array.size(); i++){
            snArrayList.add(gson.fromJson(array.get(i), StopsNearby.class));
        }
        return snArrayList;
    }

    public AdressNearby getAdressNearby(){
        Gson gson = new Gson();
        JsonObject obj = this.json.get("CoordLocation").getAsJsonObject();
        return gson.fromJson(obj, AdressNearby.class);
    }

    public ArrayList<SearchResaultTrips> getTripAdvice(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("Trip").getAsJsonArray();
        for(int i=0; i<array.size(); i++){
            srtArrayList.add(gson.fromJson(array.get(i), SearchResaultTrips.class));
        }
        return srtArrayList;
    }



    //hämta alla busstop längst vägen
    //hämta alla tider längst vägen
    //hämta en specifik hållplats
    //
}
