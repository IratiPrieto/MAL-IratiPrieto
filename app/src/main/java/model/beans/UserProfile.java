package model.beans;

import java.util.ArrayList;

public class UserProfile {
    private String id;
    private String name;
    private ArrayList<loc> locations = new ArrayList<>();
    private Double time, distance;
    private Double safeLat, safeLong;
    private loc tempLoc;


    public loc getTempLoc() {
        return tempLoc;
    }

    public void setTempLoc(loc tempLoc) {
        this.tempLoc = tempLoc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<loc> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<loc> locations) {
        this.locations = locations;
    }

    public void addLocations(loc locations) {
        this.locations.add(locations);
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getSafeLat() {
        return safeLat;
    }

    public void setSafeLat(Double safeLat) {
        this.safeLat = safeLat;
    }

    public Double getSafeLong() {
        return safeLong;
    }

    public void setSafeLong(Double safeLong) {
        this.safeLong = safeLong;
    }
}