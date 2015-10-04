package se.chalmers.student.devit.resekompanjon.backend;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author Jonathan
 * @version 0.1
 */
public class ElectricityBackend extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            electricityConnect();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            //if no connection to internet exists.
            return false;
        }
    }


    public void electricityConnect() throws NoConnectionException {
        if(!isConnectedToInternet())
            throw new NoConnectionException();
        long t2 = System.currentTimeMillis();
        long t1 = t2 - (1000 * 120);

        StringBuffer response = new StringBuffer();
        //Can't have key in program as it ends up publically on github
        //TODO: Figure out a way to read api-key? or we have to enter it manually before running
        String key = "";
        String url = "https://ece01.ericsson.net:4443/ecity?dgw=Ericsson$Vin_Num_001&sensorSpec=Ericsson$Next_Stop&t1="
                + t1 + "&t2=" + t2;

        URL requestURL = null;
        try {
            requestURL = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) requestURL.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", key);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Log.d("SERVER:","The response is: " + response.toString());
            Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show();


            System.out.println(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
