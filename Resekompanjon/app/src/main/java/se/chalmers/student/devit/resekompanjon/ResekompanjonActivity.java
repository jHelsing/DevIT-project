package se.chalmers.student.devit.resekompanjon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.BackendCommunicator;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoConnectionException;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoJsonAvailableException;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoTripFoundException;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoTripsForDateException;
import se.chalmers.student.devit.resekompanjon.backend.utils.JsonInfoExtract;
import se.chalmers.student.devit.resekompanjon.backend.utils.OnTaskCompleted;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;
import se.chalmers.student.devit.resekompanjon.fragment.SearchBoxFragment;

public class ResekompanjonActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SearchBoxFragment.OnSearchButtonClick
        , OnTaskCompleted {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private final static String STATE_START_LOCATION = "startLocation";
    private final static String STATE_END_LOCATION = "endLocation";
    private final static String STATE_DATE = "date";
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

    private boolean start = false; //Needed to stop program from going back to start right away

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resekompanjon);

        if (savedInstanceState != null) {
            SearchBoxFragment searchBoxFragment = (SearchBoxFragment) this.getFragmentManager()
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
        switch (position) {
            case 0:
                //this is the current activity, do nothing
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
                break;
            case 5:
                //No about to go to
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
            setContentView(R.layout.loading_layout);
            TextView loadingView = (TextView) this.findViewById(R.id.loadingMessage);
            loadingView.setText(R.string.loading_search_result);
        } catch (NoConnectionException e) {
            Toast noConectionMessage = Toast.makeText(this
                    , "OBS! Internetanslutning krävs!", Toast.LENGTH_LONG);
            noConectionMessage.show();
            e.printStackTrace();
        }
    }

    /**
     * Needed to listen for changes in the backend
     */
    //TODO: A lot of work of JSON
    @Override
    public void onTaskCompleted() {
        try {
            JsonObject fromAPI = bComm.getApiData().getAsJsonObject();
            JsonInfoExtract tripResult = new JsonInfoExtract(fromAPI);
            SearchResultListActivity.setTrips(tripResult.getAllTripSummary());
            startActivity(new Intent(ResekompanjonActivity.this, SearchResultListActivity.class));
            finish();
        } catch (NoJsonAvailableException e) {
            Toast noConectionMessage = Toast.makeText(this, "Tyvärr så går det inte att söka med det innehållet!", Toast.LENGTH_LONG);
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
