package model.backend;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public abstract class FirebaseHelper{
    private DatabaseReference db;

    protected DatabaseReference dbRef(Object... nodes) {
        if( db == null )
            db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference child = db;
        for( Object node : nodes )
            child = child.child(node.toString());
        return child;
    }


    protected static class JumpFutureException extends Exception {
    }
}
