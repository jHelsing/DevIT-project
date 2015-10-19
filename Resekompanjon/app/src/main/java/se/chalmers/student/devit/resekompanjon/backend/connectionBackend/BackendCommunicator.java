package se.chalmers.student.devit.resekompanjon.backend.connectionBackend;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonElement;
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
    private VasttrafikBackend vBackend;
    private ElectricityBackend eBackend;
    private JsonElement apiData;
    private OnTaskCompleted listener;
    private boolean severalStepsNeeded = false;
    private JsonObject apiTempOrigin = null;
    private JsonObject apiTempDest= null;
    private ArrayList<String> tempStrings = new ArrayList<>();
    private JsonInfoExtract jsonInfoExtract;
    private String ORIGIN;

    public BackendCommunicator(Context context, OnTaskCompleted listener){
        vBackend = new VasttrafikBackend(context.getApplicationContext(), this);
        eBackend = new ElectricityBackend(context.getApplicationContext(), this);
        this.listener = listener;
    }

    public void getAllVehiclesFromStop(int id) throws NoConnectionException{
        ORIGIN = "vBackend";
        vBackend.getAllVehiclesFromStop(id);
    }
    public void getTripCoord(Double originLat, Double originLong, String originName, Double destLat, Double destLong, String destName) throws NoConnectionException {
        ORIGIN = "vBackend";
        vBackend.getTripCoord(originLat, originLong, originName, destLat, destLong, destName);
    }
    public void getAllStops() throws NoConnectionException {
        ORIGIN = "vBackend";
        vBackend.getAllStops();
    }
    public void getStationbyName(String stop) throws NoConnectionException{
        ORIGIN = "vBackend";
        vBackend.getStationbyName(stop);
    }
    public void getFromPremadeUrl(String url) throws NoConnectionException{
        ORIGIN = "vBackend";
        vBackend.getFromPremadeUrl(url);
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
        ORIGIN = "vBackend";
        vBackend.getStationbyName(originName);
        severalStepsNeeded = true;
        if (tempStrings.isEmpty()) {
            tempStrings.add(originName);
            tempStrings.add(destName);
            tempStrings.add(time);
            tempStrings.add(date);
        }
    }

    public void getElectricityJourneyInfo() throws NoConnectionException{
        ORIGIN = "eBackend";
        eBackend.getJourneyInfo();
    }

    public void getElectricityNextStopInfo() throws NoConnectionException{
        ORIGIN = "eBackend";
        eBackend.getNextStopInfo();
    }

    public void getElectricityStopPressedInfo() throws NoConnectionException{
        ORIGIN = "eBackend";
        eBackend.getStopPressedInfo();
    }

    @Override
    public void onTaskCompleted() {
        Log.d("origin: ", ORIGIN);
        if (!severalStepsNeeded){
            if (ORIGIN.equals("vBackend")) {
                apiData = vBackend.getApiData();
            } else if (ORIGIN == "eBackend"){
                apiData = eBackend.getApiData();
            }
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
            apiTempOrigin = vBackend.getApiData();
        } else if (apiTempDest == null){
            apiTempDest = vBackend.getApiData();
        }
        if (apiTempOrigin == null){
            vBackend.getStationbyName(tempStrings.get(0));
        } else if (apiTempDest == null){
            vBackend.getStationbyName(tempStrings.get(1));
        }
        if (apiTempDest != null && apiTempOrigin != null) {
            jsonInfoExtract = new JsonInfoExtract(apiTempOrigin);
            tempStrings.add(jsonInfoExtract.getStopsFromSearchString().get(0).getId()); //adds onto index 4
            jsonInfoExtract = new JsonInfoExtract(apiTempDest);
            tempStrings.add(jsonInfoExtract.getStopsFromSearchString().get(0).getId()); //adds onto index 5
            severalStepsNeeded = false;
            vBackend.getTripID(tempStrings.get(4), tempStrings.get(5), tempStrings.get(2), tempStrings.get(3));
            tempStrings.clear();
            apiTempOrigin = null;
            apiTempDest = null;
        }
    }

    public JsonElement getApiData() throws NoJsonAvailableException {
        if (apiData == null){
            throw new NoJsonAvailableException();
        }
        return apiData;
    }
}
