package se.chalmers.student.devit.resekompanjon.backend;

/**
 * Created by emmafahlen on 2015-10-07.
 */
public class AdressNearby {
    String name;
    String lat;
    String lon;
    String type;

    public AdressNearby(String name, String lat, String lon, String type) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getType() {
        return type;
    }
}
