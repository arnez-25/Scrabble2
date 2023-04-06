package com.example.Scrabble2.players;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlaceAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlayAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleSkipAction;
import com.example.Scrabble2.infoMessage.SCBState;
import com.example.Scrabble2.infoMessage.Tile;

import java.util.ArrayList;

/**
 * This is the dumb AI for our game. Right now we just want it to skip its turn.
 * It is so dumb it can't even play the game!
 *
 * @author Jacob Arnez
 * @version May 2023
 */
public class SCBComputerPlayer1 extends GameComputerPlayer{
    SCBState scb;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public SCBComputerPlayer1(String name) {
        super(name);
        scb = null;
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
        scb = (SCBState) info;

        if(scb.getWhoseMove() == playerNum){//TODO: case for AI having the first move
//            //0 IQ, ultra veggie AI
//            ScrabbleSkipAction skip = new ScrabbleSkipAction(this);
//            game.sendAction(skip);


            String wordDir = "";
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    if (scb.board[i][j].getLetter() != ' ') {
                        Tile toPlace = null;
                        if (scb.board[i+1][j].getLetter() == ' ' && scb.board[i-1][j].getLetter() == ' ') {
                            toPlace = find2LetterWord(scb.board[i][j], i+1, j, scb.player2Tiles);
                            if (toPlace != null) {
                                game.sendAction(new ScrabblePlaceAction(this, toPlace, i+1, j));
                                game.sendAction(new ScrabblePlayAction(this));
                                return;
                            }

                            toPlace = find2LetterWord(scb.board[i][j], i-1, j, scb.player2Tiles);
                            if (toPlace != null) {
                                game.sendAction(new ScrabblePlaceAction(this, toPlace, i-1, j));
                                game.sendAction(new ScrabblePlayAction(this));
                                return;
                            }
                        } else if (scb.board[i][j+1].getLetter() == ' ' && scb.board[i][j-1].getLetter() == ' ') {
                            toPlace = find2LetterWord(scb.board[i][j], i, j+1, scb.player2Tiles);
                            if (toPlace != null) {
                                game.sendAction(new ScrabblePlaceAction(this, toPlace, i, j+1));
                                game.sendAction(new ScrabblePlayAction(this));
                                return;
                            }

                            toPlace = find2LetterWord(scb.board[i][j], i, j-1, scb.player2Tiles);
                            if (toPlace != null) {
                                game.sendAction(new ScrabblePlaceAction(this, toPlace, i, j-1));
                                game.sendAction(new ScrabblePlayAction(this));
                                return;
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * A method to help find a letter word throughout the table/playing area.
     *
     * @param playOff
     * @param row
     * @param col
     * @param hand
     * @return Tile - tile to play
     */
    public Tile find2LetterWord(Tile playOff, int row, int col, ArrayList<Tile> hand) {
        int rootRow = scb.getTileRow(playOff);
        int rootCol = scb.getTileCol(playOff);
        String word = "";
        for (Tile t : hand) {
            if (rootRow > row || rootCol > col) {
                word = "" + t.getLetter() + playOff.getLetter();
            } else if (rootRow < row || rootCol < col) {
                word = "" + playOff.getLetter() + t.getLetter();
            }

            if (scb.dictionary.checkWord(word)) {
                return t;
            }
        }

        //if there is no valid word, return null
        return null;
    }
}
