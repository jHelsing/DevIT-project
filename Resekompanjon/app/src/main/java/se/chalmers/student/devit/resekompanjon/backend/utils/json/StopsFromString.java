package se.chalmers.student.devit.resekompanjon.backend.utils.json;

/**
 * Created by emmafahlen on 2015-10-07.
 */
public class StopsFromString {
    private final String name;
    private final String lon;
    private final String lat;
    private final String id;
    private final String idx;
    private final String type;

    public StopsFromString(String name, String lon, String lat, String id, String idx, String type) {

        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.id = id;
        this.idx = idx;
        this.type = type;
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

    public String getType(){ return type; }
}
