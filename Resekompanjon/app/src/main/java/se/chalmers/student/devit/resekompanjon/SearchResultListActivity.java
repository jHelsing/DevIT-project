package se.chalmers.student.devit.resekompanjon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.FavoriteHandler;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.PrenumHandler;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;
import se.chalmers.student.devit.resekompanjon.fragment.SearchInfoFragment;

/**
 * @author Amar. Revisited by Jonathan
 * @version 0.5
 */
public class SearchResultListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        SearchInfoFragment.OnFragmentInteractionListener, KeyEvent.Callback, View.OnClickListener{

    private static ArrayList<SearchResaultTrips> searchResultTrips;

    private ListView listView;

    private SearchResultTripArrayAdapter adapter;

    private ImageButton favouriteButton;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    public SearchResultListActivity() {}

    public static void setTrips(ArrayList<SearchResaultTrips> trips) {
        searchResultTrips = trips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_list_layout);

        listView = (ListView) findViewById(R.id.list);
        adapter = new SearchResultTripArrayAdapter(this, searchResultTrips);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setItemsCanFocus(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.search_result_drawer_layout));

        initSearchInfo();
    }

    private void initSearchInfo(){
        String startLocation = searchResultTrips.get(0).getOriginName();
        String endLocation = searchResultTrips.get(0).getDestinationName();
        String time = searchResultTrips.get(0).getOriginDate() + ", " + searchResultTrips.get(0)
                .getOriginTime();
        View fragment = findViewById(R.id.fragment);

        ((TextView) fragment.findViewById(R.id.tripStartLocation)).setText(startLocation);
        ((TextView) fragment.findViewById(R.id.tripEndLocation)).setText(endLocation);
        ((TextView) fragment.findViewById(R.id.selectedTime)).setText(time);

        favouriteButton = (ImageButton) findViewById(R.id.favouriteButton);
        favouriteButton.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(SearchResultListActivity.this, ResekompanjonActivity.class));
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
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
        switch(view.getId()) {
            case R.id.checkboxButton:
                ImageButton button = (ImageButton) view;
                SearchResaultTrips trip = searchResultTrips.get(position);
                PrenumHandler handler = new PrenumHandler(this);
                JsonArray arr = handler.getTripArrayAsJson();
                JsonObject tripAsJson = new JsonObject();
                tripAsJson.addProperty("originName", trip.getOriginName());
                tripAsJson.addProperty("originID", trip.getOriginId());
                tripAsJson.addProperty("endName", trip.getDestinationName());
                tripAsJson.addProperty("endID", trip.getDestinationId());
                tripAsJson.addProperty("date", trip.getOriginDate());
                tripAsJson.addProperty("time", trip.getOriginTime());
                tripAsJson.addProperty("ref", trip.getRef());
                int i=0;
                JsonObject tempObj = arr.get(i).getAsJsonObject();
                while(i<arr.size() && tripAsJson.equals(tempObj)) {
                    i++;
                    tempObj = arr.get(i).getAsJsonObject();
                }
                if(tripAsJson.equals(tempObj)) {
                    //Trip is already a planned trip
                    handler.removePrenum(i);
                    button.setBackgroundResource(R.drawable.checkbox_untoggled);
                } else {
                    //Trip is not a planned trip, add it as one
                    handler.addToPrenumTrips(trip.getOriginName(), trip.getOriginId(),
                            trip.getDestinationName(), trip.getDestinationId(), trip.getRef(),
                            trip.getOriginDate(), trip.getOriginTime());
                    button.setBackgroundResource(R.drawable.checkbox_toggled);
                }
                Log.d("HEJ", "KOM HIT");
                break;
        }
        Log.d("HEJ", "KOM HIT");
    }

    /**
     * When interaction with the Search Info Fragment happens, this method will run.
     */
    @Override
    public void onSearchInfoInteraction() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == favouriteButton.getId()){
            FavoriteHandler favHandler = new FavoriteHandler(this);
            String orginName = searchResultTrips.get(0).getOriginName();
            String endName = searchResultTrips.get(0).getDestinationName();
            String orginID = searchResultTrips.get(0).getOriginId();
            String endID = searchResultTrips.get(0).getDestinationId();
            favHandler.addToFavoriteTrips(orginName,orginID,endName,endID);
        }
    }
}
