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
    ArrayList<EntireTripRoute> etrArrayList = new ArrayList<>();



    public JsonInfoExtract(JsonObject jobj){
        this.json = jobj;
    }

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

    //Use when user search for a trip (origin-dest). All information can be reached with
    //getters in SearchResaultTrips.
    public ArrayList<SearchResaultTrips> getTripAdvice(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("Trip").getAsJsonArray();
        for(int i=0; i<array.size(); i++){
            JsonArray ar = array.get(i).getAsJsonObject().getAsJsonArray("Leg");
            for(int j=0; j<ar.size(); j++){
                srtArrayList.add(gson.fromJson(ar.get(j), SearchResaultTrips.class));
            }
        }
        return srtArrayList;
    }

    //Gets all stops on the entire trip route, creates EntireTripRoute objects and puts them in an arrayList.
    //All the information about the objectes(stops) can be reached with getters in EntireTripRoute.
    public ArrayList<EntireTripRoute> getEntireTripRoute(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("Stop").getAsJsonArray();
        for(int i=0; i<array.size(); i++){
            etrArrayList.add(gson.fromJson(array.get(i), EntireTripRoute.class));
        }
        return etrArrayList;
    }



    //hämta alla busstop längst vägen
    //hämta alla tider längst vägen
    //hämta en specifik hållplats
    //
}
