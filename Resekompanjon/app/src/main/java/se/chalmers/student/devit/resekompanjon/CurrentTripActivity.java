package se.chalmers.student.devit.resekompanjon;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.ElectricityBackend;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoConnectionException;
import se.chalmers.student.devit.resekompanjon.backend.utils.OnTaskCompleted;
import se.chalmers.student.devit.resekompanjon.fragment.BetweenBusStopCurrentFragment;
import se.chalmers.student.devit.resekompanjon.fragment.BusStopCurrentFragment;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;

/**
 * @author  Jonathan. Revisited by Amar.
 * @version  0.2
 */
public class CurrentTripActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnTaskCompleted
        ,BusStopCurrentFragment.OnFragmentInteractionListener, BetweenBusStopCurrentFragment.OnFragmentInteractionListener{

    ElectricityBackend eb;

    private JsonObject trip;

    private InfoState infoState;

    private String busDirection;

    private final String[] stopToLindholmen = {"G�taplatsen", "Kungsportsplatsn", "NisseTerminalen"
            , "Frihamnen", "Lindholmen"};

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    public CurrentTripActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.current_trip_layout);

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

        if(isOnBus()){
            infoState = InfoState.JOURNEY;
            try{
                eb = new ElectricityBackend(this, this);
                eb.getJourneyInfo();

            }
            catch(NoConnectionException e){
                Toast noConectionMessage = Toast.makeText(this
                        , "OBS! Internetanslutning krävs!", Toast.LENGTH_LONG);
                noConectionMessage.show();
            }
        } else{
            setContentView(R.layout.current_trip_warning_layout);
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
            return info.getSSID().equals("Amars iPhone");
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
            Intent i = new Intent(CurrentTripActivity.this, ResekompanjonActivity.class);
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
        JsonObject jsObj = null;
        boolean condition = true;
        int i = jsArray.size()-1;
        switch(infoState){
            case JOURNEY:
                TextView busNbrTextview = (TextView) findViewById(R.id.busNumber);
                busNbrTextview.setText("55");
                while(condition){
                    jsObj = jsArray.get(i).getAsJsonObject();
                    if(jsObj.get("resourceSpec").getAsString().equals("Destination_Value")){
                        condition = false;
                    } else {
                        i--;
                    }
                }
                TextView busDirectionTextview = (TextView) findViewById(R.id.busDirection);
                busDirection = jsObj.get("value").getAsString();
                busDirectionTextview.setText("→" + busDirection);
                infoState = InfoState.NEXT_STOP;
                try{
                eb.getNextStopInfo();
            } catch(NoConnectionException e){
                Toast noConectionMessage = Toast.makeText(this
                        , "OBS! Internetanslutning krävs!", Toast.LENGTH_LONG);
                noConectionMessage.show();
            }
                break;
            case NEXT_STOP:
                jsObj = jsArray.get(i).getAsJsonObject();
                String nextStop = jsObj.get("value").getAsString();
                int j = 0;
                    switch (busDirection) {
                        case "Lindholmen":
                            while (condition){
                                if(stopToLindholmen[j].equals(nextStop)){
                                    for(int k = j; k<stopToLindholmen.length; k++){
                                        String busStop = stopToLindholmen[k];
                                        BusStopCurrentFragment busStopFragement = BusStopCurrentFragment.newInstance(busStop, "");
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.add(R.id.currentStops, busStopFragement, "busStopFragment");
                                        if(!busStop.equals("Lindholmen")){
                                            BetweenBusStopCurrentFragment betweenBusStopFragment = BetweenBusStopCurrentFragment.newInstance("", "");
                                            fragmentTransaction.add(R.id.currentStops, betweenBusStopFragment, "betweenBusStopFragment");
                                        }
                                        fragmentTransaction.commit();
                                    }
                                    condition = false;
                                } else if(j<stopToLindholmen.length){
                                    j++;
                                } else{
                                    condition = false;
                                }
                            }
                            break;
                }
                break;
        }
    }

    public void initStops(){

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public enum InfoState{
        JOURNEY,
        NEXT_STOP;
    }
}
