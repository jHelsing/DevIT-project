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

import se.chalmers.student.devit.resekompanjon.backend.SearchResaultTrips;

/**
 * Created by jonat on 10/10/2015.
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
        TextView departure = (TextView) rowView.findViewById(R.id.departure);
        TextView arrival = (TextView) rowView.findViewById(R.id.arrival);
        TextView travelTime = (TextView) rowView.findViewById(R.id.travelTime);
        TextView delay = (TextView) rowView.findViewById(R.id.delay);
        departure.setText(timeFormatter.format(trip.getOriginTime()));
        arrival.setText(timeFormatter.format(trip.getDestinationTime()));

        int departureHour = Integer.getInteger(timeFormatter.format(trip.getOriginTime()).substring(0, 2));
        int departureMin = Integer.getInteger(timeFormatter.format(trip.getOriginTime()).substring(3,5));
        int arrivalHour = Integer.getInteger(timeFormatter.format(trip.getDestinationTime()).substring(0,2));
        int arrivalMin = Integer.getInteger(timeFormatter.format(trip.getDestinationTime()).substring(3,5));

        int travelHour = arrivalHour-departureHour;
        if(travelHour < 0) travelHour = travelHour+24;
        int travelMin = arrivalMin-departureMin;
        if(travelMin < 0) {
            travelHour = travelHour-1;
            travelMin = 60 + travelMin;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, travelHour);
        cal.set(Calendar.MINUTE, travelMin);
        String totalTravelTime = timeFormatter.format(cal.getTime());
        travelTime.setText(totalTravelTime);

        delay.setText("XX:XX");

        return rowView;
    }

}
