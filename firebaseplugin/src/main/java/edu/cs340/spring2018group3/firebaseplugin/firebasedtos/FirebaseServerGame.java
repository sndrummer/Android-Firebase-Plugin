package edu.cs340.spring2018group3.firebaseplugin.firebasedtos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import edu.cs340.spring2018group3.pluginsdk.dtos.GameInfoDTO;
import edu.cs340.spring2018group3.pluginsdk.dtos.PathListDto;
import edu.cs340.spring2018group3.pluginsdk.dtos.PlayerInfoDto;
import edu.cs340.spring2018group3.pluginsdk.dtos.ServerGameDTO;
import edu.cs340.spring2018group3.pluginsdk.dtos.TcPileDto;

/**
 * @author Samuel Nuttall
 */
public class FirebaseServerGame {
    
    private FirebaseGameInfo gameInfo;
    private Map<String, String> destinationCards; // maps card ids to usernames
    private Map<String, String> claimedRoutes; // route ids to usernames
    private List<ServerGameDTO.TrainCardColor> visibleTcs;
    private TcPileDto tcDiscardPile;
    private TcPileDto tcDeck;
    private List<PlayerInfoDto> playerList;
    private Map<String, PathListDto> mPlayerPaths;
    
    public FirebaseServerGame(ServerGameDTO serverGameDTO){
        gameInfo = new FirebaseGameInfo(serverGameDTO.getGameInfo());
        destinationCards = serverGameDTO.getDestinationCards();
        claimedRoutes = serverGameDTO.getClaimedRoutes();
        visibleTcs = new ArrayList<>(Arrays.asList(serverGameDTO.getVisibleTcs()));
        tcDiscardPile = serverGameDTO.getTcDiscardPile();
        tcDeck = serverGameDTO.getTcDeck();
        playerList = serverGameDTO.getPlayerList();
        mPlayerPaths = serverGameDTO.getPlayerPaths();
    }
    
    public FirebaseServerGame(){
    
    }
    
    public ServerGameDTO convertToDTO(){
        ServerGameDTO serverGameDTO = new ServerGameDTO();
        
        serverGameDTO.setGameInfo(gameInfo.convertToDTO());
        serverGameDTO.setDestinationCards(destinationCards);
        serverGameDTO.setClaimedRoutes(claimedRoutes);
        serverGameDTO.setVisibleTcs(visibleTcs.toArray(
                new ServerGameDTO.TrainCardColor[visibleTcs.size()]));
        serverGameDTO.setTcDiscardPile(tcDiscardPile);
        serverGameDTO.setTcDeck(tcDeck);
        serverGameDTO.setPlayerDTOList(playerList);
        serverGameDTO.setPlayerPaths(mPlayerPaths);
        
        return serverGameDTO;
    }
    
    public FirebaseGameInfo getGameInfo() {
        return gameInfo;
    }
    
    public void setGameInfo(FirebaseGameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }
    
    public Map<String, String> getDestinationCards() {
        return destinationCards;
    }
    
    public void setDestinationCards(Map<String, String> destinationCards) {
        this.destinationCards = destinationCards;
    }
    
    public Map<String, String> getClaimedRoutes() {
        return claimedRoutes;
    }
    
    public void setClaimedRoutes(Map<String, String> claimedRoutes) {
        this.claimedRoutes = claimedRoutes;
    }
    
    public List<ServerGameDTO.TrainCardColor> getVisibleTcs() {
        return visibleTcs;
    }
    
    public void setVisibleTcs(List<ServerGameDTO.TrainCardColor> visibleTcs) {
        this.visibleTcs = visibleTcs;
    }
    
    public TcPileDto getTcDiscardPile() {
        return tcDiscardPile;
    }
    
    public void setTcDiscardPile(TcPileDto tcDiscardPile) {
        this.tcDiscardPile = tcDiscardPile;
    }
    
    public TcPileDto getTcDeck() {
        return tcDeck;
    }
    
    public void setTcDeck(TcPileDto tcDeck) {
        this.tcDeck = tcDeck;
    }
    
    public List<PlayerInfoDto> getPlayerList() {
        return playerList;
    }
    
    public void setPlayerList(List<PlayerInfoDto> playerList) {
        this.playerList = playerList;
    }
    
    public Map<String, PathListDto> getmPlayerPaths() {
        return mPlayerPaths;
    }
    
    public void setmPlayerPaths(Map<String, PathListDto> mPlayerPaths) {
        this.mPlayerPaths = mPlayerPaths;
    }
}
