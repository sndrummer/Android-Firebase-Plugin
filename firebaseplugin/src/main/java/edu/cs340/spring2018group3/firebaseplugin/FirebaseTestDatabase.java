package edu.cs340.spring2018group3.firebaseplugin;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.cs340.spring2018group3.firebaseplugin.firebasedtos.FakeGameCreator;
import edu.cs340.spring2018group3.pluginsdk.dtos.CommandDTO;
import edu.cs340.spring2018group3.pluginsdk.dtos.ServerGameDTO;
import edu.cs340.spring2018group3.pluginsdk.dtos.UserDTO;
import edu.cs340.spring2018group3.pluginsdk.interfaces.IClientCmdDAO;
import edu.cs340.spring2018group3.pluginsdk.interfaces.IGameDAO;
import edu.cs340.spring2018group3.pluginsdk.interfaces.IUserDAO;

/**
 * @author Samuel Nuttall
 */
public class FirebaseTestDatabase {
    
    private static final String DATABASE_URL = "NA";
    
    private static DatabaseReference rootRef;
    private static DatabaseReference ttrGame;
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Hello, FireBase!");
        
        // Initialize Firebase
        
        try {
            FileInputStream serviceAccount = new FileInputStream(
                    "NO LONGER ACTIVE");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
        }
        catch (IOException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());
            
        }
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        
        rootRef = FirebaseDatabase.getInstance().getReference();
        
        
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Database changed");
            }
            
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Error updating");
            }
        });

        testUserDAO();
        wait(3);

    }
    
    private static void testGetGameSnap(String gameName){
        IGameDAO gameDAO = new FirebaseGameDAO(rootRef);
       ServerGameDTO gameDTO = gameDAO.getGameSnapshot(gameName);
    }
    
    private static void testGameSnap(){
        IGameDAO gameDAO = new FirebaseGameDAO(rootRef);
        FakeGameCreator gameCreator = new FakeGameCreator();
        gameDAO.updateGameSnapshot(gameCreator.createGame());
    }
    
    private static void testGetGameCmds(String gameName){
        IGameDAO gameDAO = new FirebaseGameDAO(rootRef);
       List<CommandDTO> cmds = gameDAO.getCommands(gameName);
       printCmds(cmds);
    }
    
    
    
    private static void testGetGameList(){
        IGameDAO gameDAO = new FirebaseGameDAO(rootRef);
        List<String> gameList = gameDAO.getGameList();

        for (String game : gameList) {
            System.out.println("GAME: " + game);
        }
        
    }
    
    private static void testClientCmdDao(){
        IClientCmdDAO cmdDAO = new FirebaseClientCmdDAO(rootRef);
        cmdDAO.clearAll();
        cmdDAO.addCommands("sam", createCmds());
        cmdDAO.addCommands("bill", createCmds());
        cmdDAO.addCommands("Jill", createCmds());
        
        List<CommandDTO> cmds = cmdDAO.getUserCommands("sam");
        printCmds(cmds);
        cmdDAO.clearUserCommands("sam");
        cmds = cmdDAO.getUserCommands("sam");
        printCmds(cmds);

    }
    
    private static void testGameAddCmd(String gameName, String className, String methodName){
        FirebaseGameDAO gameDAO = new FirebaseGameDAO(rootRef);
    
        CommandDTO cmd = new CommandDTO();
        cmd.setClassName(className);
        cmd.setMethodName(methodName);
        cmd.setmParamValues(new String[]{"1", "2", "3"});
        cmd.setParamTypes(new String[]{"4","5", "6"});
        
        
        gameDAO.addCommand(gameName, cmd);
    }
    
    private static void clearTestGameCmd(String gameName){
        FirebaseGameDAO gameDAO = new FirebaseGameDAO(rootRef);
        
        gameDAO.clearCommands(gameName);
    }
    
    private static void clearAllGames() {
        FirebaseGameDAO gameDAO = new FirebaseGameDAO(rootRef);
    
        gameDAO.clearAll();
    }
    
    private static List<CommandDTO> createCmds(){
        List<CommandDTO> cmds = new ArrayList<>();
        CommandDTO cmd = new CommandDTO();
        cmd.setClassName("class");
        cmd.setMethodName("myMethod");
        cmd.setmParamValues(new String[]{"Chicken", "Nuggets", "YUM"});
        cmd.setParamTypes(new String[]{"String","Food", "Cheese"});
        cmds.add(cmd);
    
        CommandDTO cmd2 = new CommandDTO();
        cmd2.setClassName("class2");
        cmd2.setMethodName("myMethod2");
        cmd2.setmParamValues(new String[]{"Bacon", "LOL", "BAM"});
        cmd2.setParamTypes(new String[]{"Integer","Map", "HHH"});
        cmds.add(cmd2);
    
    
    
        CommandDTO cmd3 = new CommandDTO();
        cmd3.setClassName("class3");
        cmd3.setMethodName("myMethod3");
        cmd3.setmParamValues(new String[]{"James", "Cameron", "LOL"});
        cmd3.setParamTypes(new String[]{"int","String", "Map"});
        cmds.add(cmd3);
    
        CommandDTO cmd4 = new CommandDTO();
        cmd4.setClassName("class4");
        cmd4.setMethodName("myMethod4");
        cmd4.setmParamValues(new String[]{"Han", "Solo", "HHHH"});
        cmd4.setParamTypes(new String[]{"1","2", "3"});
        cmds.add(cmd4);
        
        
        return cmds;
    }
    
    private static void testUserDAO(){
        IUserDAO userDAO = new FirebaseUserDAO(rootRef);
        userDAO.setUsers(createTestUsers());
        List<UserDTO> users = userDAO.getUsers();
        printUsers(users);

    }
    
    private static void wait(int sec) {
        try {
            Thread.sleep(sec * 1000);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    private static void printUsers(List<UserDTO> users) {
        System.out.println("USERS RETRIEVED!!");
        for (UserDTO user : users) {
            System.out.println(user.toString());
        }
    }
    
    private static void printCmds(List<CommandDTO> cmds){
        System.out.println("COMMANDS RETRIEVED!!");
        for (CommandDTO cmd : cmds) {
            System.out.println(cmd.toString());
        }
    }
    
    private static List<UserDTO> createTestUsers() {
        List<UserDTO> userList = new ArrayList<>();
        userList.add(new UserDTO("sam", "sam", "sam"));
        userList.add(new UserDTO("bill", "bill", "bill"));
        userList.add(new UserDTO("james", "james", "james"));
        userList.add(new UserDTO("cameron", "Cameron", "Cameron"));
        
        return userList;
    }
    
    private static void testAddUser2(UserDTO user) {
    
    
    }
    
    private static void testAddUser(UserDTO user) {
        
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail("user@example.com")
                    .setEmailVerified(false)
                    .setPassword("secretPassword")
                    .setPhoneNumber("+11234567890")
                    .setDisplayName("John Doe")
                    .setPhotoUrl("http://www.example.com/12345678/photo.png")
                    .setDisabled(false);
            
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            
        }
        catch (Exception ex) {
            System.out.println("You failed, user not created");
            System.out.println(ex.getMessage());
        }
        
        // Shared Database reference
        rootRef = FirebaseDatabase.getInstance().getReference();
    }
    
}
