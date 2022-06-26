package model.backend;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import model.beans.UserProfile;

public interface Backend{


    Task<Void> logout();
    Task<DataSnapshot> saveTemp();
    Task<UserProfile> getTemp();
    Task<UserProfile> getUserData();
    Task<DataSnapshot> setUserData();
    Task<DataSnapshot> setUserSafeData();
    Task<DataSnapshot> setUserSettings();
    Task<DataSnapshot> addFriend();
    Task<DataSnapshot> getFriendID();
    Task<DataSnapshot> addingFriend();
    Task<Void> checkingFriend(String id);
    Task<DataSnapshot> checkFriend(String id);
    Task<DataSnapshot> deleteFriend(int i);
    Task<UserProfile> getFriendLoc(int i);


    UserProfile getUserProfile();
}
