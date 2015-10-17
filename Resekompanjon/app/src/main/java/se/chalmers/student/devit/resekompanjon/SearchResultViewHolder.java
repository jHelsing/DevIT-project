package se.chalmers.student.devit.resekompanjon;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.PrenumHandler;

/**
 * Simple class for holding search result items.
 *
 * @author Jonathan
 * @version 1.0
 */
public class SearchResultViewHolder {

    private final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
    private TextView departureText;
    private TextView arrivalText;
    private TextView delay;
    private TextView travelTime;
    private ImageButton button;

    public SearchResultViewHolder(View v) {
        departureText = (TextView) v.findViewById(R.id.depa11rture);
        arrivalText = (TextView) v.findViewById(R.id.arrival);
        delay = (TextView) v.findViewById(R.id.delay);
        travelTime = (TextView) v.findViewById(R.id.travelTime);
        button = (ImageButton) v.findViewById(R.id.checkboxButton);
    }

    public TextView getDepartureText() {
        return departureText;
    }

    public TextView getArrivalText() {
        return arrivalText;
    }

    public void setArrivalText(TextView arrivalText) {
        this.arrivalText = arrivalText;
    }

    public TextView getDelay() {
        return delay;
    }

    public void setDelay(TextView delay) {
        this.delay = delay;
    }

    public TextView getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(TextView travelTime) {
        this.travelTime = travelTime;
    }

    public ImageButton getButton() {
        return button;
    }

    public void setButton(ImageButton button) {
        this.button = button;
    }

    public void setViewFields(SearchResaultTrips trip) {
        int departureHour = Integer.parseInt(trip.getOriginTime().substring(0, 2));
        int departureMin = Integer.parseInt(trip.getOriginTime().substring(3));
        int arrivalHour = Integer.parseInt(trip.getDestinationTime().substring(0, 2));
        int arrivalMin = Integer.parseInt(trip.getDestinationTime().substring(3));

        final SearchResaultTrips trip2 = trip;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, departureHour);
        cal.set(Calendar.MINUTE, departureMin);
        departureText.setText(timeFormatter.format(cal.getTime()));
        cal.set(Calendar.HOUR_OF_DAY, arrivalHour);
        cal.set(Calendar.MINUTE, arrivalMin);
        arrivalText.setText(timeFormatter.format(cal.getTime()));

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrenumHandler handler = new PrenumHandler(v.getContext());
                JsonArray arr = handler.getTripArrayAsJson();
                JsonObject tripAsJson = new JsonObject();
                tripAsJson.addProperty("originName", trip2.getOriginName());
                tripAsJson.addProperty("originID", trip2.getOriginId());
                tripAsJson.addProperty("endName", trip2.getDestinationName());
                tripAsJson.addProperty("endID", trip2.getDestinationId());
                tripAsJson.addProperty("date", trip2.getOriginDate());
                tripAsJson.addProperty("time", trip2.getOriginTime());
                tripAsJson.addProperty("ref", trip2.getRef());
                Log.d("ARR:SIZE", arr.size() + "");
                if(arr.size() != 0) {
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
                        handler.addToPrenumTrips(trip2.getOriginName(), trip2.getOriginId(),
                                trip2.getDestinationName(), trip2.getDestinationId(), trip2.getRef(),
                                trip2.getOriginDate(), trip2.getOriginTime());
                        button.setImageResource(R.drawable.checkbox_toggled);
                    }
                } else {
                    handler.addToPrenumTrips(trip2.getOriginName(), trip2.getOriginId(),
                            trip2.getDestinationName(), trip2.getDestinationId(), trip2.getRef(),
                            trip2.getOriginDate(), trip2.getOriginTime());
                    button.setImageResource(R.drawable.checkbox_toggled);
                }
                Log.d("HEJ", "KOM HIT1");
            }
        });
    }
}
