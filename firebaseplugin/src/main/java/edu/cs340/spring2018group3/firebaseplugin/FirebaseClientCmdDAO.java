package edu.cs340.spring2018group3.firebaseplugin;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.cs340.spring2018group3.firebaseplugin.callbacks.DTOCallback;
import edu.cs340.spring2018group3.firebaseplugin.firebasedtos.FirebaseCommand;
import edu.cs340.spring2018group3.firebaseplugin.firebasedtos.FirebaseUtil;
import edu.cs340.spring2018group3.firebaseplugin.threads.ClearThread;
import edu.cs340.spring2018group3.firebaseplugin.threads.GetUserCmdsThread;
import edu.cs340.spring2018group3.pluginsdk.dtos.CommandDTO;
import edu.cs340.spring2018group3.pluginsdk.interfaces.IClientCmdDAO;

/**
 * @author Samuel Nuttall
 */
public class FirebaseClientCmdDAO implements IClientCmdDAO {
    
    private DatabaseReference rootRef;
    private DatabaseReference cmdListRef;
    
    private List<FirebaseCommand> clientCmds = new ArrayList<>();
    
    public FirebaseClientCmdDAO(DatabaseReference rootRef) {
        this.rootRef = rootRef;
        cmdListRef = rootRef.child("clientCommandList");
    }
    
    @Override
    public void addCommands(String username, List<CommandDTO> cmds) {
        cmdListRef = rootRef.child("clientCommandList");
        DatabaseReference userCmdsRef = cmdListRef.child(username);
        try {
            for (CommandDTO cmd : cmds) {
                FirebaseCommand fireCmd = new FirebaseCommand(cmd);
                userCmdsRef.push().setValueAsync(fireCmd);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public List<CommandDTO> getUserCommands(String username) {
        clientCmds.clear();
        final CountDownLatch latch = new CountDownLatch(1);
        DTOCallback callback = new DTOCallback() {
            @Override
            public void onCallback(List<Object> fireCmdList) {
                clientCmds = (List<FirebaseCommand>) (Object) fireCmdList;
            }
        };
        Thread thread = new GetUserCmdsThread(callback, cmdListRef, latch, "usersCmdsThread", username);
        try {
            
            thread.start();
            latch.await();
        }
        catch (Exception e) {
            System.out.println(thread.getName() + " was interrupted");
        }
        return FirebaseUtil.convertFirebaseCmdList(clientCmds);
    }
    
   
    
    @Override
    public void clearAll() {
        clientCmds.clear();
        final CountDownLatch latch = new CountDownLatch(1);
        DTOCallback callback = new DTOCallback() {
            @Override
            public void onCallback(List<Object> DTOList) {
            }
        };
    
        Thread thread = new ClearThread(callback, cmdListRef, latch, "clearThread");
        try {
        
            thread.start();
            latch.await();
        }
        catch (Exception e) {
            System.out.println(thread.getName() + " was interrupted");
        }
    }
    
    @Override
    public void clearUserCommands(String username) {
        DatabaseReference usersCmdList = cmdListRef.child(username);
        clientCmds.clear();
        final CountDownLatch latch = new CountDownLatch(1);
        DTOCallback callback = new DTOCallback() {
            @Override
            public void onCallback(List<Object> DTOList) {
            }
        };
    
        Thread thread = new ClearThread(callback, usersCmdList, latch, "clearThread");
        try {
        
            thread.start();
            latch.await();
        }
        catch (Exception e) {
            System.out.println(thread.getName() + " was interrupted");
        }
    }
}
