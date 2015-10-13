package se.chalmers.student.devit.resekompanjon;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;
import se.chalmers.student.devit.resekompanjon.fragment.SearchInfoFragment;

/**
 * @author Amar. Revisited by Jonathan
 * @version 0.2
 */
public class SearchResultListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        SearchInfoFragment.OnFragmentInteractionListener, KeyEvent.Callback{

    private static ArrayList<SearchResaultTrips> searchResultTrips;

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

        ListView list = (ListView) findViewById(R.id.list);
        SearchResultTripArrayAdapter adapter = new SearchResultTripArrayAdapter(this, searchResultTrips);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        //TODO get NavigationFragment from parent Activity
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
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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

    }
}
