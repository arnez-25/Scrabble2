package com.example.Scrabble2;

import com.example.GameFramework.LocalGame;
import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.Scrabble2.infoMessage.SCBState;

import java.io.BufferedReader;

public class ScrabbleLocalGame extends LocalGame {

    /**
     * Constructor for the ScrabbleLocalGame.
     */
    public ScrabbleLocalGame(BufferedReader reader) {

        // perform superclass initialization
        super();

        // create a new, unfilled-in TTTState object
        super.state = new SCBState(reader);
    }

    /**
     * Constructor for the ScrabbleLocalGame with loaded SCBState
     * @param scrabbleState
     */
    public ScrabbleLocalGame(SCBState scrabbleState){
        super();
        super.state = new SCBState(scrabbleState);
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }
}
