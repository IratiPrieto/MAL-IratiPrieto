package model.backend;

import model.beans.UserProfile;

public class Data {
    private UserProfile user = new UserProfile();
    public static Data instance = null;

    public Data(){}
    public static synchronized Data getInstance(){
        if(instance == null)
            instance = new Data();
        return instance;
    }


    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

}
