package se.chalmers.student.devit.resekompanjon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.utils.readers.PrenumHandler;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;

/**
 * @author Linnea
 */
public class PlannedTripsActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private PrenumHandler fileHandler;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ExpandableListView expListView;
    private ExpandableListAdapter adapter;
    private ArrayList<JsonObject> arrayList;

    public PlannedTripsActivity(){}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fileHandler = new PrenumHandler(getApplicationContext());
        if(fileHandler.getNumbOfPrenums() > 0) {
            JsonArray tempArr = fileHandler.getTripArrayAsJson();
            for(int i=0; i<tempArr.size(); i++) {
                JsonObject obj = (JsonObject) tempArr.get(i);
                arrayList.add(obj);
            }
            setContentView(R.layout.planned_trip_layout);
            expListView = (ExpandableListView) findViewById(R.id.expandableListView);
            adapter = new PlannedTripExpandableListAdapter(this, arrayList);
            expListView.setAdapter(adapter);
        } else {
            //There are no planned trips available
        }



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
