package se.chalmers.student.devit.resekompanjon.se.chalmers.student.devit.resekompanjon.backend;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.channels.NoConnectionPendingException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jonat on 27/09/2015.
 */
public class ElectricityBackend extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        String key = "Z3JwMTE6UEtQSnhIWlc0ag==";
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
