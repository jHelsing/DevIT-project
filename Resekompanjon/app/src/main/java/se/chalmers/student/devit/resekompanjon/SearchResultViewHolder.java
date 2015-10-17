package se.chalmers.student.devit.resekompanjon;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Simple class for holding search result items.
 *
 * @author Jonathan
 * @version 1.0
 */
public class SearchResultViewHolder {

    private TextView departureTime;
    private TextView arrivalTime;
    private TextView delay;
    private TextView travelTime;
    private ImageButton button;

    public SearchResultViewHolder(View v) {
        departureTime = (TextView) v.findViewById(R.id.depa11rture);
        arrivalTime = (TextView) v.findViewById(R.id.arrival);
        delay = (TextView) v.findViewById(R.id.delay);
        travelTime = (TextView) v.findViewById(R.id.travelTime);
        button = (ImageButton) v.findViewById(R.id.checkboxButton);
    }

    public TextView getDepartureTime() {
        return departureTime;
    }

    public TextView getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(TextView arrivalTime) {
        this.arrivalTime = arrivalTime;
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
}
