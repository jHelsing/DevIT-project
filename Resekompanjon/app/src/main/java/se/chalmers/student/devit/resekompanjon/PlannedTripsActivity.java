package se.chalmers.student.devit.resekompanjon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import se.chalmers.student.devit.resekompanjon.backend.utils.readers.PrenumHandler;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;

/**
 * @author Linnea
 */
public class PlannedTripsActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private PrenumHandler fileHandler;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    public PlannedTripsActivity(){}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fileHandler = new PrenumHandler(getApplicationContext());
        setContentView(R.layout.planned_trip_layout);
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);


        // Setting navigation
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_drawer_layout));
    }

    /**
     * Called when an item in the navigation drawer is selected.
     *
     * @param position
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}
