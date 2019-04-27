package edu.cs340.spring2018group3.firebaseplugin.threads;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.CountDownLatch;

import edu.cs340.spring2018group3.firebaseplugin.callbacks.DTOCallback;

/**
 * @author Samuel Nuttall
 */
public class ClearThread extends FirebaseAwaitThread{
    
    private DatabaseReference ref;
    
    public ClearThread(DTOCallback callback, DatabaseReference ref, CountDownLatch latch, String name) {
        super(callback, ref, latch, name);
        this.ref = ref;
    }
    
    @Override
    void readData() {
        ref.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                System.out.println("Data cleared -- ");
                callback.onCallback(null);
                interrupt();
            }
        });
    }
    
   
}
