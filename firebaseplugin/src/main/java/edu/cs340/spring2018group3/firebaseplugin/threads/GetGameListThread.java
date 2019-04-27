package edu.cs340.spring2018group3.firebaseplugin.threads;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import edu.cs340.spring2018group3.firebaseplugin.callbacks.DTOCallback;
import edu.cs340.spring2018group3.pluginsdk.dtos.UserDTO;

/**
 * @author Samuel Nuttall
 */
public class GetGameListThread extends FirebaseAwaitThread {
    
    private DatabaseReference gameListRef;
    
    public GetGameListThread(DTOCallback callback, DatabaseReference ref, CountDownLatch latch, String name) {
        super(callback, ref, latch, name);
        this.gameListRef = ref;
    }
    
    @Override
    void readData() {
        gameListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Object> games = new ArrayList<>();
                
                try {
                    Map<String,Object> gameName = (Map<String, Object>) dataSnapshot.getValue();
                    games.addAll(gameName.keySet());
                    
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
              
                callback.onCallback(games);
                interrupt();
            }
            
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Firebase Database Error: " + error.getMessage());
            }
        });
    }
}
