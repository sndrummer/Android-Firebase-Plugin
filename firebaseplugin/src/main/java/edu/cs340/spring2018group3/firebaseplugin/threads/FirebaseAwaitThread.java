package edu.cs340.spring2018group3.firebaseplugin.threads;

import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.cs340.spring2018group3.firebaseplugin.callbacks.DTOCallback;


/**
 * @author Samuel Nuttall
 */
public abstract class FirebaseAwaitThread extends Thread {
    private CountDownLatch latch;
    private DatabaseReference ref;
    protected DTOCallback callback;
    
    abstract void readData();
    
    public FirebaseAwaitThread(DTOCallback callback, DatabaseReference ref, CountDownLatch latch,
                               String name) {
        super(name);
        this.callback = callback;
        this.latch = latch;
        this.ref = ref;
    }
    
    @Override
    public void run() {
        
        System.out.println(Thread.currentThread().getName()
                                   + " awaiting response");
        try {
            readData();
            Thread.sleep(7000);
            //Call the interrupt() method on your thread.
            latch.countDown();
            System.out.println(Thread.currentThread().getName()
                                       + " finished");
        }
        catch (InterruptedException ex){
            latch.countDown();
            System.out.println(Thread.currentThread().getName()
                                       + " finished");
        }
    }
}