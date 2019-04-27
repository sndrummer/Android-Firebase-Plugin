package edu.cs340.spring2018group3.firebaseplugin;

import com.google.firebase.database.ChildEventListener;
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
import edu.cs340.spring2018group3.firebaseplugin.threads.ClearThread;
import edu.cs340.spring2018group3.firebaseplugin.threads.GetUsersThread;
import edu.cs340.spring2018group3.pluginsdk.dtos.UserDTO;
import edu.cs340.spring2018group3.pluginsdk.interfaces.IUserDAO;

/**
 * @author Samuel Nuttall
 */
public class FirebaseUserDAO implements IUserDAO {
    
    private DatabaseReference rootRef;
    private DatabaseReference usersRef;
    private List<UserDTO> users = new ArrayList<>();
    
    public FirebaseUserDAO(DatabaseReference rootRef) {
        this.rootRef = rootRef;
        usersRef = rootRef.child("users");
    }
    
    @Override
    public List<UserDTO> getUsers() {
        
        users.clear();
        final CountDownLatch latch = new CountDownLatch(1);
        DTOCallback callback = new DTOCallback() {
            @Override
            public void onCallback(List<Object> DTOList) {
                users = (List<UserDTO>) (Object) DTOList;
            }
        };
        Thread thread = new GetUsersThread(callback, usersRef, latch, "getUsersThread");
        try {
            
            thread.start();
            latch.await();
        }
        catch (Exception e) {
            System.out.println(thread.getName() + " was interrupted");
        }
        return users;
    }
    
    @Override
    public void setUsers(List<UserDTO> users) {
        this.users = users;
        
        try {
            usersRef = rootRef.child("users");
            
            Map<String, UserDTO> userMap = new HashMap<>();
            for (UserDTO user : users) {
                userMap.put(user.getUsername(), user);
            }
            usersRef.setValueAsync(userMap);
            
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    @Override
    public void clearAll() {
        users.clear();
        final CountDownLatch latch = new CountDownLatch(1);
        DTOCallback callback = new DTOCallback() {
            @Override
            public void onCallback(List<Object> DTOList) {
               // users = (List<UserDTO>) (Object) DTOList;
            }
        };
    
        Thread thread = new ClearThread(callback, usersRef, latch, "clearThread");
        try {
        
            thread.start();
            latch.await();
        }
        catch (Exception e) {
            System.out.println(thread.getName() + " was interrupted");
        }
    }
}
