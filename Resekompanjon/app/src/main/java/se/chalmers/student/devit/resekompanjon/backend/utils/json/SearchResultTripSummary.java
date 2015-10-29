package se.chalmers.student.devit.resekompanjon.backend.utils.json;

import java.util.ArrayList;

/**
 * Created by Marcus on 2015-10-19.
 */
public class SearchResultTripSummary {

    private String originName;
    private String originID;
    private String startTime;
    private String originDate;
    private String realStartTime;
    private String realOriginDate;
    private String endName;
    private String endID;
    private String endDate;
    private String endTime;
    private String realEndTime;
    private String realEndDate;
    private ArrayList<SearchResultTrips> resultTripsArrayList = new ArrayList<>();

    public SearchResultTripSummary(String originName, String originID, String startTime, String originDate, String realStartTime,
                                   String realOriginDate, String endName, String endID, String endTime, String endDate,
                                   String realEndTime, String realEndDate, ArrayList<SearchResultTrips> array) {
        this.originName = originName;
        this.originID = originID;
        this.startTime = startTime;
        this.originDate = originDate;
        this.realOriginDate = realOriginDate;
        this.realStartTime = realStartTime;
        this.endName = endName;
        this.endID = endID;
        this.endTime = endTime;
        this.endDate = endDate;
        this.realEndTime = realEndTime;
        this.realEndDate = realEndDate;
        resultTripsArrayList = array;
    }

    public String getOriginName() {
        return originName;
    }

    public String getOriginID() {
        return originID;
    }

    public String getDepartureTime() {
        return startTime;
    }

    public String getDepartureDate() {
        return originDate;
    }

    public String getRealDepartureTime() {
        return realStartTime;
    }

    public String getRealDepartureDate() {
        return realOriginDate;
    }

    public String getDestinationName() {
        return endName;
    }

    public String getDestinationId() {
        return endID;
    }

    public String getDestinationDate() {
        return endDate;
    }

    public String getArrivalTime() {
        return endTime;
    }

    public String getRealArrivalTime() {
        return realEndTime;
    }

    public String getRealArrivalDate() {
        return realEndDate;
    }

    public ArrayList<SearchResultTrips> getResultTripsArrayList() {
        return resultTripsArrayList;
    }

    public void setResultTripArrayList(ArrayList<SearchResultTrips> arrayList) {
        this.resultTripsArrayList = arrayList;
    }
}
