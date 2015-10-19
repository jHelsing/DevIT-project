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
    private ArrayList<SearchResultTrips> resaultTripsArrayList = new ArrayList<>();

    public SearchResultTripSummary(String originName, String originID, String startTime, String originDate,String realStartTime,
                                   String realOriginDate, String endName, String endID, String endTime, String endDate,
                                   String realEndTime, String realEndDate, ArrayList<SearchResultTrips> array){
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
        resaultTripsArrayList = array;
    }

    public String getOriginName() {
        return originName;
    }
    public String getOriginID() {
        return originID;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getOriginDate() {
        return originDate;
    }

    public String getRealStartTime() {
        return realStartTime;
    }

    public String getRealOriginDate() {
        return realOriginDate;
    }

    public String getEndName() {
        return endName;
    }

    public String getEndID() {
        return endID;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getRealEndTime() {
        return realEndTime;
    }

    public String getRealEndDate() {
        return realEndDate;
    }

    public ArrayList<SearchResultTrips> getResaultTripsArrayList() {
        return resaultTripsArrayList;
    }
    public void setResaultTripArrayList(ArrayList<SearchResultTrips> arrayList){
        this.resaultTripsArrayList = arrayList;
    }
}
