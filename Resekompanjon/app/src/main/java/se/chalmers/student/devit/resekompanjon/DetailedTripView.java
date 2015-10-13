package se.chalmers.student.devit.resekompanjon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;

/**
 * @author  Jonathan
 * @version  0.2
 */
public class DetailedTripView extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private JsonObject trip;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    public DetailedTripView() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_trip_layout);
        Bundle bundle = getIntent().getExtras();
        // We know that this bundle will contain a key called JSON
        // that contains the JSON object of this trip.
        trip = new JsonParser().parse((String) bundle.get("JSON")).getAsJsonObject();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.detailed_trip_drawer_layout));

        initBasicTripInfo();
    }

    private void initBasicTripInfo() {
        //TODO initialise all the views with the right content
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
}
