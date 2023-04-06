package com.example.Scrabble2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.LocalGame;
import com.example.GameFramework.gameConfiguration.GameConfig;
import com.example.GameFramework.gameConfiguration.GamePlayerType;
import com.example.GameFramework.infoMessage.GameState;
import com.example.GameFramework.players.GamePlayer;
import com.example.GameFramework.utilities.Logger;
import com.example.GameFramework.utilities.Saving;
import com.example.Scrabble2.infoMessage.SCBState;
import com.example.Scrabble2.players.SCBComputerPlayer1;
import com.example.Scrabble2.players.SCBComputerPlayer2;
import com.example.Scrabble2.players.SCBHumanPlayer1;
import com.example.gametestb.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;

public class ScrabbleMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 5213;
    private final static String TAG = "MainActivity";
    @Override
    public GameConfig createDefaultConfig(){
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // human player
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new SCBHumanPlayer1(name, R.layout.scb_humanplayer1);
            }
        });

        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new SCBComputerPlayer1(name);
            }
        });

        // smarter computer player
        playerTypes.add(new GamePlayerType("Computer Player (smart)") {
            public GamePlayer createPlayer(String name) {
                return new SCBComputerPlayer2(name);
            }
        });

        // Create a game configuration class for Scrabble 2
        GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Scrabble 2", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0); // human
        defaultConfig.addPlayer("Computer", 1); // dumb computer player

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 0); // red-on-yellow GUI

        //done!
        return defaultConfig;

    }//createDefaultConfig

    /**
     * createLocalGame
     *
     * Creates a new game that runs on the server tablet,
     * @param gameState
     * 				the gameState for this game or null for a new game
     *
     * @return a new, game-specific instance of a sub-class of the LocalGame
     *         class.
     */
    @Override
    public LocalGame createLocalGame(GameState gameState){
        if(gameState == null) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(getAssets().open("dictionary.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new ScrabbleLocalGame(reader);
        }

        return new ScrabbleLocalGame((SCBState) gameState);
    }

    /**
     * saveGame, adds this games prepend to the filename
     *
     * @param gameName
     * 				Desired save name
     * @return String representation of the save
     */
    @Override
    public GameState saveGame(String gameName){
        return super.saveGame(getGameString(gameName));
    }

    /**
     * loadGame, adds this games prepend to the desire file to open and creates the game specific state
     * @param gameName
     * 				The file to open
     * @return The loaded GameState
     */
    @Override
    public GameState loadGame(String gameName){
        String appName = getGameString(gameName);
        super.loadGame(appName);
        Logger.log(TAG, "Loading: " + gameName);
        return (GameState) new SCBState((SCBState) Saving.readFromFile(appName, this.getApplicationContext()));
    }
}