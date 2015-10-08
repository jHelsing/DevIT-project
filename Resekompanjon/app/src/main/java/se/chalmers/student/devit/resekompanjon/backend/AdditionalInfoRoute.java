package se.chalmers.student.devit.resekompanjon.backend;

/**
 * Created by emmafahlen on 2015-10-08.
 */
public class AdditionalInfoRoute {
    String fgColor;
    String bgColor;
    String stroke;
    String ref;
    String name;
    String routeIdxFrom;
    String routeIdxTo;
    String type;
    String id;
    String $;


    public AdditionalInfoRoute(String str1, String str2, String str3) {
        if(str1=="fgColor" && str2=="bgColor" && str3=="stroke"){
            this.fgColor=str1;
            this.bgColor=str2;
            this.stroke=str3;
        }
        else if(str1=="name" && str2=="routeIdxFrom" && str3=="routeIdxTo"){
            this.name=str1;
            this.routeIdxFrom=str2;
            this.routeIdxTo=str3;
        }
        else if(str1=="type" && str2=="routeIdxFrom" && str3=="routeIdxTo"){
            this.type=str1;
            this.routeIdxFrom=str2;
            this.routeIdxTo=str3;
        }
        else if(str1=="id" && str2=="routeIdxFrom" && str3=="routeIdxTo"){
            this.id=str1;
            this.routeIdxFrom=str2;
            this.routeIdxTo=str3;
        }
        else if(str1=="routeIdxFrom" && str2=="routeIdxTo" && str3=="$"){
            this.routeIdxFrom=str1;
            this.routeIdxTo=str2;
            this.$=str3;
        }
    }

    public AdditionalInfoRoute(String ref) {
        this.ref = ref;
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

    public String getRef() {
        return ref;
    }

    public String getName() {
        return name;
    }

    public String getRouteIdxFrom() {
        return routeIdxFrom;
    }

    public String getRouteIdxTo() {
        return routeIdxTo;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String get$() {
        return $;
    }
}
