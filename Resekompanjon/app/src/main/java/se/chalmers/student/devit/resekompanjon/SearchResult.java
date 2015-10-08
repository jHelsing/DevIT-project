package se.chalmers.student.devit.resekompanjon;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.SearchResaultTrips;

/**
 * @author Amar.
 * @version 0.1
 */
public class SearchResult extends ListActivity{

    private ArrayList<SearchResaultTrips> searchResultTrips;

    public SearchResult (ArrayList<SearchResaultTrips> searchResultTrips){
        this.searchResultTrips = searchResultTrips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_result_layout);
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

}
