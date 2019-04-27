package edu.cs340.spring2018group3.firebaseplugin.firebasedtos;

import java.util.ArrayList;
import java.util.HashMap;
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
public class FakeGameCreator {
    
    public ServerGameDTO createGame() {
        ServerGameDTO game = new ServerGameDTO();
        game.setGameInfo(createGameInfo());
        game.setDestinationCards(createDestCards());
        game.setVisibleTcs(createTrainCards());
        game.setTcDiscardPile(createTcDiscardPile());
        game.setTcDeck(createtcDeck());
        game.setPlayerDTOList(createPlayerList());
        game.setmPlayerPaths(createPlayerPaths());
        
        return game;
    }
    
    public GameInfoDTO createGameInfo() {
        GameInfoDTO gameInfo = new GameInfoDTO();
        gameInfo.setGameName("game1");
        gameInfo.setPlayers(createPlayers());
        gameInfo.setLastRound(false);
        gameInfo.setHasStarted(true);
        gameInfo.setTurnsRemaining(4);
        
        return gameInfo;
    }
    
    private Map<String, PathListDto> createPlayerPaths(){
        Map<String, PathListDto> playerPaths = new HashMap<>();
        PathListDto pathListDto = new PathListDto();
        playerPaths.put("Sam", new PathListDto());
        playerPaths.put("Bill", new PathListDto());
        playerPaths.put("Jim", new PathListDto());
        playerPaths.put("James", new PathListDto());
        playerPaths.put("Ben", new PathListDto());
        return playerPaths;
        
    }
    
    private String[] createPlayers() {
        return new String[]{"Sam", "Bill", "Jim", "Ben", "James"};
    }
    
    private Map<String, String> createDestCards() {
        Map<String, String> destCardMap = new HashMap<>();
        destCardMap.put("ha", "Sam");
        destCardMap.put("haae", "Sam");
        destCardMap.put("eva", "Sam");
        
        destCardMap.put("Chicagp", "Bill");
        destCardMap.put("ab", "Bill");
        destCardMap.put("cd", "Bill");
        
        destCardMap.put("ef", "Jim");
        destCardMap.put("gh", "Jim");
        destCardMap.put("hi", "Jim");
        
        destCardMap.put("jk", "Ben");
        destCardMap.put("lm", "Ben");
        destCardMap.put("no", "Ben");
    
        destCardMap.put("pq", "James");
        destCardMap.put("re", "James");
        destCardMap.put("st", "James");
        
        return destCardMap;
    }
    
    private ServerGameDTO.TrainCardColor[] createTrainCards() {
        ServerGameDTO.TrainCardColor[] visibleTcs = new ServerGameDTO.TrainCardColor[5];
        
        visibleTcs[0] = ServerGameDTO.TrainCardColor.BLACK;
        visibleTcs[1] = ServerGameDTO.TrainCardColor.BLUE;
        visibleTcs[2] = ServerGameDTO.TrainCardColor.ORANGE;
        visibleTcs[3] = ServerGameDTO.TrainCardColor.RAINBOW;
        visibleTcs[4] = ServerGameDTO.TrainCardColor.RED;
        
        return visibleTcs;
    }
    
    private TcPileDto createTcDiscardPile() {
        TcPileDto tcPileDto = new TcPileDto();
        tcPileDto.setGreen(3);
        tcPileDto.setBlack(2);
        tcPileDto.setLocomotives(1);
        tcPileDto.setRed(3);
        
        return tcPileDto;
    }
    
    private TcPileDto createtcDeck() {
        TcPileDto tcPileDto = new TcPileDto();
        tcPileDto.setGreen(20);
        tcPileDto.setBlack(20);
        tcPileDto.setLocomotives(10);
        tcPileDto.setRed(3);
        
        return tcPileDto;
    }
    
    private List<PlayerInfoDto> createPlayerList() {
        List<PlayerInfoDto> playerList = new ArrayList<>();
        playerList.add(createPlayer("Sam", "Green"));
        playerList.add(createPlayer("Bill", "Red"));
        playerList.add(createPlayer("Jim", "Purple"));
        playerList.add(createPlayer("Ben", "Black"));
        
        return playerList;
    }
    
    private PlayerInfoDto createPlayer(String username, String color) {
        PlayerInfoDto player = new PlayerInfoDto();
        player.setBlack(1);
        player.setBlue(1);
        player.setScore(25);
        player.setDcCount(3);
        player.setLocomotives(1);
        player.setUserName(username);
        player.setColor(color);
        
        return player;
    }
}
