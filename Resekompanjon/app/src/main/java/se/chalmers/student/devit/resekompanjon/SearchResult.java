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


    }

}
