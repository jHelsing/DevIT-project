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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.BackendCommunicator;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoConnectionException;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoJsonAvailableException;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoTripFoundException;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoTripsForDateException;
import se.chalmers.student.devit.resekompanjon.backend.utils.OnTaskCompleted;
import se.chalmers.student.devit.resekompanjon.fragment.BetweenBusStopCurrentFragment;
import se.chalmers.student.devit.resekompanjon.fragment.BusStopCurrentFragment;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;

/**
 * @author  Jonathan. Revisited by Amar.
 * @version  0.6
 */
public class CurrentTripActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnTaskCompleted
        ,BusStopCurrentFragment.OnFragmentInteractionListener, BetweenBusStopCurrentFragment.OnFragmentInteractionListener{

    private JsonObject trip;
    private InfoState infoState;
    private BackendCommunicator bComm;
    private String oldNextStop = "";
    private String nextStop;
    private String firstNextStop;
    private int start = 2; //Needed to stop program from going back to start right away
    private String busDirection;

    private final String[] stopToLindholmen = {"G�taplatsen", "Kungsportsplatsn", "NisseTerminalen"
            , "Frihamnen", "Lindholmen"};

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private NavigationDrawerFragment m2NavigationDrawerFragment;

    public CurrentTripActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.current_trip_layout);

        bComm = new BackendCommunicator(this, this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
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
                bComm = new BackendCommunicator(this, this);
                bComm.getElectricityJourneyInfo();
            }
            catch(NoConnectionException e){
                Toast noConectionMessage = Toast.makeText(this
                        , "OBS! Internetanslutning krävs!", Toast.LENGTH_LONG);
                noConectionMessage.show();
            }
        } else{
            setContentView(R.layout.current_trip_warning_layout);
            m2NavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            // Set up the drawer.
            m2NavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.current_trip_drawer_layout));

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
        switch (position){
            case 0:
                if(!(start > 0)) {
                    Intent myIntent = new Intent(this, ResekompanjonActivity.class);
                    startActivity(myIntent);
                    finish();
                } else {
                    start--;
                }
                break;
            case 1:
                Intent plannedTripIntent = new Intent(this, PlannedTripsActivity.class);
                startActivity(plannedTripIntent);
                finish();
                break;
            case 2:
                Intent favIntent = new Intent(this, FavoritesActivity.class);
                startActivity(favIntent);
                finish();
                break;
            case 3:
                //this is the current activity, do nothing
                break;
            case 4:
                //No settings to go to
                break;
            case 5:
                //No about to go to
                break;
        }
    }

    /**
     * First brings the Journeyinfo to show the busNumber and the destination. Later on, onTaskCompleted
     * is called again to make the trip visible to the user. The trip is from next BusStop to Lindholmen. When
     * this is done, the OnTaskCompleted method is called again and again to update the next stop of the bus so
     * the users can se the location. This is done until CurrentTripActivity is finished.
     */
    @Override
    public void onTaskCompleted() {
        if(!this.isFinishing()) {
            try {
                JsonArray jsArray = bComm.getApiData().getAsJsonArray();
                JsonObject jsObj = null;
                boolean condition = true;
                int i = jsArray.size() - 1;
                switch (infoState) {
                    case JOURNEY:
                        TextView busNbrTextview = (TextView) findViewById(R.id.busNumber);
                        busNbrTextview.setText("55");
                        while (condition) {
                            jsObj = jsArray.get(i).getAsJsonObject();
                            if (jsObj.get("resourceSpec").getAsString().equals("Destination_Value")) {
                                condition = false;
                            } else {
                                i--;
                            }
                        }
                        TextView busDirectionTextview = (TextView) findViewById(R.id.busDirection);
                        busDirection = jsObj.get("value").getAsString();
                        busDirectionTextview.setText("→" + busDirection);
                        infoState = InfoState.UN_UPDATED_NEXT_STOP;
                        try {
                            bComm.getElectricityNextStopInfo();
                        } catch (NoConnectionException e) {
                            Toast noConectionMessage = Toast.makeText(this
                                    , "OBS! Internetanslutning krävs!", Toast.LENGTH_LONG);
                            noConectionMessage.show();
                            e.printStackTrace();
                        }
                        break;
                    case UN_UPDATED_NEXT_STOP:
                        jsObj = jsArray.get(i).getAsJsonObject();
                        nextStop = jsObj.get("value").getAsString();
                        if(nextStop.equals("Lindholmen") && oldNextStop.equals("")){
                            nextStop = "Frihamnen";
                        }
                        firstNextStop = nextStop;
                        if (nextStop.equals("G�taplatsen") && oldNextStop.equals("G�taplatsen")) {
                            LinearLayout currentStopsLinearLayout = (LinearLayout) findViewById(R.id.currentStops);
                            currentStopsLinearLayout.removeAllViews();
                            BusStopCurrentFragment.setIsPressed(false);
                        }
                        int j = 0;
                        switch (busDirection) {
                            case "Lindholmen":
                                while (condition) {
                                    if (stopToLindholmen[j].equals(nextStop)) {
                                        for (int k = j; k < stopToLindholmen.length; k++) {
                                            String busStop = stopToLindholmen[k];
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            if (k==j) {
                                                BusStopCurrentFragment busStopFragement = BusStopCurrentFragment.newInstance(busStop);
                                                fragmentTransaction.add(R.id.currentStops, busStopFragement, busStop);
                                            } else{
                                                BetweenBusStopCurrentFragment betweenBusStopFragment = BetweenBusStopCurrentFragment.newInstance("", "");
                                                fragmentTransaction.add(R.id.currentStops, betweenBusStopFragment, busStop + "1");
                                                BusStopCurrentFragment busStopFragement = BusStopCurrentFragment.newInstance(busStop);
                                                fragmentTransaction.add(R.id.currentStops, busStopFragement, busStop);
                                            }
                                            fragmentTransaction.commit();
                                        }
                                        condition = false;
                                    } else if (j < stopToLindholmen.length-1) {
                                        j++;
                                    } else {
                                        condition = false;
                                    }
                                }
                                break;
                        }
                        oldNextStop = nextStop;
                        infoState = InfoState.UPDATED_NEXT_STOP;
                        try {
                            bComm.getElectricityNextStopInfo();
                        } catch (NoConnectionException e) {
                            Toast noConectionMessage = Toast.makeText(this
                                    , "OBS! Internetanslutning krävs!", Toast.LENGTH_LONG);
                            noConectionMessage.show();
                            e.printStackTrace();
                        }
                        break;
                    case UPDATED_NEXT_STOP:
                        jsObj = jsArray.get(i).getAsJsonObject();
                        nextStop = jsObj.get("value").getAsString();
                        if (!nextStop.equals(oldNextStop)) {
                            FragmentManager fragmentManager = getFragmentManager();
                            if(oldNextStop.equals(firstNextStop)){
                                View nextStopFragment = fragmentManager.findFragmentByTag(firstNextStop).getView();
                                ImageView busStopIcon = (ImageView) nextStopFragment.findViewById(R.id.busStopIcon);
                                busStopIcon.setImageResource(R.drawable.visited_stop);
                            } else{
                                View previousStopLineFragment = fragmentManager.findFragmentByTag(oldNextStop + "1").getView();
                                ImageView tripIcon = (ImageView) previousStopLineFragment.findViewById(R.id.tripIcon);
                                tripIcon.setImageResource(R.drawable.completed_trip);
                                View previousStopFragment = fragmentManager.findFragmentByTag(oldNextStop).getView();
                                ImageView busStopIcon = (ImageView) previousStopFragment.findViewById(R.id.busStopIcon);
                                busStopIcon.setImageResource(R.drawable.visited_stop);
                            }
                            if (nextStop.equals("G�taplatsen") && oldNextStop.equals("Lindholmen")) {
                                infoState = InfoState.UN_UPDATED_NEXT_STOP;
                            }
                            oldNextStop = nextStop;
                        }
                        try {
                            bComm.getElectricityNextStopInfo();
                        } catch (NoConnectionException e) {
                            Toast noConectionMessage = Toast.makeText(this
                                    , "OBS! Internetanslutning krävs!", Toast.LENGTH_LONG);
                            noConectionMessage.show();
                            e.printStackTrace();
                        }
                        break;
                }
            } catch (NoJsonAvailableException e) {
                Toast noConectionMessage = Toast.makeText(this
                        , "Tyvärr, kan inte hämta information från servern!", Toast.LENGTH_LONG);
                noConectionMessage.show();
                e.printStackTrace();
            } catch (NoTripFoundException e) {
                Toast noConectionMessage = Toast.makeText(this
                        , "Tyvärr så gick det inte att hitta någon resa för sträckan", Toast.LENGTH_LONG);
                noConectionMessage.show();
                e.printStackTrace();
            } catch (NoTripsForDateException e) {
                Toast noConectionMessage = Toast.makeText(this
                        , "Tyvärr så finns inga resor tillgängliga för det datumet", Toast.LENGTH_LONG);
                noConectionMessage.show();
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called by busStopCurrentFragment when a stop button is pressed in there.
     * @param stop is the paramter where the bus has to stay.
     */

    /**
     * Finishes this activity and starts ResekompanjonActivity.
     */
    @Override
    protected void onPause(){
        super.onPause();
        Intent i = new Intent(CurrentTripActivity.this, ResekompanjonActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * Is called by busStopCurrentFragment when stop button is pressed in there.
     * @param uri is the parameter where the bus has to stop.
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
            Toast stopPressedMessage = Toast.makeText(this
                    , "Du har valt att stanna på: " + uri.toString(), Toast.LENGTH_LONG);
            stopPressedMessage.show();
    }

    /**
     * Checks if the stop to stop at is passed.
     * @param stopToStopAt is the stop to check if it has passed.
     * @return Returns true if the stop to stop at has been passed.
     */
    public boolean isPassed(String stopToStopAt){
        String [] tempStopToLindholmen = this.stopToLindholmen.clone();

        for(int i = 0; i<tempStopToLindholmen.length; i++){
            switch (tempStopToLindholmen[i]){
                case "G�taplatsen":
                    tempStopToLindholmen[i] = "Götaplatsen";
                    break;
                case "Kungsportsplatsn":
                    tempStopToLindholmen[i] = "Kungsportsplatsen";
                    break;
            }
        }

        int indexNextStop = -1;
        for(int i = 0; i<tempStopToLindholmen.length; i++){
            if(tempStopToLindholmen[i].equals(nextStop)){
                indexNextStop = i;
                break;
            }
        }
        int indexStopToStopAt = -1;
        for(int i = 0; i<tempStopToLindholmen.length; i++){
            if(tempStopToLindholmen[i].equals(stopToStopAt)){
                indexStopToStopAt = i;
                break;
            }
        }

        if(indexStopToStopAt < indexNextStop){
            return true;
        }
        return false;
    }

    @Override
    public void onBetweenFragmentInteraction(Uri uri) {}

    public enum InfoState{
        JOURNEY,
        UN_UPDATED_NEXT_STOP,
        UPDATED_NEXT_STOP;
    }
}
