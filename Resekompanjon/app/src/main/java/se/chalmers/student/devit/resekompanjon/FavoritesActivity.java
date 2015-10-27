package se.chalmers.student.devit.resekompanjon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.BackendCommunicator;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoConnectionException;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoJsonAvailableException;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoTripFoundException;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoTripsForDateException;
import se.chalmers.student.devit.resekompanjon.backend.utils.JsonInfoExtract;
import se.chalmers.student.devit.resekompanjon.backend.utils.OnTaskCompleted;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResultTrips;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.FavoriteHandler;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;

/**
 * @author Jonathan
 * @version 0.1
 */
public class FavoritesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnTaskCompleted,
                                                            NavigationDrawerFragment.NavigationDrawerCallbacks{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FavoriteArrayAdapter adapter;
    private ListView listView;
    private ArrayList<JsonObject> list;

    public FavoritesActivity() {}

    private boolean start = false;

    private BackendCommunicator bComm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FavoriteHandler handler = new FavoriteHandler(this);
        JsonArray jsonArray = handler.getTripArrayAsJson();
        list = new ArrayList<>();
        for(int i=0; i<jsonArray.size(); i++) {
            JsonObject obj = jsonArray.get(i).getAsJsonObject();
            list.add(obj);
        }

        setContentView(R.layout.favorite_layout);
        listView = (ListView) findViewById(R.id.list);
        adapter = new FavoriteArrayAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        bComm = new BackendCommunicator(this, this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.favorite_trip_drawer_layout));
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DateFormat travelDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat travelTimeFormatter = new SimpleDateFormat("HH:mm");
        Date currentTime = Calendar.getInstance().getTime();
        String startLocation = list.get(position).get("originName").getAsString();
        String endLocation = list.get(position).get("endName").getAsString();
        try{
            bComm.getTripByName(startLocation, endLocation, travelDateFormatter.format(currentTime)
                    , travelTimeFormatter.format(currentTime));
            setContentView(R.layout.loading_layout);
            TextView loadingView = (TextView)this.findViewById(R.id.loadingMessage);
            loadingView.setText(R.string.loading_search_result);
        } catch (NoConnectionException e) {
            Toast noConectionMessage = Toast.makeText(this
                    , "OBS! Internetanslutning krävs!", Toast.LENGTH_LONG);
            noConectionMessage.show();
            e.printStackTrace();
        }

    }

    @Override
    public void onTaskCompleted() {
        try{
            JsonObject fromAPI= bComm.getApiData().getAsJsonObject();
            JsonInfoExtract tripResult = new JsonInfoExtract(fromAPI);
            ArrayList<SearchResultTrips> searchedTrips = tripResult.getTripAdvice();
            //SearchResultListActivity.setTrips(searchedTrips);
            SearchResultListActivity.setTrips(tripResult.getAllTripSummary());
            startActivity(new Intent(FavoritesActivity.this, SearchResultListActivity.class));
            finish();
        } catch (NoJsonAvailableException e) {
            Toast noConectionMessage = Toast.makeText(this
                    , "Tyvärr så går det inte att söka med det innehållet!", Toast.LENGTH_LONG);
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

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if(position == 0) {
            if(start) {
                Intent myIntent = new Intent(this, ResekompanjonActivity.class);
                startActivity(myIntent);
            } else {
                start = true;
            }
        } else if(position == 1) {
            Intent myIntent = new Intent(this, PlannedTripsActivity.class);
            startActivity(myIntent);
            // Go to planned trips
        } else if(position == 2) {
            // Go to favourites
            // Already in favorites, no reason to do anything
        } else if(position == 3) {
            Intent myIntent = new Intent(this, CurrentTripActivity.class);
            startActivity(myIntent);
            // Go to detailed trip
        } else if(position == 4) {
            // Go to settings
        } else if(position == 5) {
            // Go to about
        }
    }
}
