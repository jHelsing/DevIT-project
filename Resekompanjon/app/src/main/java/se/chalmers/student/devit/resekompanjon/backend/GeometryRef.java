package se.chalmers.student.devit.resekompanjon.backend;

/**
 * Created by emmafahlen on 2015-10-08.
 */
public class GeometryRef {
    String lat;
    String lon;

    public GeometryRef(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
