package se.chalmers.student.devit.resekompanjon;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.ElectricityBackend;
import se.chalmers.student.devit.resekompanjon.backend.JsonInfoExtract;
import se.chalmers.student.devit.resekompanjon.backend.NoConnectionException;
import se.chalmers.student.devit.resekompanjon.backend.OnTaskCompleted;
import se.chalmers.student.devit.resekompanjon.backend.SearchResaultTrips;
import se.chalmers.student.devit.resekompanjon.backend.VasttrafikBackend;

public class ResekompanjonActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SearchBoxFragment.OnSearchFragmentInteractionListener,
                    VehicleStopFragment.OnFragmentInteractionListener, OnTaskCompleted {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    //TODO: REMOVE! ONLY FOR DEBUG PURPOSES
    VasttrafikBackend vb; //REMOVE ?!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resekompanjon);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();


        //TODO: REMOVEE!!!!!!!
        vb = new VasttrafikBackend(this, this);
        vb.getTripID("Kungsportsplatsen", "Brunnsparken", "2015-10-09", "08.18");


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
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
        setContentView(R.layout.loading_layout);
        TextView loadingView = (TextView)this.findViewById(R.id.loadingMessage);
        loadingView.setText(R.string.loading_search_result);

        vb.getTripID(startLocation, endLocation, date, time);
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
        JsonObject fromAPI= vb.getApiData();
        JsonInfoExtract tripResult = new JsonInfoExtract(fromAPI);
        ArrayList<SearchResaultTrips> searchedTrips = tripResult.getTripAdvice();
        SearchResult searchResult = new SearchResult(searchedTrips);
        searchResult.startActivity(getIntent());

        TextView tv = (TextView)findViewById(R.id.debugText);

        //JsonObject data = vb.getApiData().get("LocationList").getAsJsonObject();
        //tv.setText(vb.getApiData().toString());

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_resekompanjon, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((ResekompanjonActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
