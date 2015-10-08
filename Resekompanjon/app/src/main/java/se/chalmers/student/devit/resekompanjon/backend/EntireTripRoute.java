package se.chalmers.student.devit.resekompanjon.backend;

/**
 * Created by emmafahlen on 2015-10-08.
 */
public class EntireTripRoute {
    String name;
    String id;
    String lon;
    String lat;
    String routeIdx;
    String detTime;
    String detDate;
    String track;

    public EntireTripRoute(String name, String id, String lon, String lat, String routeIdx, String detTime, String detDate, String track) {
        this.name = name;
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.routeIdx = routeIdx;
        this.detTime = detTime;
        this.detDate = detDate;
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public String getRouteIdx() {
        return routeIdx;
    }

    public String getDetTime() {
        return detTime;
    }

    public String getDetDate() {
        return detDate;
    }

    public String getTrack() {
        return track;
    }
}
