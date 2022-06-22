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

    UserProfile getUserProfile();
}
