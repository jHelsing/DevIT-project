package se.chalmers.student.devit.resekompanjon.backend;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Marcus on 2015-10-09.
 */
public class FavoriteHandler {

    private Context cont;
    private String result;

    public FavoriteHandler (Context context){
        cont = context;
    }

    //TODO: Figure out what to return
    public void getFavoriteTrips() {
        String filePath = cont.getFilesDir().getPath().toString() + "/favorites.txt";
        File file = new File(filePath);

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

        Log.d("result" , result);
    }
    public void addToFavoriteTrips(String s){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(cont.openFileOutput("favorites.txt", Context.MODE_APPEND));
            outputStreamWriter.write(s);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //Remove a single favorite
    public void removeFavorite(int i){

    }
}
