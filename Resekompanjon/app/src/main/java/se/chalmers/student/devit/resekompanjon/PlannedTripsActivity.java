package se.chalmers.student.devit.resekompanjon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.utils.readers.PrenumHandler;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;

/**
 * @author Linnea
 * @version 1.0
 */
public class PlannedTripsActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ExpandableListView expListView;
    private ExpandableListAdapter adapter;
    private ArrayList<JsonObject> arrayList;
    private boolean start = false; //Needed to stop program from going back to start right away

    public PlannedTripsActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        PrenumHandler fileHandler = new PrenumHandler(getApplicationContext());
        if (fileHandler.getNumbOfPrenums() > 0) {
            JsonArray tempArr = fileHandler.getTripArrayAsJson();
            for (int i = 0; i < tempArr.size(); i++) {
                JsonObject obj = (JsonObject) tempArr.get(i);
                arrayList.add(obj);
            }
            setContentView(R.layout.planned_trip_layout);
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.planned_trip_drawer_layout));

            expListView = (ExpandableListView) findViewById(R.id.expandableListView);
            adapter = new PlannedTripExpandableListAdapter(this, arrayList);
            expListView.setAdapter(adapter);


        } else {
            //There are no planned trips available
            Toast noConectionMessage = Toast.makeText(this
                    , "Tyvärr, men du måste lägga till en"
                    + " resa som planerad resa för att kunna gå vidare.", Toast.LENGTH_LONG);
            noConectionMessage.show();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3500);
                        Intent i = new Intent(PlannedTripsActivity.this, ResekompanjonActivity.class);
                        startActivity(i);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
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
                //this is the current activity, do nothing
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
}
