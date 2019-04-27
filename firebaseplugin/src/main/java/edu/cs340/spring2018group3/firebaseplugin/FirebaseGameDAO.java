package edu.cs340.spring2018group3.firebaseplugin;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.xml.crypto.Data;

import edu.cs340.spring2018group3.firebaseplugin.callbacks.DTOCallback;
import edu.cs340.spring2018group3.firebaseplugin.firebasedtos.FirebaseCommand;
import edu.cs340.spring2018group3.firebaseplugin.firebasedtos.FirebaseServerGame;
import edu.cs340.spring2018group3.firebaseplugin.firebasedtos.FirebaseUtil;
import edu.cs340.spring2018group3.firebaseplugin.threads.ClearThread;
import edu.cs340.spring2018group3.firebaseplugin.threads.GetGameCmdsThread;
import edu.cs340.spring2018group3.firebaseplugin.threads.GetGameSnapThread;
import edu.cs340.spring2018group3.firebaseplugin.threads.GetUserCmdsThread;
import edu.cs340.spring2018group3.firebaseplugin.threads.GetGameListThread;
import edu.cs340.spring2018group3.pluginsdk.dtos.CommandDTO;
import edu.cs340.spring2018group3.pluginsdk.dtos.ServerGameDTO;
import edu.cs340.spring2018group3.pluginsdk.interfaces.IGameDAO;

/**
 * @author Samuel Nuttall
 */
public class FirebaseGameDAO implements IGameDAO {
    
    private List<String> gameList = new ArrayList<>();
    private DatabaseReference gameListRef;
 
    
    private List<FirebaseCommand> cmdList = new ArrayList<>();
    private FirebaseServerGame gameDTO;
    
    public FirebaseGameDAO(DatabaseReference rootRef) {
        gameListRef = rootRef.child("gameList");
      
    }
    
    @Override
    public void updateGameSnapshot(ServerGameDTO game) {
        String gameName = game.getGameInfo().getGameName();
        DatabaseReference gameRef = gameListRef.child(gameName);
        DatabaseReference snapRef = gameRef.child("snapshot");
        FirebaseServerGame fireGame = new FirebaseServerGame(game);
        try {
            System.out.println("Adding game command to game: " + gameName);
         
            snapRef.setValueAsync(fireGame);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    @Override
    public List<String> getGameList() {
        gameList.clear();
        
        final CountDownLatch latch = new CountDownLatch(1);
        DTOCallback callback = new DTOCallback() {
            @Override
            public void onCallback(List<Object> DTOList) {
                gameList = (List<String>) (Object) DTOList;
            }
        };
        Thread thread = new GetGameListThread(callback, gameListRef, latch, "gameListThread");
        try {
            
            thread.start();
            latch.await();
        }
        catch (Exception e) {
            System.out.println(thread.getName() + " was interrupted");
        }
        return gameList;
    }
    
    @Override
    public ServerGameDTO getGameSnapshot(String gameName) {
        gameDTO = null;
        DatabaseReference gameNameRef = gameListRef.child(gameName);
        DatabaseReference snapshotRef = gameNameRef.child("snapshot");
        final CountDownLatch latch = new CountDownLatch(1);
        DTOCallback callback = new DTOCallback() {
            @Override
            public void onCallback(List<Object> DTOList) {
                List<FirebaseServerGame> gameDTOList = (List<FirebaseServerGame>) (Object) DTOList;
                gameDTO = gameDTOList.get(0);
            }
        };
        Thread thread = new GetGameSnapThread(callback, snapshotRef, latch, "gameSnapThread");
        try {
        
            thread.start();
            latch.await();
        }
        catch (Exception e) {
            System.out.println(thread.getName() + " was interrupted");
        }
        if (gameDTO == null){
            return null;
        }
        return gameDTO.convertToDTO();
    }
    
    @Override
    public void addCommand(String gameName, CommandDTO cmd) {
        DatabaseReference gameNameRef = gameListRef.child(gameName);
        DatabaseReference cmdListRef = gameNameRef.child("commandList");
        FirebaseCommand fireCmd = new FirebaseCommand(cmd);
        try {
            System.out.println("Adding game command to game: " + gameName);
            cmdListRef.push().setValueAsync(fireCmd);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public List<CommandDTO> getCommands(String gameName) {
        DatabaseReference gameRef = gameListRef.child(gameName);
        DatabaseReference cmdsRef = gameRef.child("commandList");
        cmdList.clear();
        final CountDownLatch latch = new CountDownLatch(1);
        DTOCallback callback = new DTOCallback() {
            @Override
            public void onCallback(List<Object> fireCmdList) {
                cmdList = (List<FirebaseCommand>) (Object) fireCmdList;
            }
        };
        Thread thread = new GetGameCmdsThread(callback, cmdsRef, latch, "getGamdCmsThread");
        try {
        
            thread.start();
            latch.await();
        }
        catch (Exception e) {
            System.out.println(thread.getName() + " was interrupted");
        }
        return FirebaseUtil.convertFirebaseCmdList(cmdList);
    }
    
  
    
    @Override
    public void clearCommands(String gameName) {
        DatabaseReference gameNameRef = gameListRef.child(gameName);
        final CountDownLatch latch = new CountDownLatch(1);
        DTOCallback callback = new DTOCallback() {
            @Override
            public void onCallback(List<Object> DTOList) {
            }
        };
        
        Thread thread = new ClearThread(callback, gameNameRef, latch, "gameDaoThread");
        try {
            
            thread.start();
            latch.await();
        }
        catch (Exception e) {
            System.out.println(thread.getName() + " was interrupted");
        }
        
    }
    
    @Override
    public void clearAll() {
        final CountDownLatch latch = new CountDownLatch(1);
        DTOCallback callback = new DTOCallback() {
            @Override
            public void onCallback(List<Object> DTOList) {
            }
        };
        
        Thread thread = new ClearThread(callback, gameListRef, latch, "gameDaoThread");
        try {
            
            thread.start();
            latch.await();
        }
        catch (Exception e) {
            System.out.println(thread.getName() + " was interrupted");
        }
    }
}
