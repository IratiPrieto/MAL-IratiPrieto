package model.beans;

import java.util.ArrayList;

public class UserProfile {
    private String id;
    private String name;
    private ArrayList<loc> locations = new ArrayList<>();
    private ArrayList<loc> friendLocations = new ArrayList<>();
    private Double time, distance;
    private Double safeLat, safeLong;
    private loc tempLoc;
    private ArrayList<String> friends = new ArrayList<>(); //Friends' UIDs
    private ArrayList<String> friendsEmails = new ArrayList<>(); //Friends' Emails
    private String newFriend="", m_text="";
    private boolean check=false, friendExists=false;




    public ArrayList<loc> getFriendLocations() {
        return friendLocations;
    }

    public void setFriendLocations(ArrayList<loc> locations) {
        this.friendLocations = locations;
    }

    public void addFriendLocations(loc locations) {
        this.friendLocations.add(locations);
    }

    public void delFriend(int i){
        for(int j=i; i<friends.size()-1; i++){
            friends.set(j, friends.get(j+1));
            friendsEmails.set(j, friendsEmails.get(j+1));
        }
        friends.remove(friends.size()-1);
        friendsEmails.remove(friendsEmails.size()-1);
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean getFriendExists() {
        return friendExists;
    }

    public void setFriendExists(boolean friendExists) {
        this.friendExists = friendExists;
    }

    public String getM_text() {
        return m_text;
    }

    public void setM_text(String m_text) {
        this.m_text = m_text;
    }

    public ArrayList<String> getFriendsEmails() {
        return friendsEmails;
    }

    public void setFriendsEmails(ArrayList<String> friendsEmails) {
        this.friendsEmails = friendsEmails;
    }
    public void setFriendEmail(int pos, String email){
        friendsEmails.set(pos, email);
    }
    public void addFriendEmail(String friend){
        this.friendsEmails.add(friend);
    }
    public String getNewFriend() {
        return newFriend;
    }

    public void setNewFriend(String newFriend) {
        this.newFriend = newFriend;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
    public void addFriend(String friend){
        this.friends.add(friend);
    }

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
