package com.example.Scrabble2.infoMessage;

import java.io.Serializable;

import com.example.GameFramework.infoMessage.GameState;

/**
 * Contains the state of a Scrabble game.  Sent by the game when
 * a player wants to enquire about the state of the game.  (E.g., to display
 * it, or to help figure out its next move.)
 *
 * @author Jacob Arnez
 * @author
 * @version April 2023
 */
public class SCBState extends GameState implements Serializable{

    //Tag for logging
    private static final String TAG = "SCBState";
    private static final long serialVersionUID = 7552321013488624386L;

    ///////////////////////////////////////////////////
    // ************** instance variables ************
    ///////////////////////////////////////////////////

    //TODO: Add instance variables for the game

    private int playerToMove;

    /**
     * Constructor for objects of class SCBState
     */
    public SCBState(){
        //TODO: Add code for Constructor initialize the board and player hand


        //make it player 0's move
        playerToMove = 0;
    }

    /**
     * Copy constructor for class SCBState
     *
     * @param original
     * 		the SCBState object that we want to clong
     */
    public SCBState(SCBState original){
        //TODO: Add code for copy construcor copy the board and player hands

    }

    /**
     * Tells whose move it is.
     *
     * @return the index (0 or 1) of the player whose move it is.
     */
    public int getWhoseMove() {
        return playerToMove;
    }

    /**
     * set whose move it is
     * @param id
     * 		the player we want to set as to whose move it is
     */
    public void setWhoseMove(int id) {
        playerToMove = id;
    }

    //TODO: Add code for the Scrabble State

}
