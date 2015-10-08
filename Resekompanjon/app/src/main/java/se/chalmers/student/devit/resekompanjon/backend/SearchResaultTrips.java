package se.chalmers.student.devit.resekompanjon.backend;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by emmafahlen on 2015-10-07.
 */
public class SearchResaultTrips {
    String name;
    String type;
    String sname;
    String id;
    String direction;
    String fgColor;
    String bgColor;
    String stroke;
    String accessibility;
    JsonObject Origin;
    JsonObject Destination;
    JsonObject JourneyDetailRef;
    //JsonObject Leg;

    public SearchResaultTrips(String name, String type, String sname, String id, String direction,
                              String fgColor, String bgColor, String stroke, String accessibility,
                              JsonObject Origin, JsonObject Destination, JsonObject JourneyDetailRef){
        this.name = name;
        this.type = type;
        this.Origin = Origin;
        this.Destination = Destination;
        this.sname = sname;
        this.id = id;
        this.direction = direction;
        this.fgColor = fgColor;
        this.bgColor = bgColor;
        this.stroke = stroke;
        this.accessibility = accessibility;
        this.JourneyDetailRef = JourneyDetailRef;
    }

    public SearchResaultTrips(String name, String type, JsonObject origin, JsonObject destination,
                              String sname, String id, String direction, String fgColor, String bgColor,
                              String stroke, JsonObject Origin, JsonObject Destination, JsonObject JourneyDetailRef){
        this.name = name;
        this.type = type;
        this.Origin = origin;
        this.Destination = destination;
        this.sname = sname;
        this.id = id;
        this.direction = direction;
        this.fgColor = fgColor;
        this.bgColor = bgColor;
        this.stroke = stroke;
        this.JourneyDetailRef = JourneyDetailRef;
    }

    public SearchResaultTrips(String name, String type) {
        this.name = name;
        this.type = type;
    }

    //public SearchResaultTrips(JsonObject Leg) {
    //    this.Leg = Leg;
    //}


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public JsonObject getOrigin() {
        return Origin;
    }

    public JsonObject getDestination() {
        return Destination;
    }

    public String getOriginName(){
        return Origin.get("name").getAsString();
    }
    public String getOriginType(){
        return Origin.get("type").getAsString();
    }
    public String getOriginTime(){
        return Origin.get("time").getAsString();
    }
    public String getOriginDate(){
        return Origin.get("date").getAsString();
    }
    public String getOrigin$(){
        return Origin.get("$").getAsString();
    }
    public String getOriginRouteIdx(){
        return Origin.get("routeIdx").getAsString();
    }
    public String getOriginTrack(){
        return Origin.get("track").getAsString();
    }
    public String getOriginRtTime(){
        return Origin.get("rtTime").getAsString();
    }
    public String getOriginRtDate(){
        return Origin.get("rtDate").getAsString();
    }
    public String getOriginId(){
        return Origin.get("id").getAsString();
    }
    public String getDestinationName(){
        return Destination.get("name").getAsString();
    }
    public String getDestinationType(){
        return Destination.get("type").getAsString();
    }
    public String getDestinationTime(){
        return Destination.get("time").getAsString();
    }
    public String getDestinationDate(){
        return Destination.get("date").getAsString();
    }
    public String getDestination$(){
        return Destination.get("$").getAsString();
    }
    public String getDestinationRouteIdx(){
        return Destination.get("routeIdx").getAsString();
    }
    public String getDestinationTrack(){
        return Destination.get("track").getAsString();
    }
    public String getDestinationRtTime(){
        return Destination.get("rtTime").getAsString();
    }
    public String getDestinationRtDate(){
        return Destination.get("rtDate").getAsString();
    }
    public String getDestinationId(){
        return Destination.get("id").getAsString();
    }
}
