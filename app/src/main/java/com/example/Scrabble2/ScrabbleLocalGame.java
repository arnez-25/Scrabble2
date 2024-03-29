package com.example.Scrabble2;

import android.widget.Toast;

import com.example.GameFramework.LocalGame;
import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.GameFramework.utilities.Logger;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleComputerAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleHintAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlaceAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlayAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleResetAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleSkipAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleSwapAction;
import com.example.Scrabble2.infoMessage.SCBState;

import java.io.BufferedReader;

/**
 * @author Riley Cameron
 * @author Alexx Blake
 * @author Nick Tabra
 * @author Jacob Arnez
 * @author David Leon
 *
 * @version 4/28/2023
 */
public class ScrabbleLocalGame extends LocalGame {

    //Instance Variables
    private SCBState gameState;

    private static final String TAG = "ScrabbleLocalGame";

    /**
     * Constructor for the ScrabbleLocalGame.
     */
    public ScrabbleLocalGame(BufferedReader reader) {

        // perform superclass initialization
        super();

        // create a new, unfilled-in SCBState object
        gameState = new SCBState(reader);
        super.state = this.gameState;
    }//ScrabbleLocalGame

    /**
     * Constructor for the ScrabbleLocalGame with loaded SCBState
     * @param scrabbleState - loaded game-state used to create the local game
     */
    public ScrabbleLocalGame(SCBState scrabbleState){
        super();
        super.state = new SCBState(scrabbleState);
    }//ScrabbleLocalGame

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // make a copy of the state, and send it to the player
        p.sendInfo(new SCBState(gameState));
    }//sendUpdatedStateTo

    /**
     * This method checks if the player is able to move
     *
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return returns whether the player can move or not.
     */
    @Override
    protected boolean canMove(int playerIdx) {
        //Checks to see if the index of the player is able to move.
        if (playerIdx == gameState.playerToMove) {
            return true;
        } else {
            return false;
        }
    }//canMove

    /**
     *This method is a checker to ensure that if the game is no longer running, that it will activate Game Over.
     *
     * @return if the game is over or not.
     */
    @Override
    protected String checkIfGameOver() {
        String message = "Game Over! Player";
        if (!gameState.gameRunning) {
            if (gameState.p1Score > gameState.p2Score) {
                message = message.concat(" One is the winner!  ");
            } else {
                message = message.concat(" Two is the winner!  ");
            }
            return message;
        } else {
            return null;
        }
    }//checkIfGameOver

    /**
     * The method checks whether you are able to make a move or not.
     *
     * @param action
     * 			The move that the player has sent to the game
     * @return true if the move is possible or not.
     */
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
        } else if (action instanceof ScrabbleComputerAction) {
            ScrabbleComputerAction compAction = (ScrabbleComputerAction) action;
            for (int i = 0; i < compAction.getTilesToPlace().size(); i++) {
                gameState.placeTile(action.getPlayer().getPlayerNum(), compAction.getTilesToPlace().get(i), compAction.getTilePoints().get(i).x, compAction.getTilePoints().get(i).y);
            }
            if(!gameState.playWord(action.getPlayer().getPlayerNum())) {
                gameState.skipper(action.getPlayer().getPlayerNum());
                Logger.log(TAG, "Computer Player tried to play a word that was incorrect");
            }
            return true;
        }
        return false;
    }//makeMove
}
