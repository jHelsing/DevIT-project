package se.chalmers.student.devit.resekompanjon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResultTripSummary;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.FavoriteHandler;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;
import se.chalmers.student.devit.resekompanjon.fragment.SearchInfoFragment;

/**
 * @author Amar. Revisited by Jonathan
 * @version 0.5
 */
public class SearchResultListActivity extends AppCompatActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        SearchInfoFragment.OnFragmentInteractionListener, KeyEvent.Callback, View.OnClickListener {

    private static ArrayList<SearchResultTripSummary> searchResultTrips;
    private ImageButton favouriteButton;
    private FavoriteHandler favHandler;
    private boolean isFavourite = false;
    private boolean start = false;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    public SearchResultListActivity() {
    }

    public static void setTrips(ArrayList<SearchResultTripSummary> trips) {
        searchResultTrips = trips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_list_layout);

        ListView listView = (ListView) findViewById(R.id.list);
        SearchResultTripArrayAdapter adapter = new SearchResultTripArrayAdapter(this, searchResultTrips, listView);
        listView.setAdapter(adapter);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.search_result_drawer_layout));

        initSearchInfo();
    }

    private void initSearchInfo() {
        String startLocation = searchResultTrips.get(0).getOriginName();
        String endLocation = searchResultTrips.get(searchResultTrips.size() - 1).getDestinationName();
        String time = searchResultTrips.get(0).getDepartureDate() + ", " + searchResultTrips.get(0)
                .getDepartureTime();
        View fragment = findViewById(R.id.fragment);

        ((TextView) fragment.findViewById(R.id.tripStartLocation)).setText(startLocation);
        ((TextView) fragment.findViewById(R.id.tripEndLocation)).setText(endLocation);
        ((TextView) fragment.findViewById(R.id.selectedTime)).setText(time);

        favouriteButton = (ImageButton) findViewById(R.id.favouriteButton);
        favouriteButton.setOnClickListener(this);

        favHandler = new FavoriteHandler(this);

        String originName = searchResultTrips.get(0).getOriginName();
        String endName = searchResultTrips.get(0).getDestinationName();
        JsonObject favObj = new JsonObject();
        favObj.addProperty("originName", originName);
        favObj.addProperty("endName", endName);

        if (favHandler.isFavorite(favObj)) {
            isFavourite = true;
            favouriteButton.setImageResource(R.drawable.favourite_toggled);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(SearchResultListActivity.this, ResekompanjonActivity.class));
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                if (start) {
                    Intent myIntent = new Intent(this, ResekompanjonActivity.class);
                    startActivity(myIntent);
                    finish();
                } else {
                    start = true;
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
                Intent currentTripIntent = new Intent(this, CurrentTripActivity.class);
                startActivity(currentTripIntent);
                finish();
                break;
            case 4:
                //No settings to go to
                Toast settingsToast = Toast.makeText(this,"Funktionen \"Inställningar\" är ej" +
                        " implementerad än.", Toast.LENGTH_LONG);
                settingsToast.show();
                break;
            case 5:
                //No about to go to
                Toast aboutApplicationToast = Toast.makeText(this,"Funktionen \"Om Applikationen\"" +
                        " är ej implementerad än.", Toast.LENGTH_LONG);
                aboutApplicationToast.show();
                break;
        }
        }

    /**
     * When interaction with the Search Info Fragment happens, this method will run.
     */
    @Override
    public void onSearchInfoInteraction() {

    }

    @Override
    public void onClick(View v) {
        favHandler = new FavoriteHandler(this);
        String originName = searchResultTrips.get(0).getOriginName();
        String endName = searchResultTrips.get(searchResultTrips.size() - 1).getDestinationName();
        String originID = searchResultTrips.get(0).getOriginID();
        String endID = searchResultTrips.get(searchResultTrips.size() - 1).getDestinationId();
        JsonObject favObj = new JsonObject();
        favObj.addProperty("originName", originName);
        favObj.addProperty("endName", endName);

        if (!isFavourite) {
            favHandler.addToFavoriteTrips(originName, originID, endName, endID);
            favouriteButton.setImageResource(R.drawable.favourite_toggled);
            isFavourite = true;
        } else {
            int i = favHandler.getFavoriteIndex(favObj);
            favHandler.removeFavorite(i);
            favouriteButton.setImageResource(R.drawable.favourite_untoggled);
            isFavourite = false;
        }
    }
}
