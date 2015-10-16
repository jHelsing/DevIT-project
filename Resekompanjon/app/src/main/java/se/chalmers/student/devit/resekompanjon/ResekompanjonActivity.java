package se.chalmers.student.devit.resekompanjon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonObject;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.BackendCommunicator;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.ElectricityBackend;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoJsonAavailableException;
import se.chalmers.student.devit.resekompanjon.backend.utils.JsonInfoExtract;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoConnectionException;
import se.chalmers.student.devit.resekompanjon.backend.utils.OnTaskCompleted;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;
import se.chalmers.student.devit.resekompanjon.fragment.SearchBoxFragment;
import se.chalmers.student.devit.resekompanjon.fragment.VehicleStopFragment;

public class ResekompanjonActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SearchBoxFragment.OnSearchFragmentInteractionListener,
                    VehicleStopFragment.OnFragmentInteractionListener, OnTaskCompleted {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private ElectricityBackend eb;

    private final static String STATE_START_LOCATION = "startLocation";
    private final static String STATE_END_LOCATION = "endLocation";
    private final static String STATE_DATE= "date";
    private final static String STATE_TIME = "time";

    private String mStartLocation;
    private String mEndLocation;
    private String mDate;
    private String mTime;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    BackendCommunicator bComm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resekompanjon);

        if(savedInstanceState != null){
             SearchBoxFragment searchBoxFragment = (SearchBoxFragment)this.getFragmentManager()
                     .findFragmentById(R.id.search_box_fragment);
            this.mStartLocation = savedInstanceState.getString(STATE_START_LOCATION);
            this.mEndLocation = savedInstanceState.getString(STATE_END_LOCATION);
            this.mDate = savedInstanceState.getString(STATE_DATE);
            this.mTime = savedInstanceState.getString(STATE_TIME);
            searchBoxFragment.initSavedValues(mStartLocation, mEndLocation, mDate, mTime);
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_drawer_layout));

        eb = new ElectricityBackend(getApplicationContext(), this);

        bComm = new BackendCommunicator(getApplicationContext(), this);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(STATE_START_LOCATION, mStartLocation);
        savedInstanceState.putString(STATE_END_LOCATION, mEndLocation);
        savedInstanceState.putString(STATE_DATE, mDate);
        savedInstanceState.putString(STATE_TIME, mTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if(position == 0) {
            // return to start
        } else if(position == 1) {
            // Go to planned trips
            Intent i = new Intent(ResekompanjonActivity.this, PlannedTripsActivity.class);
            startActivity(i);
        } else if(position == 2) {
            // Go to favourites
            Intent i = new Intent(ResekompanjonActivity.this, FavoritesActivity.class);
            startActivity(i);
        } else if(position == 3) {
            // Go to detailed trip
            Intent i = new Intent(ResekompanjonActivity.this, CurrentTripActivity.class);
            startActivity(i);
        } else if(position == 4) {
            // Go to settings
        } else if(position == 5) {
            // Go to about
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchButtonClick(String startLocation, String endLocation, String date, String time) {

        try {
            bComm.getTripByName(startLocation, endLocation, date, time);
            this.mStartLocation = startLocation;
            this.mEndLocation = endLocation;
            this.mDate = date;
            this.mTime = time;
            Log.d("Rätt", date);
            Log.d("Rätt här", time);
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
    public void OnVehicleStopFragmentInteraction(Uri uri) {
    }

    /**
     * Needed to listen for changes in the backend
     */
    //TODO: A lot of work of JSON
    @Override
    public void onTaskCompleted() {
        try{
            JsonObject fromAPI= bComm.getApiData().getAsJsonObject();
            JsonInfoExtract tripResult = new JsonInfoExtract(fromAPI);
            //Log.d("res", tripResult.getAllTripSummary().toString());
            ArrayList<SearchResaultTrips> searchedTrips = tripResult.getTripAdvice();
            SearchResultListActivity.setTrips(searchedTrips);
            startActivity(new Intent(ResekompanjonActivity.this, SearchResultListActivity.class));
            finish();
        } catch (NoJsonAavailableException e) {
            Toast noConectionMessage = Toast.makeText(this
                    , "Tyvärr så går det inte att söka med det innehållet!", Toast.LENGTH_LONG);
            noConectionMessage.show();
            e.printStackTrace();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((ResekompanjonActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
