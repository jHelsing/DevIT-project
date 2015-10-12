package se.chalmers.student.devit.resekompanjon.backend.utils.json;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
/**
 * Created by emmafahlen on 2015-10-06.
 */
public class VehicleInfo {

    private final String name;
    private final String sname;
    private final String type;
    private final String stopid;
    private final String stop;
    private final String time;
    private final String date;
    private final String journeyid;
    private final String direction;
    private final String track;
    private final String rtTime;
    private final String stDate;
    private final String fgColor;
    private final String bgColor;
    private final String stroke;
    private final String accessibility;
    private final JsonObject JourneyDetailRef;

    public VehicleInfo(String name, String sname, String type, String stopid, String stop,
                       String time, String date, String journeyid, String direction,
                       String track, String rtTime, String stDate, String fgColor,
                       String bgColor, String stroke, String accessibility, JsonObject JourneyDetailRef){

        this.name = name;
        this.sname = sname;
        this.type = type;
        this.stopid = stopid;
        this.stop = stop;
        this.time = time;
        this.date = date;
        this.journeyid = journeyid;
        this.direction = direction;
        this.track = track;
        this.rtTime = rtTime;
        this.stDate = stDate;
        this.fgColor = fgColor;
        this.bgColor = bgColor;
        this.stroke = stroke;
        this.accessibility = accessibility;
        this.JourneyDetailRef = JourneyDetailRef;
    }
    public String getName() {
        return name;
    }

    public String getSname() {
        return sname;
    }

    public String getType() {
        return type;
    }

    public String getStopid() {
        return stopid;
    }

    public String getStop() {
        return stop;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getJourneyid() {
        return journeyid;
    }

    public String getDirection() {
        return direction;
    }

    public String getTrack() {
        return track;
    }

    public String getRtTime() {
        return rtTime;
    }

    public String getStDate() {
        return stDate;
    }

    public String getFgColor() {
        return fgColor;
    }

    public String getBgColor() {
        return bgColor;
    }

    public String getStroke() {
        return stroke;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public String getJourneydetailref() {
        return JourneyDetailRef.get("ref").getAsString();

    }
}
