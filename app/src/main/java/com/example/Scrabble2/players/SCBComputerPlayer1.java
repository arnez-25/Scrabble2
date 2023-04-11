package com.example.Scrabble2.players;
import android.graphics.Point;

import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleComputerAction;
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
        ArrayList<Tile> tilesToPlace = new ArrayList<>();
        ArrayList<Point> tilePoints = new ArrayList<>();

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
            ArrayList<Tile> myTiles;
            if (playerNum == 0) {
                myTiles = scb.player1Tiles;
            } else {
                myTiles = scb.player2Tiles;
            }


            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    if (scb.board[i][j].getLetter() != ' ') {
                        Tile toPlace = null;
                        if (scb.board[i+1][j].getLetter() == ' ' && scb.board[i-1][j].getLetter() == ' ') {
                            toPlace = find2LetterWord(scb.board[i][j], i+1, j, myTiles);
                            if (toPlace != null) {
                                tilesToPlace.add(toPlace);
                                tilePoints.add(new Point(i+1, j));
                                game.sendAction(new ScrabbleComputerAction(this, tilesToPlace, tilePoints));
                                return;
                            }

                            toPlace = find2LetterWord(scb.board[i][j], i-1, j, myTiles);
                            if (toPlace != null) {
                                tilesToPlace.add(toPlace);
                                tilePoints.add(new Point(i-1, j));
                                game.sendAction(new ScrabbleComputerAction(this, tilesToPlace, tilePoints));
                                return;
                            }
                        } else if (scb.board[i][j+1].getLetter() == ' ' && scb.board[i][j-1].getLetter() == ' ') {
                            toPlace = find2LetterWord(scb.board[i][j], i, j+1, myTiles);
                            if (toPlace != null) {
                                tilesToPlace.add(toPlace);
                                tilePoints.add(new Point(i, j+1));
                                game.sendAction(new ScrabbleComputerAction(this, tilesToPlace, tilePoints));
                                return;
                            }

                            toPlace = find2LetterWord(scb.board[i][j], i, j-1, myTiles);
                            if (toPlace != null) {
                                tilesToPlace.add(toPlace);
                                tilePoints.add(new Point(i, j-1));
                                game.sendAction(new ScrabbleComputerAction(this, tilesToPlace, tilePoints));
                                return;
                            }
                        }
                    }
                }
            }

            game.sendAction(new ScrabbleSkipAction(this));
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
