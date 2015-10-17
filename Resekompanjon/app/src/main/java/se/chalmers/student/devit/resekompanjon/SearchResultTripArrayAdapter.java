package se.chalmers.student.devit.resekompanjon;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.PrenumHandler;
import se.chalmers.student.devit.resekompanjon.fragment.SearchResultBoxFragment;

/**
 * @author Jonathan
 * @version 1.0
 */
public class SearchResultTripArrayAdapter extends ArrayAdapter<SearchResaultTrips> implements View.OnClickListener, ListView.OnItemClickListener {

    private final Context context;

    private ArrayList<SearchResaultTrips> values;

    private final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

    public SearchResultTripArrayAdapter(Context context, ArrayList<SearchResaultTrips> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_search_result_box, null);
        }
        SearchResaultTrips trip = values.get(index);
        TextView departure = (TextView) convertView.findViewById(R.id.depa11rture);
        TextView arrival = (TextView) convertView.findViewById(R.id.arrival);
        TextView travelTime = (TextView) convertView.findViewById(R.id.travelTime);
        TextView delay = (TextView) convertView.findViewById(R.id.delay);
        int departureHour = Integer.parseInt(trip.getOriginTime().substring(0, 2));
        int departureMin = Integer.parseInt(trip.getOriginTime().substring(3));
        int arrivalHour = Integer.parseInt(trip.getDestinationTime().substring(0, 2));
        int arrivalMin = Integer.parseInt(trip.getDestinationTime().substring(3));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, departureHour);
        cal.set(Calendar.MINUTE, departureMin);
        departure.setText(timeFormatter.format(cal.getTime()));
        cal.set(Calendar.HOUR_OF_DAY, arrivalHour);
        cal.set(Calendar.MINUTE, arrivalMin);
        arrival.setText(timeFormatter.format(cal.getTime()));

        int travelHour = arrivalHour-departureHour;
        if(travelHour < 0)
            travelHour = travelHour+24;
        int travelMin = arrivalMin-departureMin;
        if(travelMin < 0) {
            travelHour = travelHour-1;
            travelMin = 60 + travelMin;
        }

        cal.set(Calendar.HOUR_OF_DAY, travelHour);
        cal.set(Calendar.MINUTE, travelMin);
        String totalTravelTime = timeFormatter.format(cal.getTime());
        travelTime.setText(totalTravelTime);
        delay.setText("XX:XX");

        return convertView;

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

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
        Log.d("HEJ", "KOM BARA HIT");
        switch(view.getId()) {
            case R.id.checkboxButton:
                ImageButton button = (ImageButton) view;
                SearchResaultTrips trip = values.get(position);
                PrenumHandler handler = new PrenumHandler(getContext());
                JsonArray arr = handler.getTripArrayAsJson();
                JsonObject tripAsJson = new JsonObject();
                tripAsJson.addProperty("originName", trip.getOriginName());
                tripAsJson.addProperty("originID", trip.getOriginId());
                tripAsJson.addProperty("endName", trip.getDestinationName());
                tripAsJson.addProperty("endID", trip.getDestinationId());
                tripAsJson.addProperty("date", trip.getOriginDate());
                tripAsJson.addProperty("time", trip.getOriginTime());
                tripAsJson.addProperty("ref", trip.getRef());
                int i=0;
                JsonObject tempObj = arr.get(i).getAsJsonObject();
                while(i<arr.size() && !tripAsJson.equals(tempObj)) {
                    i++;
                    tempObj = arr.get(i).getAsJsonObject();
                }
                if(tripAsJson.equals(tempObj)) {
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
        Log.d("HEJ", "KOM HIT2");
    }
}