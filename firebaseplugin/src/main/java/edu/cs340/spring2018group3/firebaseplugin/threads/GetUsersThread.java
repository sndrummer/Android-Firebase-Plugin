package edu.cs340.spring2018group3.firebaseplugin.threads;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.cs340.spring2018group3.firebaseplugin.callbacks.DTOCallback;
import edu.cs340.spring2018group3.pluginsdk.dtos.UserDTO;

/**
 * @author Samuel Nuttall
 */
public class GetUsersThread extends FirebaseAwaitThread {
    
    private DatabaseReference usersRef;
    
    public GetUsersThread(DTOCallback callback, DatabaseReference ref, CountDownLatch latch, String name) {
        super(callback, ref, latch, name);
        this.usersRef = ref;
    }
    
    @Override
    void readData() {
        usersRef.orderByChild("username");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Object> users = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    UserDTO user = userSnapshot.getValue(UserDTO.class);
                    users.add(user);
                }
                callback.onCallback(users);
                interrupt();
            }
            
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Firebase Database Error: " + error.getMessage());
            }
        });
    }
}
