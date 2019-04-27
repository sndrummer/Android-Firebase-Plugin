package edu.cs340.spring2018group3.firebaseplugin.threads;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.cs340.spring2018group3.firebaseplugin.callbacks.DTOCallback;
import edu.cs340.spring2018group3.firebaseplugin.firebasedtos.FirebaseCommand;

/**
 * @author Samuel Nuttall
 */
public class GetGameCmdsThread extends FirebaseAwaitThread{
    
    private DatabaseReference cmdsRef;
  
    public GetGameCmdsThread(DTOCallback callback, DatabaseReference ref, CountDownLatch latch,
                             String name) {
        super(callback, ref, latch, name);
        this.cmdsRef = ref;
    }
    
    @Override
    void readData() {
        cmdsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                
                List<Object> fireCmds = new ArrayList<>();
                for (DataSnapshot cmdSnapshot : snapshot.getChildren()) {
                    FirebaseCommand user = cmdSnapshot.getValue(FirebaseCommand.class);
                    fireCmds.add(user);
                }
                callback.onCallback(fireCmds);
                interrupt();
            }
            
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Firebase Database Error: " + error.getMessage());
            }
        });
        
        
    }
    
}
