package com.example.Scrabble2.players;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleSkipAction;
import com.example.Scrabble2.infoMessage.SCBState;

/**
 * This is the dumb AI for our game. Right now we just want it to skip its turn.
 * It is so dumb it can't even play the game!
 *
 * @author Jacob Arnez
 * @versio2 May 2023
 */
public class SCBComputerPlayer1 extends GameComputerPlayer{

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public SCBComputerPlayer1(String name) {
        super(name);
    }

    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * This is where the AI will be written
     *
     * @param info
     * 		the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (!(info instanceof SCBState)){
            return;
        }
        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        SCBState scb = (SCBState) info;

        if(scb.getWhoseMove() == 1){
            ScrabbleSkipAction skip = new ScrabbleSkipAction(this);
            game.sendAction(skip);
        }
    }
}
