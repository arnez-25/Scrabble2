package com.example.Scrabble2.players;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;

/**
 * This will be our smart AI. It Should be able to compete with the player instead of just skipping turn
 *
 * @author Jacob Arnez
 * @version May 2023
 */
public class SCBComputerPlayer2 extends GameComputerPlayer{

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public SCBComputerPlayer2(String name){
        super(name);

    }


    /**
     * Called when the player receives a game-state (or other info) from the game
     * This is where the AI will be written
     *
     * @param info
     * 			the object representing the information from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {

    }
}
