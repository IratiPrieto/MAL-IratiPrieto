package model.backend;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import model.beans.UserProfile;
import model.beans.loc;

public class FBBackend extends FirebaseHelper implements Backend{
    Data data1 = Data.getInstance();

    private Context context;
    private Integer i=0;
    public Task<UserProfile> getUserData() {

        //Toast.makeText(context.getApplicationContext(), "Login4", Toast.LENGTH_SHORT).show();
        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        if( fireUser == null )
            return Tasks.forException(new Exception("Not logged"));
        //       user = new UserProfile();

        return dbRef("sessions", fireUser.getUid()).get()
                .continueWithTask( task -> {
                    DataSnapshot data = task.getResult();
                    if( data.hasChildren() ) {


                        i=0;
                        while(data.child("loc"+i).exists()){
                            loc location = new loc();
                            location.setDate(data.child("loc"+i).child("date").getValue(String.class));
                            location.setLatitude(data.child("loc"+i).child("latitude").getValue(Double.class));
                            location.setLongitude(data.child("loc"+i).child("longitude").getValue(Double.class));
                            data1.getUser().addLocations(location);
                            i++;
                        }
                        if(data.child("friends").exists()){
                            for(DataSnapshot ds : data.child("friends").getChildren()) {
                                data1.getUser().addFriend(ds.getKey());
                            }
                        }
                        data1.getUser().setTime(data.child("time").getValue(Double.class));
                        data1.getUser().setDistance(data.child("distance").getValue(Double.class));
                        data1.getUser().setSafeLat(data.child("safeLat").getValue(Double.class));
                        data1.getUser().setSafeLong(data.child("safeLong").getValue(Double.class));

                        return task;
                    } else return dbRef("sessions", 1).get().continueWith(subtask -> {
                        DataSnapshot session = subtask.getResult();
                        return null;
                    });
                }).continueWith(task -> {
                    task.getResult();   // To propagate possible error
                    return data1.getUser();
                });
    }
    @Override
    public Task<DataSnapshot> setUserData() {return null;}
    @Override
    public Task<DataSnapshot> setUserSafeData() {
        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        if( fireUser == null )
            return Tasks.forException(new Exception("Not logged"));
        //       user = new UserProfile();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference sessionRef = FirebaseDatabase.getInstance().getReference("sessions/" + data1.getUser().getId());
        return dbRef.child("sessions").child(data1.getUser().getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                } else {
                    DataSnapshot dataSnapshot = task.getResult();
                    dbRef.child("sessions").child(data1.getUser().getId()).child("safeLat").setValue(data1.getUser().getSafeLat());
                    dbRef.child("sessions").child(data1.getUser().getId()).child("safeLong").setValue(data1.getUser().getSafeLong());
                }
            }
        });
    }
    @Override
    public Task<DataSnapshot> setUserSettings() {
        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        if( fireUser == null )
            return Tasks.forException(new Exception("Not logged"));
        //       user = new UserProfile();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference sessionRef = FirebaseDatabase.getInstance().getReference("sessions/" + data1.getUser().getId());
        return dbRef.child("sessions").child(data1.getUser().getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                } else {
                    DataSnapshot dataSnapshot = task.getResult();
                    dbRef.child("sessions").child(data1.getUser().getId()).child("time").setValue(data1.getUser().getTime());
                    dbRef.child("sessions").child(data1.getUser().getId()).child("distance").setValue(data1.getUser().getDistance());
                }
            }
        });
    }
    @Override
    public Task<DataSnapshot> saveTemp() {
        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        if( fireUser == null )
            return Tasks.forException(new Exception("Not logged"));
        //       user = new UserProfile();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference sessionRef = FirebaseDatabase.getInstance().getReference("sessions/" + data1.getUser().getId());
        return dbRef.child("sessions").child(data1.getUser().getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                } else {
                    //data1 = Data.getInstance();
                    DataSnapshot dataSnapshot = task.getResult();
                    HashMap map = new HashMap();
                    map.put("loc"+i, "loc"+i);
                    dbRef.child("sessions").child(data1.getUser().getId()).updateChildren(map);

               //     dbRef.child("sessions").child(data1.getUser().getId()).updateChildren((Map<String, Object>) dbRef.child("sessions").child(data1.getUser().getId()).child("loc"+i));
                    HashMap map2 = new HashMap();
                    map2.put("latitude", data1.getUser().getTempLoc().getLatitude());
                    map2.put("longitude", data1.getUser().getTempLoc().getLongitude());
                    if(data1.getUser().getTempLoc().getDate()==null)
                        data1.getUser().getTempLoc().setDate("19/06/2022");
                    map2.put("date", data1.getUser().getTempLoc().getDate());

                    dbRef.child("sessions").child(data1.getUser().getId()).child("loc"+i).updateChildren(map2);

                    data1.getUser().addLocations(data1.getUser().getTempLoc());
                    i++;
                }
            }
        });
    }
    public Task<UserProfile> getTemp() {
        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        if( fireUser == null )
            return Tasks.forException(new Exception("Not logged"));
        //       user = new UserProfile();

        return dbRef("sessions", fireUser.getUid()).get()
                .continueWithTask( task -> {
                    DataSnapshot data = task.getResult();
                    if( data.hasChildren() ) {

                        loc temp = new loc();
                        temp.setLatitude(data.child("tempLoc").child("latitude").getValue(Double.class));
                        temp.setLongitude(data.child("tempLoc").child("longitude").getValue(Double.class));

                        data1.getUser().setTempLoc(temp);
                        return task;
                    } else return dbRef("sessions", 1).get().continueWith(subtask -> {
                        DataSnapshot session = subtask.getResult();
                        return null;
                    });
                }).continueWith(task -> {
                    task.getResult();   // To propagate possible error
                    return data1.getUser();
                });
    }
    @Override
    public Task<Void> logout() {
        return null;
    }

    @Override
    public UserProfile getUserProfile() {
        return null;
    }

    public Task<DataSnapshot> addFriend() {
        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        if( fireUser == null )
            return Tasks.forException(new Exception("Not logged"));
        //       user = new UserProfile();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("sessions/" + data1.getUser().getId());
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("friends")) {
                    HashMap map = new HashMap();
                    map.put(data1.getUser().getNewFriend(), data1.getUser().getNewFriend());
                    dbRef.child("sessions").child(data1.getUser().getId()).child("friends").updateChildren(map);
                } else {
                    HashMap map = new HashMap();
                    map.put("friends", "friends");
                    dbRef.child("sessions").child(data1.getUser().getId()).updateChildren(map);

                    HashMap map2 = new HashMap();
                    map2.put(data1.getUser().getNewFriend(), data1.getUser().getNewFriend());
                    dbRef.child("sessions").child(data1.getUser().getId()).child("friends").updateChildren(map);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //some code
            }
        });
        return null;
    }


}
