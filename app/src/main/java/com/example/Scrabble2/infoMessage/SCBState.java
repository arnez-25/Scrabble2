package com.example.Scrabble2.infoMessage;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.example.GameFramework.infoMessage.GameState;

/**
 * Contains the state of a Scrabble game.  Sent by the game when
 * a player wants to enquire about the state of the game.  (E.g., to display
 * it, or to help figure out its next move.)
 *
 * @author Jacob Arnez
 * @author Riley Cameron
 * @version April 2023
 */
public class SCBState extends GameState implements Serializable{//TODO: fix game ending early

    //Tag for logging
    private static final String TAG = "SCBState";
    private static final long serialVersionUID = 7552321013488624386L;

    ///////////////////////////////////////////////////
    // ************** instance variables ************
    ///////////////////////////////////////////////////

    //Establishing variables to be used in the Game State.
    public ScrabbleDictionary dictionary;
    public boolean gameRunning;
    public boolean firstPlay;

    public ArrayList<Tile> player1Tiles;
    public ArrayList<Tile> player2Tiles;

    public int[][]pointKey;

    public Tile[][] board;

    public int p1Score;
    public int p2Score;
    public int iqLevel;
    public ArrayList<Tile> bag;

    public String hintWord;

    //HashMap for assigning and calculating letter scores
    public HashMap<Character, Integer> letterScore = new HashMap<>();

    //constants for word direction
    public static final int DOWN = 0;
    public static final int ACROSS = 1;

    //constants for board mapping:
    public static final int START = 3;
    public static final int D_LETTER = 1;
    public static final int D_WORD = 2;
    public static final int EMPTY = 0;

    public int playerToMove;

