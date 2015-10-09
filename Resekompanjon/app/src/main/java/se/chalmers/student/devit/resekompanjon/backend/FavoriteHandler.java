package se.chalmers.student.devit.resekompanjon.backend;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        AssetManager  assets = cont.getResources().getAssets();
        InputStream is;
        byte[] b = new byte[0];
        try {
            is = assets.open("favorites.txt");
            b = new byte[is.available()];
            is.read(b);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = new String(b);

        Log.i("result: ", result);
        //return result;
    }
    public void addToFavoriteTrips(String s){
        try {
            FileOutputStream out = cont.openFileOutput("favorites.txt", cont.MODE_APPEND);
            out.write(s.getBytes());
            Log.d("Trying to write", s);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
