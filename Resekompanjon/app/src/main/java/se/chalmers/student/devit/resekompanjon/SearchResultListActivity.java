package se.chalmers.student.devit.resekompanjon;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.SearchResaultTrips;

/**
 * @author Amar.
 * @version 0.1
 */
public class SearchResultListActivity extends ListActivity{

    private final ArrayList<SearchResaultTrips> searchResultTrips;

    public SearchResultListActivity(ArrayList<SearchResaultTrips> searchResultTrips){
        this.searchResultTrips = searchResultTrips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SearchResultTripArrayAdapter adapter = new SearchResultTripArrayAdapter(this, searchResultTrips);
        setListAdapter(adapter);

        initSearchInfo();
    }

    private void initSearchInfo(){
        String startLocation = searchResultTrips.get(0).getOriginName();
        String endLocation = searchResultTrips.get(0).getDestinationName();
        String time = searchResultTrips.get(0).getOriginDate() + ", " + searchResultTrips.get(0)
                .getOriginTime();
        SearchInfoFragment searchInfo = SearchInfoFragment.newInstance(startLocation, endLocation, time);
        searchInfo.startActivity(getIntent());
    }

    @Override
    public void onListItemClick(ListView l, View v, int index, long id){
        //TODO implement some logic
    }

}
