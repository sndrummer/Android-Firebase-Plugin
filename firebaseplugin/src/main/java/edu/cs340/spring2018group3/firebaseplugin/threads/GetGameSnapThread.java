package edu.cs340.spring2018group3.firebaseplugin.threads;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.cs340.spring2018group3.firebaseplugin.callbacks.DTOCallback;
import edu.cs340.spring2018group3.firebaseplugin.firebasedtos.FirebaseServerGame;
import edu.cs340.spring2018group3.pluginsdk.dtos.ServerGameDTO;

/**
 * @author Samuel Nuttall
 */
public class GetGameSnapThread  extends FirebaseAwaitThread{
    private DatabaseReference snapshotRef;
    
    public GetGameSnapThread(DTOCallback callback, com.google.firebase.database.DatabaseReference ref, CountDownLatch latch, String name) {
        super(callback, ref, latch, name);
        this.snapshotRef = ref;
    }
    
    @Override
    void readData() {
        snapshotRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Object> snapList = new ArrayList<>();
                try {
                   FirebaseServerGame game = (dataSnapshot.getValue(FirebaseServerGame.class));
                   snapList.add(game);
                }
                catch (Exception ex){
                    System.out.println("READ DATA ERROR");
                    System.out.println(ex.getMessage());
                }
                
                callback.onCallback(snapList);
                interrupt();
            }
            
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Firebase Database Error: " + error.getMessage());
            }
        });
    }
}
