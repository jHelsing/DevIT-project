package se.chalmers.student.devit.resekompanjon.backend;

/**
 * Created by emmafahlen on 2015-10-07.
 */
public class StopsFromString {
    private final String name;
    private final String lon;
    private final String lat;
    private final String id;
    private final String idx;

    public StopsFromString(String name, String lon, String lat, String id, String idx){

        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.id = id;
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public String getId() {
        return id;
    }

    public String getIdx() {
        return idx;
    }
}