package se.chalmers.student.devit.resekompanjon.backend.connectionBackend;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.utils.JsonInfoExtract;
import se.chalmers.student.devit.resekompanjon.backend.utils.OnTaskCompleted;

/**
 * Created by Marcus on 2015-10-13.
 *
 * Handles communication between backend classes and activity
 * Mostly created to make it possible to search for trips by string names
 */
public class BackendCommunicator implements OnTaskCompleted{
    private VasttrafikBackend vbackend;
    private ElectricityBackend eBackend;
    private JsonObject apiData;
    private OnTaskCompleted listener;
    private boolean severalStepsNeeded = false;
    private JsonObject apiTempOrigin = null;
    private JsonObject apiTempDest= null;
    private ArrayList<String> tempStrings = new ArrayList<>();
    private JsonInfoExtract jsonInfoExtract;

    public BackendCommunicator(Context context, OnTaskCompleted listener){
        vbackend = new VasttrafikBackend(context.getApplicationContext(), this);
        eBackend = new ElectricityBackend(context.getApplicationContext(), this);
        this.listener = listener;
    }

    public void getAllVehiclesFromStop(int id) throws NoConnectionException{
        vbackend.getAllVehiclesFromStop(id);
    }
    public void getTripCoord(Double originLat, Double originLong, String originName, Double destLat, Double destLong, String destName) throws NoConnectionException {
        vbackend.getTripCoord(originLat, originLong, originName, destLat, destLong, destName);
    }
    public void getAllStops() throws NoConnectionException {
        vbackend.getAllStops();
    }
    public void getStationbyName(String stop) throws NoConnectionException{
        vbackend.getStationbyName(stop);
    }
    /**
     * Using ID is preferred in ALL cases as the name of bus stop isn't working
     *
     * @param originName name of bus stop, will always take first result
     * @param destName name of bus stop, will always take first result
     * @param date needs to be on format [YYYY-MM-DD]
     * @param time needs to be on format [XX:XX]
     */
    public void getTripByName(String originName, String destName, String time, String date) throws NoConnectionException{
        vbackend.getStationbyName(originName);
        severalStepsNeeded = true;
        if (tempStrings.isEmpty()) {
            tempStrings.add(originName);
            tempStrings.add(destName);
            tempStrings.add(time);
            tempStrings.add(date);
        }
    }

    @Override
    public void onTaskCompleted() {
        if (!severalStepsNeeded){
            listener.onTaskCompleted();
        }else {
            try {
                handleTripByNameSearch();
            } catch (NoConnectionException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Only used to keep logic out of onTaskCompleted
     */
    private void handleTripByNameSearch() throws NoConnectionException {
        if (apiTempOrigin == null){
            apiTempOrigin = vbackend.getApiData();
            try {
                vbackend.getStationbyName(tempStrings.get(0));
            } catch (NoConnectionException e) {
                e.printStackTrace();
            }
        } else if (apiTempDest == null){
            apiTempDest = vbackend.getApiData();
            jsonInfoExtract = new JsonInfoExtract(apiTempOrigin);
            tempStrings.add(jsonInfoExtract.getStopsFromSearchString().get(0).getId()); //adds onto index 4
            jsonInfoExtract = new JsonInfoExtract(apiTempDest);
            tempStrings.add(jsonInfoExtract.getStopsFromSearchString().get(0).getId()); //adds onto index 5
            severalStepsNeeded = false;
            vbackend.getTripID(tempStrings.get(4),tempStrings.get(5), tempStrings.get(2), tempStrings.get(3));
            apiTempOrigin = null;
            apiTempDest = null;
        }
    }

    public JsonObject getApiData(){
        return apiData;
    }
}
