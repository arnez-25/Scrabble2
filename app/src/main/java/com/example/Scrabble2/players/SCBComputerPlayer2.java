package com.example.Scrabble2.players;
import android.graphics.Point;
import android.util.Pair;

import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameComputerPlayer;
import com.example.GameFramework.utilities.Logger;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleComputerAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleSkipAction;
import com.example.Scrabble2.infoMessage.SCBState;
import com.example.Scrabble2.infoMessage.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * This will be our smart AI. It Should be able to compete with the player instead of just skipping turn
 *
 * @author Riley Cameron
 * @author Alexx Blake
 * @author Nick Tabra
 * @author Jacob Arnez
 * @author David Leon
 *
 * @Version 4/24/2023
 */
public class SCBComputerPlayer2 extends GameComputerPlayer{

    private static final String TAG = "ComputerPlayer";

    SCBState scb;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public SCBComputerPlayer2(String name) {
        super(name);
        scb = null;
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
        //Tracks the current user & establishes new ArrayLists
        Logger.log(TAG, "Computer player " + playerNum + " received info");
        ArrayList<Tile> tilesToPlace = new ArrayList<>();
        ArrayList<Point> tilePoints = new ArrayList<>();
        boolean isAcross = true;

        //Checks if there is an instance of the game state
        if (!(info instanceof SCBState)){
            Logger.log(TAG, "Computer player: info is not a gamestate");
            return;
        }
        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        scb = (SCBState) info;

        //This section finds a word to play
        Logger.log(TAG, "Computer player(" + playerNum + "), Player turn: " + scb.getWhoseMove());
        if(scb.getWhoseMove() == playerNum){
            ArrayList<Tile> myTiles;
            if (playerNum == 0) {
                myTiles = scb.player1Tiles;
            } else {
                myTiles = scb.player2Tiles;
            }

            //create a list of usable letters
            ArrayList<Character> myLetters = new ArrayList<>();
            for (Tile t : myTiles) myLetters.add(t.getLetter());

            //store possible words for each direction
            ArrayList<Pair<String, Point>> acrossWords = new ArrayList<>();
            ArrayList<Pair<String, Point>> downWords = new ArrayList<>();


            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    int spacesAcross = 0;
                    int spacesDown = 0;

                    if (scb.board[i][j].getLetter() != ' ') {
                        spacesAcross = findSpacesAcross(i, j);
                        spacesDown = findSpacesDown(i, j);

                        ArrayList<String> possibleWords = scb.dictionary.findWords("" + scb.board[i][j].getLetter(), null, myLetters);

                        if (spacesAcross > 1) {
                            for (String word : possibleWords) {
                                if (!(word.length() > spacesAcross)) {
                                    Point p = new Point(i, j);
                                    Pair<String, Point> pair = new Pair(word, p);
                                    acrossWords.add(pair);
                                }
                            }
                        }

                        if (spacesDown > 1) {
                            for (String word : possibleWords) {
                                if (!(word.length() > spacesDown)) {
                                    Point p = new Point(i, j);
                                    Pair<String, Point> pair = new Pair(word, p);
                                    downWords.add(pair);
                                }
                            }
                        }
                    }
                }
            }

            //pick a word from one of the lists:
            Random rand = new Random();
            ArrayList<Pair<String, Point>> wordChoices = new ArrayList<>();

            //randomly choose between across or down
            if (rand.nextBoolean() && downWords.size() > 0 && acrossWords.size() > 0) {
                wordChoices = downWords;
                isAcross = false;
            } else if (acrossWords.size() > 0){
                wordChoices = acrossWords;
                isAcross = true;
            } else if (downWords.size() > 0) {
                wordChoices = downWords;
                isAcross = false;
            }

            //find the longest word and choose that one
            Pair<String, Point> longest = new Pair("", new Point(0, 0));
            for (Pair<String, Point> p : wordChoices) {
                if (longest.first.length() < p.first.length()) {
                    longest = p;
                }
            }

            //convert to a tile list and point list:
            for (int i = 1; i < longest.first.length(); i++) {
                for (Tile t : myTiles) {
                    if (t.getLetter() == longest.first.charAt(i)) {
                        tilesToPlace.add(t);
                        Point p;
                        if (isAcross) {
                            p = new Point(longest.second.x, longest.second.y + i);
                        } else {
                            p = new Point(longest.second.x + i, longest.second.y);
                        }
                        tilePoints.add(p);
                    }
                }
            }

            if (tilesToPlace.size() == 0) {
                game.sendAction(new ScrabbleSkipAction(this));
            } else {
                game.sendAction(new ScrabbleComputerAction(this, tilesToPlace, tilePoints));
            }
        }
    }//receiveInfo


    //helper methods for findWords:
    public int findSpacesAcross(int row, int col) {
        int count = 0;
        while (col+count+1 < 15 && row-1 > 0 && row+1 < 15 && scb.board[row][col+count+1].getLetter() == ' ' && scb.board[row+1][col+count+1].getLetter() == ' ' && scb.board[row-1][col+count+1].getLetter() == ' ') {
            count++;
        }

        if (col-1 < 0 || scb.board[row][col-1].getLetter() != ' ') {
            count = 0;
        }

        return count;
    }//findSpacesAcross

    public int findSpacesDown(int row, int col) {
        int count = 0;
        while (row+count+1 < 15 && col+1 < 15 && col-1 > 0 && scb.board[row+count+1][col].getLetter() == ' ' && scb.board[row+count+1][col+1].getLetter() == ' ' && scb.board[row+count+1][col-1].getLetter() == ' ') {
            count++;
        }

        if (row-1 < 0 || scb.board[row-1][col].getLetter() != ' ') {
            count = 0;
        }

        return count;
    }//findSpacesDown
}


