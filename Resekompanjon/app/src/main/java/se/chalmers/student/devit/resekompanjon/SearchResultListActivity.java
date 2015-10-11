package se.chalmers.student.devit.resekompanjon;

import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.SearchResaultTrips;

/**
 * @author Amar. Revisited by Jonathan
 * @version 0.2
 */
public class SearchResultListActivity extends ListActivity implements SearchInfoFragment.OnFragmentInteractionListener{

    private static ArrayList<SearchResaultTrips> searchResultTrips;

    public SearchResultListActivity() {}

    public SearchResultListActivity(ArrayList<SearchResaultTrips> searchResultTrips){
        this.searchResultTrips = searchResultTrips;
    }

    public static void setTrips(ArrayList<SearchResaultTrips> trips) {
        searchResultTrips = trips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_list_layout);

        SearchResultTripArrayAdapter adapter = new SearchResultTripArrayAdapter(this, searchResultTrips);
        setListAdapter(adapter);

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
    public void onListItemClick(ListView l, View v, int index, long id){
        //TODO implement some logic
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}