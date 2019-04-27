package edu.cs340.spring2018group3.firebaseplugin;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;

import edu.cs340.spring2018group3.pluginsdk.interfaces.IClientCmdDAO;
import edu.cs340.spring2018group3.pluginsdk.interfaces.IGameDAO;
import edu.cs340.spring2018group3.pluginsdk.interfaces.IPersistenceProvider;
import edu.cs340.spring2018group3.pluginsdk.interfaces.IUserDAO;

/**
 * @author Samuel Nuttall
 * //https://www.youtube.com/watch?v=lpFDFK44pX8
 *
 * Adding Java Objects to Firebase DB
 * https://www.youtube.com/watch?v=ImNs-z872ck
 *
 * read data from Firebase DB
 * https://youtu.be/2duc77R4Hqw
 */
public class FirebasePlugin implements IPersistenceProvider{
    
    private static final String DATABASE_URL = "https://ticket-to-ride-plugin.firebaseio.com/";
    private static final String ACCOUNT_JSON = "TTR_KEY/ticket-to-ride-plugin-firebase-adminsdk-jksw4-a93634a090.json";
    private DatabaseReference rootRef;
   // private DatabaseReference child;
    
    public FirebasePlugin(){
        initDatabase();
    }
    
    private void initDatabase(){
        
        try {
            // Initialize the SDK
            FileInputStream serviceAccount = new FileInputStream(
                    ACCOUNT_JSON);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
        
        } catch (IOException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());
        
        }

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);  // --> for offline
        // Shared Database reference
        rootRef = FirebaseDatabase.getInstance().getReference();
        
    }
    
    @Override
    public void clearAll() {
        //TODO Test
        rootRef.removeValueAsync();
    }
    
    @Override
    public void startTransaction() {
    
    }
    @Override
    public void endTransaction(boolean commit) {
    
    }
    
    @Override
    public IUserDAO getUserDAO() {
        return new FirebaseUserDAO(rootRef);
    }
    
    @Override
    public IGameDAO getGameDAO() {
        return new FirebaseGameDAO(rootRef);
    }
    
    @Override
    public IClientCmdDAO getClientCmdDAO() {
        return new FirebaseClientCmdDAO(rootRef);
    }
}