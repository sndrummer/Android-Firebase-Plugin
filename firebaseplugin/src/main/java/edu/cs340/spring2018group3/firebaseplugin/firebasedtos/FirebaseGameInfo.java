package edu.cs340.spring2018group3.firebaseplugin.firebasedtos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs340.spring2018group3.pluginsdk.dtos.GameInfoDTO;

/**
 * @author Samuel Nuttall
 */
public class FirebaseGameInfo {
    
    private String gameName;
    private List<String> players;
    private boolean lastRound;
    private boolean hasStarted;
    private int turnsRemaining;
    
    public FirebaseGameInfo(GameInfoDTO gameInfoDTO){
        this.gameName = gameInfoDTO.getGameName();
        players = new ArrayList<>(Arrays.asList(gameInfoDTO.getPlayers()));
        lastRound = gameInfoDTO.isLastRound();
        hasStarted = gameInfoDTO.isHasStarted();
        turnsRemaining = gameInfoDTO.getTurnsRemaining();
    }
    
    public FirebaseGameInfo(){
    
    }
    
    
    public GameInfoDTO convertToDTO(){
        GameInfoDTO gameInfoDTO = new GameInfoDTO();
        gameInfoDTO.setGameName(gameName);
        gameInfoDTO.setPlayers(players.toArray(new String[players.size()]));
        gameInfoDTO.setLastRound(lastRound);
        gameInfoDTO.setHasStarted(hasStarted);
        gameInfoDTO.setTurnsRemaining(turnsRemaining);
        return gameInfoDTO;
    }
    
    public String getGameName() {
        return gameName;
    }
    
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    
    public List<String> getPlayers() {
        return players;
    }
    
    public void setPlayers(List<String> players) {
        this.players = players;
    }
    
    public boolean isLastRound() {
        return lastRound;
    }
    
    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }
    
    public boolean isHasStarted() {
        return hasStarted;
    }
    
    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }
    
    public int getTurnsRemaining() {
        return turnsRemaining;
    }
    
    public void setTurnsRemaining(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }
}