    /**
     * GameState is the Constructor that establishes and defines the needed variables & parameters that will be used for the game.
     */
    public SCBState(BufferedReader reader) {
        dictionary = new ScrabbleDictionary(reader);
        playerToMove = 0;
        gameRunning = true;//creating a gameState should start the game

        //init board to null (represents empty tile)
        board = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = new Tile(' ');
                board[i][j].setOnBoard(true);
            }
        }

        //init board point key, it as well establishes special slots that include Double Letter & Double Word, with Start Position. Alongside default empty spots.
        pointKey = new int[15][15];
        pointKey[0] = new int[]{D_WORD, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, D_WORD};
        pointKey[1] = new int[]{EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY};
        pointKey[2] = new int[]{EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY};
        pointKey[3] = new int[]{D_LETTER, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, D_LETTER};
        pointKey[4] = new int[]{EMPTY, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, EMPTY};
        pointKey[5] = new int[]{EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY};
        pointKey[6] = new int[]{EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY};
        pointKey[7] = new int[]{D_WORD, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, START, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, D_WORD};
        pointKey[8] = new int[]{EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY};
        pointKey[9] = new int[]{EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY};
        pointKey[10] = new int[]{EMPTY, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, EMPTY};
        pointKey[11] = new int[]{D_LETTER, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, D_LETTER};
        pointKey[12] = new int[]{EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY};
        pointKey[13] = new int[]{EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY};
        pointKey[14] = new int[]{D_WORD, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, EMPTY, D_WORD, EMPTY, EMPTY, EMPTY, D_LETTER, EMPTY, EMPTY, D_WORD};

        p1Score = 0;
        p2Score = 0;
        iqLevel = 0;
        hintWord = " ";
        firstPlay = true;

        //Assigning score values for each letter in the HashMap
        //Letters that are valued at 1
        letterScore.put('A', 1);
        letterScore.put('E', 1);
        letterScore.put('I', 1);
        letterScore.put('L', 1);
        letterScore.put('O', 1);
        letterScore.put('N', 1);
        letterScore.put('R', 1);
        letterScore.put('T', 1);
        letterScore.put('S', 1);
        letterScore.put('U', 1);

        //Letters that are valued at 2
        letterScore.put('D', 2);
        letterScore.put('G', 2);

        //Letters that are valued at 3
        letterScore.put('B', 3);
        letterScore.put('C', 3);
        letterScore.put('M', 3);
        letterScore.put('P', 3);

        //Letters that are valued at 4
        letterScore.put('F', 4);
        letterScore.put('H', 4);
        letterScore.put('V', 4);
        letterScore.put('W', 4);
        letterScore.put('Y', 4);

        //Letters that are valued at 5
        letterScore.put('K', 5);

        //Letters that are valued at 8
        letterScore.put('J', 8);
        letterScore.put('X', 8);

        //Letters that are valued at 10
        letterScore.put('Q', 10);
        letterScore.put('Z', 10);

        bag = new ArrayList<Tile>();
        makeBag(bag);

        //initialize player hands:
        player1Tiles = new ArrayList<>();
        player2Tiles = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            player1Tiles.add(drawFromBag());
            player2Tiles.add(drawFromBag());
        }
    }

    /**
     * This method is the copy constructor
     *
     * @param g The constructed game state that is in use
     */
    public SCBState(SCBState g) {
        dictionary = g.dictionary;
        gameRunning = g.gameRunning;
        firstPlay = g.firstPlay;

        player1Tiles = new ArrayList<>();
        for (Tile t : g.player1Tiles) {
            player1Tiles.add(new Tile(t));
        }

        player2Tiles = new ArrayList<>();
        for (Tile t : g.player2Tiles) {
            player2Tiles.add(new Tile(t));
        }

        //copy board
        board = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = new Tile(g.board[i][j]);
            }
        }

        p1Score = g.p1Score;
        p2Score = g.p2Score;
        iqLevel = g.iqLevel;

        bag = new ArrayList<Tile>();
        for(int i = 0; i < g.bag.size(); i++ ){
            Tile temp = new Tile(g.bag.get(i));
            bag.add(temp);
        }

        //copy pointKey
        pointKey = new int[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                pointKey[i][j] = g.pointKey[i][j];
            }
        }

        hintWord = g.hintWord;
    }

    /**
     * This method moves the tiles on the board back into the player's hand
     *
     * @param playerId
     * @return
     */
    public boolean resetHand(int playerId) {
        if (playerId == playerToMove) {
            //put letters back into player's hand:
            for (Tile[] row : board) {
                for (Tile t : row) {
                    if (playerId == 0 && !t.isOnBoard()) {
                        player1Tiles.add(t);
                    } else if (playerId == 1 && !t.isOnBoard()) {
                        player2Tiles.add(t);
                    }
                }
            }

            cleanBoard();
            return true;
        } else {
            return false;
        }
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

    /**
     * This method places a tile on the board at the given position.
     *
     * @param playerId Checks which player is currently active
     * @param t This is the tile that is being used
     * @param row The location in the Row Section
     * @param col The location in the Column Section
     */
    public void placeTile(int playerId, Tile t, int row, int col) {
        if (playerId != playerToMove) {return;}//if its not this player's turn, don't place the tile

        Tile localTile = null;
        //find matching tile in our copy:
        if (playerId == 0) {
            for (Tile tile : player1Tiles) {
                if (tile.getLetter() == t.getLetter()) {
                    localTile = tile;
                    break;
                }
            }
        } else {
            for (Tile tile : player2Tiles) {
                if (tile.getLetter() == t.getLetter()) {
                    localTile = tile;
                    break;
                }
            }
        }

        //place the tile *if* the board position is empty
        if (board[row][col].getLetter() == ' ') {
            board[row][col] = localTile;

            //remove the placed tile from the player's hand
            if (playerId == 0) {
                player1Tiles.remove(localTile);
            } else {
                player2Tiles.remove(localTile);
            }
        }

    }

    /**
     * Mathod to find a Tile given its coordinates
     *
     * @param row
     * @param col
     * @return Tile  - at position row, col
     */
    public Tile getPiece(int row, int col) {
        // if we're out of bounds or anything, return '?';
        if (board == null || row < 0 || col < 0) return null;
        if (row >= board.length || col >= board[row].length) return null;

        // return the character that is in the proper position
        return board[row][col];
    }


    /**
     * This method checks the turn of the player and checks the word that is played to validate that it works.
     *
     * @param playerId Checks which player is playing
     * @return Returns either a true or false response after the method has completed
     */
    public boolean playWord(int playerId) {
        if (playerId == playerToMove) {
            String wordPlayed = "";
            boolean wordChecker = false;

            //algorithm for finding what word was played on the grid
            int wordDir = -1;
            Tile firstLetter = null;
            int row1 = -1;
            int col1 = -1;
            Tile secondLetter = null;
            int row2 = -1;
            int col2 = -1;
            ArrayList<Tile> lettersPlayed = new ArrayList<>();
            int rowStart = -1;
            int colStart = -1;

            //find first and second letters played
            for (int i = 0; i < 15; i++) {
                if (secondLetter != null) break;
                for (int j = 0; j < 15; j++) {
                    if (!board[i][j].isOnBoard()) {
                        if (firstLetter == null) {
                            firstLetter = board[i][j];
                            row1 = i;
                            col1 = j;
                        } else {
                            secondLetter = board[i][j];
                            row2 = i;
                            col2 = j;
                            break;
                        }
                    }
                }
            }

            //if they did not play a word return null
            if (firstLetter == null) {
                Log.d(TAG, "Player " + playerId + " did not play a letter");
                return false;
            }

            //set word direction;
            if (row1 == row2) wordDir = ACROSS;
            if (col1 == col2) wordDir = DOWN;

            //case for a single letter being played:
            if (secondLetter == null) {
                //check grid around firstLetter
                if (row1+1 < 15 && board[row1+1][col1].getLetter() != ' ') {
                    secondLetter = board[row1+1][col1];
                    wordDir = DOWN;
                } else if (row1-1 > 0 && board[row1-1][col1].getLetter() != ' ') {
                    secondLetter = board[row1-1][col1];
                    wordDir = DOWN;
                } else if (col1+1 < 15 && board[row1][col1+1].getLetter() != ' ') {
                    secondLetter = board[row1][col1+1];
                    wordDir = ACROSS;
                } else if (col1-1 > 0 && board[row1][col1-1].getLetter() != ' ') {
                    secondLetter = board[row1][col1-1];
                    wordDir = DOWN;
                } else {
                    return false;//the word played is a single letter that isn't connected to any other tiles
                }
            }

            //find the starting letter of the word (might not be a tile that was just played)
            colStart = col1;
            rowStart = row1;
            if (wordDir == ACROSS) {
                int i = col1-1;
                while (i >= 0) {
                    if (board[row1][i].getLetter() != ' ') {
                        colStart = i;
                        i--;
                    } else {
                        break;
                    }
                }
            } else if (wordDir == DOWN) {
                int i = row1-1;
                while (i >= 0) {
                    if (board[i][col1].getLetter() != ' ') {
                        rowStart = i;
                        i--;
                    } else {
                        break;
                    }
                }
            }

            //add letters to the array list starting at the 'start letter'
            if (wordDir == ACROSS) {
                int i = colStart;
                while (i >= 0) {
                    if (board[rowStart][i].getLetter() != ' ') {
                        lettersPlayed.add(board[rowStart][i]);
                        i++;
                    } else {
                        break;
                    }
                }
            } else if (wordDir == DOWN) {
                int i = rowStart;
                while (i >= 0) {
                    if (board[i][colStart].getLetter() != ' ') {
                        lettersPlayed.add(board[i][colStart]);
                        i++;
                    } else {
                        break;
                    }
                }
            }

            //make a list of perpendicular words:
            ArrayList<ArrayList<Tile>> perpWords = new ArrayList<>();
            for (Tile t : lettersPlayed) {
                if (!t.isOnBoard()) {
                    //add word branching from this tile to the perpWords list
                    perpWords.add(findPerpWord(t, wordDir));
                }
            }



            //convert arrayList of Tiles into a String
            wordPlayed = tilesToString(lettersPlayed);

            //Checks if the word has been played and assigns value based on play.
            wordChecker = dictionary.checkWord(wordPlayed);

            //check that the number of letters played matches the number absent in the players hand
            int numPlayedFound = 0;
            for (Tile t : lettersPlayed) {
                if (!t.isOnBoard()) numPlayedFound++;
            }
            if (playerId == 0 && 7-player1Tiles.size() != numPlayedFound) {
                wordChecker = false;
            } else if (playerId == 1 && 7-player2Tiles.size() != numPlayedFound) {
                wordChecker = false;
            }

            //check to make sure the new word was played off an existing word:
            if (wordPlayed.length() == numPlayedFound && !firstPlay) {
                wordChecker = false;
            }

            //check each perpendicular word:
            for (ArrayList<Tile> word : perpWords) {
                if (word.size() > 1 && !dictionary.checkWord(tilesToString(word))) {
                    wordChecker = false;
                }
            }

            //starting play case: word must be played off the center square
            if (firstPlay) {
                boolean foundMatch = false;
                for (Tile t : lettersPlayed) {
                    if (pointKey[getTileRow(t)][getTileCol(t)] == START) {
                        foundMatch = true;
                    }
                }

                if (foundMatch) {
                    firstPlay = false;
                } else {
                    wordChecker = false;
                }
            }

            //all checks are finished when entering this if statement:
            if (wordChecker) {
                if (playerId == 0) {
                    //draw back up to hand size:
                    while (player1Tiles.size() < 7) {
                        Tile t = drawFromBag();
                        if (t == null) {
                            gameRunning = false;
                            break;
                        } else {
                            player1Tiles.add(t);
                        }
                    }

                    String info = "Player 0 has played the word " + wordPlayed + ", to form:";
                    for (ArrayList<Tile> word : perpWords) {
                        if (word.size() > 1) {
                            info = info.concat(" " + tilesToString(word));
                        }
                    }

                    Log.d(TAG, info);
                    playerToMove = 1;
                    p1Score += calculateScore(lettersPlayed, perpWords);
                } else {
                    while (player2Tiles.size() < 7) {
                        Tile t = drawFromBag();
                        if (t == null) {
                            gameRunning = false;
                            break;
                        } else {
                            player2Tiles.add(t);
                        }
                    }

                    String info = "Player 1 has played the word " + wordPlayed + ", to form:";
                    for (ArrayList<Tile> word : perpWords) {
                        if (word.size() > 1) {
                            info = info.concat(" " + tilesToString(word));
                        }
                    }

                    Log.d(TAG, info);
                    playerToMove = 0;
                    p2Score += calculateScore(lettersPlayed, perpWords);
                }

                //set 'onBoard' to true for tiles played
                for (Tile t : lettersPlayed) {
                    t.setOnBoard(true);
                }
                return true;
            }
            else {
                //put letters back into player's hand:
                for (Tile[] row : board) {
                    for (Tile t : row) {
                        if (playerId == 0 && !t.isOnBoard()) {
                            player1Tiles.add(t);
                        } else if (playerId == 1 && !t.isOnBoard()) {
                            player2Tiles.add(t);
                        }
                    }
                }

                cleanBoard();

                return false;
            }
        }
        else{
            Log.d(TAG, "Invalid Move for player " + playerId );
            return false;
        }
    }
    //playWord

    /**
     * This method finds the score of a given play by adding up the points of all the new words that are formed
     *
     * @param word - 'main' word that was played
     * @param perpWords - new words formed that branch off the main word
     * @return totalScore - the number of points earned
     */
    public int calculateScore(ArrayList<Tile> word, ArrayList<ArrayList<Tile>> perpWords) {//TODO: dont count points of single letter perpendicular 'words'
        int totalScore = 0;
        int multiplier = 1;

        int wordScore = 0;//temporary score for each word
        for (Tile t : word) {//score for main word that was played
            int letterMultiplier = 1;
            if (!t.isOnBoard()) {//check if the square is a double letter/word
                if (pointKey[getTileRow(t)][getTileCol(t)] == D_LETTER) {
                    letterMultiplier *= 2;
                }
                if (pointKey[getTileRow(t)][getTileCol(t)] == D_WORD || pointKey[getTileRow(t)][getTileCol(t)] == START) {
                    multiplier *= 2;
                }
            }
            wordScore += (t.getScore() * letterMultiplier);//add each letter score to the word score
        }
        totalScore += wordScore * multiplier;//add the word score to the total score

        //add the points for each perpendicular word - same method as above
        for (ArrayList<Tile> w : perpWords) {
            wordScore = 0;
            multiplier = 1;
            for (Tile t : w) {
                int letterMultiplier = 1;
                if (!t.isOnBoard()) {
                    if (pointKey[getTileRow(t)][getTileCol(t)] == D_LETTER) letterMultiplier *= 2;
                    if (pointKey[getTileRow(t)][getTileCol(t)] == D_WORD || pointKey[getTileRow(t)][getTileCol(t)] == START)
                        multiplier *= 2;
                }
                wordScore += (t.getScore() * letterMultiplier);
            }
            totalScore += wordScore * multiplier;
        }

        return totalScore;
    }

    /**
     * This method finds the words connected to a given tile that are perpendicular to the word direction
     *
     * @param t
     * @param wordDir
     * @return the word perpendicular to Tile t as a string
     */
    public ArrayList<Tile> findPerpWord(Tile t, int wordDir) {
        ArrayList<Tile> lettersInWord = new ArrayList<>();
        int startCol = getTileCol(t);
        int startRow = getTileRow(t);

        //across case
        if (wordDir == ACROSS) {
            //find the start position of the perp word
            while (startRow-1 >= 0) {
                if (board[startRow-1][startCol].getLetter() != ' ') {
                    startRow--;
                } else {
                    break;
                }
            }

            //loop back through all the consecutive letters to form a word
            int i = startRow;
            while (i <= 14 && board[i][startCol].getLetter() != ' ') {
                lettersInWord.add(board[i][startCol]);
                i++;
            }
        } else {//down case
            //find the start position of the perp word
            while (startCol-1 >= 0) {
                if (board[startRow][startCol-1].getLetter() != ' ') {
                    startCol--;
                } else {
                    break;
                }
            }

            //loop back through all the consecutive letters to form a word
            int i = startCol;
            while (i <= 14 && board[startRow][i].getLetter() != ' ') {
                lettersInWord.add(board[startRow][i]);
                i++;
            }
        }

        return lettersInWord;
    }


    /**
     * Clears the board of any Tile objects that do not have their 'onBoard' parameter set to true
     */
    public void cleanBoard() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (!board[i][j].isOnBoard()) {
                    board[i][j] = new Tile(' ');
                    board[i][j].setOnBoard(true);
                }
            }
        }
    }

    /**
     * Converts a list of Tiles to a string
     *
     * @param list
     * @return String:word
     */
    public String tilesToString(ArrayList<Tile> list) {
        String word = "";
        for (int i = 0;i < list.size();i++) {
            word = word.concat(Character.toString(list.get(i).getLetter()));
        }

        return word;
    }

    /**
     * returns the row of a given tile:  (-1 means it is not on the board)
     *
     * @param t
     * @return row
     */
    public int getTileRow(Tile t) {
        int row = -1;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (board[i][j].equals(t)) {
                    row = i;
                }
            }
        }

        return row;
    }

    /**
     * returns the column of a given tile:  (-1 means it is not on the board)
     *
     * @param t
     * @return col
     */
    public int getTileCol(Tile t) {
        int col = -1;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (board[i][j].equals(t)) {
                    col = j;
                }
            }
        }

        return col;
    }

    /**
     * Establishes a skip method in order for the player to change over their turn.
     *
     * @param playerId the Player that is current in control
     * @return returns whether the operation was true or false
     */
    public boolean skipper(int playerId){
        if(playerId == playerToMove) {
            if (playerId == 0) {
                Log.d(TAG, "Player 0 has skipped their turn");
                playerToMove = 1;
            } else {
                Log.d(TAG, "Player 1 has skipped their turn");
                playerToMove = 0;
            }
            cleanBoard();
            return true;
        }
        else{
            Log.d(TAG, "Invalid Move for player " + playerId );

            return false;
        }
    }

    /**
     * Swaps a tile from the bag to be added.
     *
     * @param playerId Checks which player is currently active
     * @param playTile Checks the tile that they are swapping
     * @return returns whether action was true or false
     */
    public boolean swapper(int playerId, Tile playTile){//TODO: fix so that the tile being swapped is the local copy
        if(playerId == playerToMove){
            bag.add(playTile);//put swapped out tile back in the bag

            //remove the swapped out tile from the correct player's hand and give them a new tile
            if (playerId == 0) {
                player1Tiles.remove(playTile);
                player1Tiles.add(drawFromBag());
                Log.d(TAG, "Player 0 has swapped their one of their tiles");
            } else if (playerId == 1) {
                player2Tiles.remove(playTile);
                player2Tiles.add(drawFromBag());
                Log.d(TAG, "Player 1 has swapped their one of their tiles");
            }
            return true;
        }
        else{
            Log.d(TAG, "Invalid Move for player " + playerId );
            return false;
        }
    }

    /**
     * Gives a hint towards the using player when method is active.
     *
     * @param playerId checks which player is currently using the method
     * @return Returns whether there is a hint available or no hint available
     */
    public boolean hinter(int playerId){
        if(playerId == playerToMove){
            hintWord = "No hint available.";
            Log.d(TAG, "Player " + playerId + " has asked for a hint");
            return true;
        }
        else{
            hintWord = "No hint available.";
            Log.d(TAG, "Invalid Move for player " + playerId );

            return false;
        }
    }

    /**
     * This method fills the bag array list with the correct amount of each letter Tile.
     *
     * @param bag carries the amount of tiles and what tiles that it is valued at
     */
    public void makeBag(ArrayList<Tile> bag) {
        //two arrays make a key for how many of each letter Tile are needed
        Character[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        int[] numLetters = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};

        //loop through each letter
        for (int i = 0; i < 26; i++) {
            //loop as many times as the given letter is needed
            for (int j = 0; j < numLetters[i]; j++) {
                Tile t = new Tile(letters[i]);//create a new tile and pass in the letter
                Integer score  = letterScore.get(letters[i]);//set the tile score based on the hashmap
                t.setScore(score);
                t.setOnBoard(false);
                bag.add(t);//add it to the bag
            }
        }
    }

    /**
     * This method removes a Tile from the bag and returns that tile.
     *
     * @return random tile
     */
    public Tile drawFromBag() {
        if(!bag.isEmpty()) {
            Collections.shuffle(bag);
            return bag.remove(0);
        } else {
            return null;
        }
    }

    /**
     * This method describes the state of the game as a string.
     * Prints the values of all the variables in the game state.
     */
    @NonNull
    @Override
    public String toString() {
        String test = "";

        //print player 1 tiles
        test = test.concat("Player1 tiles: ");
        for (int i = 0; i < player1Tiles.size(); i++) {
            test = test.concat("[" + player1Tiles.get(i).getLetter() + "]");
        }

        //print player 2 tiles
        test = test.concat("\nPlayer2 tiles: ");
        for (int i = 0; i < player2Tiles.size(); i++) {
            test = test.concat("[" + player2Tiles.get(i).getLetter() + "]");
        }

        //print the board:
        test = test.concat("\nBoard: \n");

        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++) {
                if (board[i][j].getLetter() == ' ') {
                    test = test.concat("[  ]");
                } else {
                    test = test.concat("[" + board[i][j].getLetter() + "]");
                }
            }
            test = test.concat("\n");
        }

        //print letters in bag
        test = test.concat("\n Letters in the Bag: ");
        for (int i = 0; i< bag.size(); i++) {
            test = test.concat(bag.get(i).getLetter() + " ");
        }

        //print the game running state (boolean value)
        test = test.concat("\n Game running: ");
        String gameStateString = String.valueOf(gameRunning);
        test = test.concat(gameStateString);

        //print player 1 score
        test = test.concat("\n Player 1 score: ");
        String player1ScoreString = String.valueOf(p1Score);
        test = test.concat(player1ScoreString);

        //print player 2 score
        test = test.concat("\n Player 2 score: ");
        String player2ScoreString = String.valueOf(p2Score);
        test = test.concat(player2ScoreString);

        //print Iq Level
        test = test.concat("\n Iq Level: ");
        String iqLevelString = String.valueOf(iqLevel);
        test = test.concat(iqLevelString);

        //print the player id
        test = test.concat("\n Player Turn: ");
        String playerIdString = String.valueOf(playerToMove);
        test = test.concat(playerIdString);

        Log.d(TAG, test);
        return test;
    }

}


