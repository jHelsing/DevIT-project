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

    public JsonInfoExtract(JsonObject jobj){
        this.json = jobj;
    }

    //public String getSpecificBusstop(){
     //   this.json.getAsJsonObject("TripList").
    //}

    public ArrayList<VehicleInfo> getAllVehiclesFromThisStop(){
        Gson gson = new Gson();
        JsonArray array = this.json.get("Departure").getAsJsonArray();
        for(int i=0; i<array.size(); i++){
            viArrayList.add(gson.fromJson(array.get(i), VehicleInfo.class));
        }
        return viArrayList;
    }

    //hämta alla busstop längst vägen
    //hämta alla tider längst vägen
    //hämta en specifik hållplats
    //
}
