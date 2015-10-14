package se.chalmers.student.devit.resekompanjon.backend.utils.readers;

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
 * Created by Marcus on 2015-10-14.
 */
public class PrenumHandler {
    private Context cont;
    private String result;
    private JsonArray tripArray;
    private String filePath;
    private static final String FILE_NAME = "prenums.txt";

    public PrenumHandler (Context context){
        cont = context;
        filePath = cont.getFilesDir().getPath().toString() + "/" + FILE_NAME;
        File file = new File(filePath);
        if (!file.exists()){
            try {
                file.createNewFile();
                Log.d("File not found", "Creating file for saving favorites");
                clearPrenums();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        readPrenumTrips();
    }

    private void readPrenumTrips() {
        try {
            InputStream inputStream = cont.openFileInput(FILE_NAME);

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
            clearPrenums();
        }

    }
    public void addToPrenumTrips(String originName, String originID, String endName, String endID, String ref, String date, String time){
        readPrenumTrips();

        JsonObject newTripObj = new JsonObject();
        newTripObj.addProperty("originName", originName);
        newTripObj.addProperty("originID", originID);
        newTripObj.addProperty("endName", endName);
        newTripObj.addProperty("endID", endID);
        newTripObj.addProperty("date", date);
        newTripObj.addProperty("time", time);
        newTripObj.addProperty("ref", ref); //is the url last in the json, inside the JourneyDetailRef
        tripArray.add(newTripObj);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(cont.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(tripArray.toString());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Used to remove a single Favorite
     * @param i int that determines what prenum to remove, index in array to remove
     */
    public void removePrenum(int i){
        readPrenumTrips();
        tripArray.remove(i); //Maybe i-1 depending on how what i is sent
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(cont.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(tripArray.toString());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    //Mostly added for testing but might be useful
    public void clearPrenums(){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(cont.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.close();
            outputStreamWriter = new OutputStreamWriter(cont.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            JsonArray eArray = new JsonArray();
            outputStreamWriter.write(eArray.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getNumbOfPrenums(){ return tripArray.size(); }

    public JsonArray getTripArrayAsJson(){
        readPrenumTrips();
        return tripArray;
    }

    public ArrayList<ArrayList> getTripArrayAsStrings(){
        readPrenumTrips();
        ArrayList<String> stringTrip = new ArrayList<>();
        ArrayList<ArrayList> stringTripArray = new ArrayList<>();
        for (int i = 0; i < tripArray.size(); i++){

            JsonObject tempObj = tripArray.get(i).getAsJsonObject();
            stringTrip.add(tempObj.get("originName").toString());
            stringTrip.add(tempObj.get("originID").toString());
            stringTrip.add(tempObj.get("endName").toString());
            stringTrip.add(tempObj.get("endID").toString());
            stringTrip.add(tempObj.get("date").toString());
            stringTrip.add(tempObj.get("time").toString());
            stringTrip.add(tempObj.get("ref").toString());

            stringTripArray.add(stringTrip);
            stringTrip.clear();
        }
        return stringTripArray;
    }
}
