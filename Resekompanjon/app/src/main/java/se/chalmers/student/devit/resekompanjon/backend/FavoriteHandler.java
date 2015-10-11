package se.chalmers.student.devit.resekompanjon.backend;

import android.content.Context;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Marcus on 2015-10-09.
 */
public class FavoriteHandler {

    private Context cont;
    private String result;
    private JsonArray tripArray;
    private String filePath;

    public FavoriteHandler (Context context){
        cont = context;
        filePath = cont.getFilesDir().getPath().toString() + "/favorites.txt";
        File file = new File(filePath);
        if (!file.exists()){
            try {
                file.createNewFile();
                Log.d("File not found", "Creating file for saving favorites");
                clearFavorites();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        readFavoriteTrips();
    }
    //TODO: Figure out what to return
    private void readFavoriteTrips() {
        try {
            InputStream inputStream = cont.openFileInput("favorites.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }

                inputStream.close();
                result = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        try { //If the file is wrong format clear file and start anew
            JsonElement jElement = new JsonParser().parse(result);
            tripArray = jElement.getAsJsonArray();
        } catch (IllegalStateException e) {
            Log.e("ERROR", "Failed to read JSON from file, clearing");
            clearFavorites();
        }

    }
    public void addToFavoriteTrips(String originName, String originID, String endName, String endID){
        readFavoriteTrips();

        JsonObject newTripObj = new JsonObject();
        newTripObj.addProperty("originName", originName);
        newTripObj.addProperty("originID", originID);
        newTripObj.addProperty("endName", endName);
        newTripObj.addProperty("endID", endID);
        tripArray.add(newTripObj);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(cont.openFileOutput("favorites.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(tripArray.toString());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Used to remove a single Favorite
     * @param i int that determines what favorite to remove, index in array to remove
     */
    public void removeFavorite(int i){
        readFavoriteTrips();
        tripArray.remove(i); //Maybe i-1 depending on how what i is sent
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(cont.openFileOutput("favorites.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(tripArray.toString());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    //Mostly added for testing but might be useful
    public void clearFavorites(){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(cont.openFileOutput("favorites.txt", Context.MODE_PRIVATE));
            outputStreamWriter.close();
            outputStreamWriter = new OutputStreamWriter(cont.openFileOutput("favorites.txt", Context.MODE_PRIVATE));
            JsonArray eArray = new JsonArray();
            outputStreamWriter.write(eArray.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getNumbOfFavorites(){ return tripArray.size(); }

    public JsonArray getTripArrayAsJson(){
        readFavoriteTrips();
        return tripArray;
    }

    public ArrayList<ArrayList> getTripArrayAsStrings(){
        readFavoriteTrips();
        ArrayList<String> stringTrip = new ArrayList<>();
        ArrayList<ArrayList> stringTripArray = new ArrayList<>();
        for (int i = 0; i < tripArray.size(); i++){

            JsonObject tempObj = tripArray.get(i).getAsJsonObject();
            stringTrip.add(tempObj.get("originName").toString());
            stringTrip.add(tempObj.get("originID").toString());
            stringTrip.add(tempObj.get("endName").toString());
            stringTrip.add(tempObj.get("endID").toString());

            stringTripArray.add(stringTrip);
            stringTrip.clear();
        }
        return stringTripArray;
    }
}
