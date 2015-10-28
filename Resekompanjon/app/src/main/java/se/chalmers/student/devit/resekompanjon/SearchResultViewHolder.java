package se.chalmers.student.devit.resekompanjon;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResultTripSummary;
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

    public void setViewFields(SearchResultTripSummary trip) {
        int departureHour = Integer.parseInt(trip.getDepartureTime().substring(0, 2));
        int departureMin = Integer.parseInt(trip.getDepartureTime().substring(3));
        int arrivalHour = Integer.parseInt(trip.getArrivalTime().substring(0, 2));
        int arrivalMin = Integer.parseInt(trip.getArrivalTime().substring(3));

        final SearchResultTripSummary trip2 = trip;
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

        int realDepHour = Integer.parseInt(trip.getRealDepartureTime().substring(0, 2));
        int realDepMin = Integer.parseInt(trip.getRealDepartureTime().substring(3));
        boolean negative = false;
        int delayHour = realDepHour - departureHour;
        if(delayHour < 0)
            negative = true;
            delayHour = delayHour * -1;
        int delayMin = realDepMin - departureMin;
        if(delayMin < 0) {
            negative = true;
            delayMin = delayMin * -1;

        }
        cal.set(Calendar.HOUR_OF_DAY, delayHour);
        cal.set(Calendar.MINUTE, delayMin);
        String totalDelayTime = timeFormatter.format(cal.getTime());
        if ( negative ){
            delay.setText("-" + totalDelayTime);
            negative = false;
        }
        delay.setText(totalDelayTime);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrenumHandler handler = new PrenumHandler(v.getContext());
                JsonArray arr = handler.getTripArrayAsJson();
                JsonObject tripAsJson = new JsonObject();
                tripAsJson.addProperty("originName", trip2.getOriginName());
                tripAsJson.addProperty("originID", trip2.getOriginID());
                tripAsJson.addProperty("endName", trip2.getDestinationName());
                tripAsJson.addProperty("endID", trip2.getDestinationId());
                tripAsJson.addProperty("date", trip2.getDepartureDate());
                tripAsJson.addProperty("time", trip2.getDepartureTime());
                Log.d("ARR:SIZE", arr.size() + "");

                handler.addToPrenumTrips(trip2.getOriginName(), trip2.getOriginID(),
                        trip2.getDestinationName(), trip2.getDestinationId(),
                        trip2.getDepartureDate(), trip2.getDepartureTime());
                button.setImageResource(R.drawable.checkbox_toggled);
            }
        });
    }
}
