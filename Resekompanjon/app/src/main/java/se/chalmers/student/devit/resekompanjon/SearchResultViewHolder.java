package se.chalmers.student.devit.resekompanjon;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;

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
    }
}
