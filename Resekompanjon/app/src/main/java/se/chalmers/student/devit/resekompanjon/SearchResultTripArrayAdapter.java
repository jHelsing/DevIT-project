package se.chalmers.student.devit.resekompanjon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;

/**
 * @author Jonathan
 * @version 1.0
 */
public class SearchResultTripArrayAdapter extends ArrayAdapter<SearchResaultTrips> {

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_search_result_box, parent, false);
        SearchResaultTrips trip = values.get(index);
        TextView departure = (TextView) rowView.findViewById(R.id.depa11rture);
        TextView arrival = (TextView) rowView.findViewById(R.id.arrival);
        TextView travelTime = (TextView) rowView.findViewById(R.id.travelTime);
        TextView delay = (TextView) rowView.findViewById(R.id.delay);

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
        return rowView;
    }

}