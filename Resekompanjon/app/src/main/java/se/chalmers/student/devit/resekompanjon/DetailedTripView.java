package se.chalmers.student.devit.resekompanjon;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author  Jonathan
 * @version  0.1
 */
public class DetailedTripView extends Activity {

    private JsonObject trip;

    public DetailedTripView() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_trip_layout);
        Bundle bundle = getIntent().getExtras();
        // We know that this bundle will contain a key called JSON
        // that contains the JSON object of this trip.
        trip = new JsonParser().parse((String) bundle.get("JSON")).getAsJsonObject();
        initBasicTripInfo();
    }

    private void initBasicTripInfo() {
        //TODO initialise all the views with the right content
    }
}
