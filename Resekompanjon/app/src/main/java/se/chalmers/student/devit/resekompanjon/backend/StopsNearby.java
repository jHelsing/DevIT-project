package se.chalmers.student.devit.resekompanjon.backend;

/**
 * Created by emmafahlen on 2015-10-07.
 */
public class StopsNearby {
    String name;
    String id;
    String lat;
    String lon;
    String track;

    public StopsNearby(String name, String id, String lat, String lon, String track) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.track = track;
    }
    public StopsNearby(String name, String id, String lat, String lon) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getTrack() {
        return track;
    }
}
