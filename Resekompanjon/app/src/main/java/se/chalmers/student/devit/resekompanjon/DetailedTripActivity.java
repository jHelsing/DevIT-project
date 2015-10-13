package se.chalmers.student.devit.resekompanjon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.ElectricityBackend;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoConnectionException;
import se.chalmers.student.devit.resekompanjon.backend.utils.OnTaskCompleted;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;

/**
 * @author  Jonathan. Revisited by Amar.
 * @version  0.2
 */
public class DetailedTripActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnTaskCompleted{

    ElectricityBackend eb;

    private JsonObject trip;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    public DetailedTripActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detailed_trip_layout);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setmCurrentSelectedPosition(3);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.detailed_trip_drawer_layout));

    }

    @Override
    public void onStart(){
        super.onStart();

        if(isOnBus()){;
            try{
                eb = new ElectricityBackend(this, this);
                eb.getInformation();
            }
            catch(NoConnectionException e){
                Toast noConectionMessage = Toast.makeText(this
                        , "OBS! Internetanslutning krävs!", Toast.LENGTH_LONG);
                noConectionMessage.show();
            }
        } else{
            setContentView(R.layout.detailed_trip_warning_layout);
        }
    }

    private void initBasicTripInfo() {
        //TODO initialise all the views with the right content
    }

    /**
     * This method checks if the user's phone is connected to a SSID that belongs to a bus.
     * @return Returns true if the user is on a bus.
     */
    private boolean isOnBus(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnected()){
            WifiManager wifiMngr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiMngr.getConnectionInfo();
            return info.getSSID().equals("eduroam");
        } else{
            return false;
        }
    }

    /**
     * Called when an item in the navigation drawer is selected.
     *
     * @param position
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if(position == 0) {
            // return to start
            Intent i = new Intent(DetailedTripActivity.this, ResekompanjonActivity.class);
            //startActivity(i);
        } else if(position == 1) {
            // Go to planned trips
        } else if(position == 2) {
            // Go to favourites
        } else if(position == 3) {
            // Go to detailed trip
        } else if(position == 4) {
            // Go to settings
        } else if(position == 5) {
            // Go to about
        }
    }

    @Override
    public void onTaskCompleted() {
        JsonArray jsArray = eb.getApiData();
        boolean condition = true;
        JsonObject jsObj = null;
        int i = 0;
        while(condition){
            jsObj = jsArray.get(i).getAsJsonObject();
            if(jsObj.get("resourceSpec").getAsString().equals("Destination_Value")){
                condition = false;
            }
            else{
                i++;
            }
        }
        TextView busNmrTextview = (TextView) findViewById(R.id.busNumber);
        busNmrTextview.setText(jsObj.get("value").getAsString());
        condition = true;
        i = 0;
        while(condition){
            jsObj = jsArray.get(i).getAsJsonObject();
            if(jsObj.get("resourceSpec").getAsString().equals("Journey_Name_Value")){
                condition = false;
            }
            else{
                i++;
            }
        }
        TextView busDirectionTextview = (TextView) findViewById(R.id.busDirection);
        busDirectionTextview.setText(jsObj.get("value").getAsString());
    }
}
