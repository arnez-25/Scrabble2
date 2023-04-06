package com.example.Scrabble2;

import com.example.GameFramework.LocalGame;
import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleHintAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlaceAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlayAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleResetAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleSkipAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleSwapAction;
import com.example.Scrabble2.infoMessage.SCBState;

import java.io.BufferedReader;

public class ScrabbleLocalGame extends LocalGame {

    private SCBState gameState;

    /**
     * Constructor for the ScrabbleLocalGame.
     */
    public ScrabbleLocalGame(BufferedReader reader) {

        // perform superclass initialization
        super();

        // create a new, unfilled-in SCBState object
        gameState = new SCBState(reader);
        super.state = this.gameState;
    }

    /**
     * Constructor for the ScrabbleLocalGame with loaded SCBState
     * @param scrabbleState - loaded game-state used to create the local game
     */
    public ScrabbleLocalGame(SCBState scrabbleState){
        super();
        super.state = new SCBState(scrabbleState);
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // make a copy of the state, and send it to the player
        p.sendInfo(new SCBState(gameState));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        if (playerIdx == gameState.playerToMove) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected String checkIfGameOver() {
        if (!gameState.gameRunning) {
            return "Game Over";
        } else {
            return null;
        }
    }

    @Override
    protected boolean makeMove(GameAction action) {
        if (action instanceof ScrabbleHintAction) {
            gameState.hinter(action.getPlayer().getPlayerNum());
            return true;
        } else if (action instanceof ScrabbleSkipAction) {
            gameState.skipper(action.getPlayer().getPlayerNum());
            return true;
        } else if (action instanceof ScrabbleSwapAction) {
            gameState.swapper(action.getPlayer().getPlayerNum(), ((ScrabbleSwapAction) action).getTile());
            return true;
        } else if (action instanceof ScrabblePlaceAction) {
            gameState.placeTile(action.getPlayer().getPlayerNum(), ((ScrabblePlaceAction) action).getTile(), ((ScrabblePlaceAction) action).getRow(), ((ScrabblePlaceAction) action).getCol());
            return true;
        } else if (action instanceof ScrabblePlayAction) {
            gameState.playWord(action.getPlayer().getPlayerNum());
            return true;
        } else if (action instanceof ScrabbleResetAction) {
            gameState.resetHand(action.getPlayer().getPlayerNum());
            return true;
        }

        return false;
    }
}
