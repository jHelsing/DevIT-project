package se.chalmers.student.devit.resekompanjon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.utils.readers.FavoriteHandler;
import se.chalmers.student.devit.resekompanjon.fragment.NavigationDrawerFragment;

/**
 * @author Jonathan
 * @version 0.1
 */
public class FavoritesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FavoriteArrayAdapter adapter;
    private ListView listView;
    private ArrayList<JsonObject> list;

    public FavoritesActivity() {}

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
