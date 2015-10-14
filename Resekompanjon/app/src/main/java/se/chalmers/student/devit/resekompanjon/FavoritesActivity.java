package se.chalmers.student.devit.resekompanjon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.BackendCommunicator;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoConnectionException;
import se.chalmers.student.devit.resekompanjon.backend.utils.JsonInfoExtract;
import se.chalmers.student.devit.resekompanjon.backend.utils.OnTaskCompleted;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.FavoriteHandler;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;

/**
 * @author Jonathan
 * @version 0.1
 */
public class FavoritesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnTaskCompleted {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FavoriteArrayAdapter adapter;
    private ListView listView;
    private ArrayList<JsonObject> list;

    public FavoritesActivity() {}

    private BackendCommunicator bComm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FavoriteHandler handler = new FavoriteHandler(this);
        JsonArray jsonArray = handler.getTripArrayAsJson();
        list = new ArrayList<>();
        for(int i=0; i<jsonArray.size(); i++) {
            JsonObject obj = jsonArray.get(i).getAsJsonObject();
            list.add(obj);
        }

        setContentView(R.layout.favorite_layout);
        listView = (ListView) findViewById(R.id.list);
        adapter = new FavoriteArrayAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        bComm = new BackendCommunicator(this, this);

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
        DateFormat travelDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat travelTimeFormatter = new SimpleDateFormat("HH:mm");
        Date currentTime = Calendar.getInstance().getTime();
        String startLocation = list.get(position).get("originName").getAsString();
        String endLocation = list.get(position).get("endName").getAsString();
        try{
            bComm.getTripByName(startLocation, endLocation, travelTimeFormatter.format(currentTime)
                    , travelDateFormatter.format(currentTime));
        } catch (NoConnectionException e) {
            Toast noConectionMessage = Toast.makeText(this
                    , "OBS! Internetanslutning krävs!", Toast.LENGTH_LONG);
            noConectionMessage.show();
            e.printStackTrace();
        }
        setContentView(R.layout.loading_layout);
        TextView loadingView = (TextView)this.findViewById(R.id.loadingMessage);
        loadingView.setText(R.string.loading_search_result);
    }

    @Override
    public void onTaskCompleted() {
        JsonObject fromAPI= bComm.getApiData().getAsJsonObject();
        JsonInfoExtract tripResult = new JsonInfoExtract(fromAPI);
        ArrayList<SearchResaultTrips> searchedTrips = tripResult.getTripAdvice();
        SearchResultListActivity.setTrips(searchedTrips);
        startActivity(new Intent(FavoritesActivity.this, SearchResultListActivity.class));
        finish();
    }
}
