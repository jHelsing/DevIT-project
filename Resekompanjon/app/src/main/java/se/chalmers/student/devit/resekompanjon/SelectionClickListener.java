package se.chalmers.student.devit.resekompanjon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.EventLog;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.PrenumHandler;
import se.chalmers.student.devit.resekompanjon.fragment.SearchResultBoxFragment;

/**
 * @author Jonathan
 * @version 0.6
 */
public class SelectionClickListener implements AdapterView.OnItemClickListener {

    private final ArrayList<SearchResaultTrips> values;
    private ListView listView;
    private ActionMode actionMode;
    private Drawable uncheckedDrawable;
    private Drawable checkedDrawable;
    private Context context;
    private int selectedItems = 1;

    public SelectionClickListener(Context context, ListView listView, ActionMode actionMode,
                                  ArrayList<SearchResaultTrips> values) {
        this.context = context;
        this.listView = listView;
        this.actionMode = actionMode;
        this.values = values;
        listView.setOnItemClickListener(this);
        uncheckedDrawable = context.getResources().getDrawable(R.drawable.checkbox_untoggled);
        checkedDrawable = context.getResources().getDrawable(R.drawable.checkbox_toggled);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("HEJ", "KOM HIT1");
        switch (view.getId()) {
            case R.id.checkboxButton:
                SearchResultBoxFragment box = (SearchResultBoxFragment) listView.getItemAtPosition(position);
                SearchResaultTrips trip = values.get(position);
                ImageButton button = (ImageButton) view;
                PrenumHandler handler = new PrenumHandler(context);
                JsonArray arr = handler.getTripArrayAsJson();
                JsonObject tripAsJson = new JsonObject();
                tripAsJson.addProperty("originName", trip.getOriginName());
                tripAsJson.addProperty("originID", trip.getOriginId());
                tripAsJson.addProperty("endName", trip.getDestinationName());
                tripAsJson.addProperty("endID", trip.getDestinationId());
                tripAsJson.addProperty("date", trip.getOriginDate());
                tripAsJson.addProperty("time", trip.getOriginTime());
                tripAsJson.addProperty("ref", trip.getRef());
                int i = 0;
                JsonObject tempObj = arr.get(i).getAsJsonObject();
                while (i < arr.size() && !tripAsJson.equals(tempObj)) {
                    i++;
                    tempObj = arr.get(i).getAsJsonObject();
                }
                if (tripAsJson.equals(tempObj)) {
                    //Trip is already a planned trip
                    handler.removePrenum(i);
                    button.setImageResource(R.drawable.checkbox_untoggled);
                } else {
                    //Trip is not a planned trip, add it as one
                    handler.addToPrenumTrips(trip.getOriginName(), trip.getOriginId(),
                            trip.getDestinationName(), trip.getDestinationId(), trip.getRef(),
                            trip.getOriginDate(), trip.getOriginTime());
                    button.setImageResource(R.drawable.checkbox_toggled);
                }
                Log.d("HEJ", "KOM HIT1");
                break;
        }
    }
}