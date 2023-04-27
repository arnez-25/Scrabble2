package com.example.Scrabble2.players;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.GameFramework.players.GameHumanPlayer;
import com.example.GameFramework.utilities.Logger;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleComputerAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlaceAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlayAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleResetAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleSkipAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleSwapAction;
import com.example.Scrabble2.infoMessage.SCBState;
import com.example.Scrabble2.infoMessage.Tile;
import com.example.gametestb.R;
import com.example.Scrabble2.views.SCBSurfaceView;

import java.util.ArrayList;

/**
 * @author Riley Cameron
 * @author Alexx Blake
 * @author Nick Tabra
 * @author Jacob Arnez
 * @author David Leon
 *
 * @Version 4/5/2023
 */
public class SCBHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener , View.OnClickListener {


    //Tag for logging
    private static final String TAG = "SCBHumanPlayer1";

    // the surface view
    private SCBSurfaceView surfaceView;

    // the gamestate

    private SCBState gameState;

    private ArrayList<Tile> myHand;

    // the ID for the layout to use
    private final int layoutId;

    private Tile t; // the currently selected tile

    private Button play;
    private Button skip;

    private Button hint;

    private Button reset;

    private Button swap;


    public Tile find2LetterWord(Tile playOff, int row, int col, ArrayList<Tile> hand) {
        int rootRow = gameState.getTileRow(playOff);
        int rootCol = gameState.getTileCol(playOff);
        String word = "";

        //make sure there are no surrounding tiles that could mess up the word
        if (row + 1 < 15 && gameState.board[row + 1][col].getLetter() != ' ' && !gameState.board[row + 1][col].equals(playOff)) {
            return null;
        } else if (row - 1 > 0 && gameState.board[row - 1][col].getLetter() != ' ' && !gameState.board[row - 1][col].equals(playOff)) {
            return null;
        } else if (col + 1 < 15 && gameState.board[row][col + 1].getLetter() != ' ' && !gameState.board[row][col + 1].equals(playOff)) {
            return null;
        } else if (col - 1 > 0 && gameState.board[row][col - 1].getLetter() != ' ' && !gameState.board[row][col - 1].equals(playOff)) {
            return null;
        }

        for (Tile t : hand) {
            if (rootRow > row || rootCol > col) {
                word = "" + t.getLetter() + playOff.getLetter();
            } else if (rootRow < row || rootCol < col) {
                word = "" + playOff.getLetter() + t.getLetter();
            }

            if (gameState.dictionary.checkWord(word)) {
                return t;
            }
        }
        //Function needs a return statement
        return null;
    }//find2LetterWord

    /**
     * constructor
     *
     * @param name     the name of the player
     * @param layoutId the id of the layout to use
     */
    public SCBHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
        t = null;
    }//SCBHumanPlayer1


    @Override
    public void receiveInfo(GameInfo info) {
        Logger.log(TAG, "Human player " + playerNum + " received info");
        if (surfaceView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        } else if (!(info instanceof SCBState))
            // if we do not have a TTTState, ignore
            return;
        else {
            surfaceView.setState((SCBState) info);
            surfaceView.setPlayerNum(playerNum);
            surfaceView.invalidate();

            this.gameState = (SCBState) info;
            if (playerNum == 0) {
                myHand = gameState.player1Tiles;
            } else {
                myHand = gameState.player2Tiles;
            }
            Logger.log(TAG, "receiving");
        }
    }//receiveInfo

    @Override
    public void setAsGui(GameMainActivity activity) {
        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener", "OnTouch");
        surfaceView.setOnTouchListener(this);

        play = myActivity.findViewById(R.id.play_button);
        play.setOnClickListener(this);
        skip = myActivity.findViewById(R.id.skip_button);
        skip.setOnClickListener(this);
        swap = myActivity.findViewById(R.id.swap_button);
        swap.setOnClickListener(this);
        reset = myActivity.findViewById(R.id.reset_button);
        reset.setOnClickListener(this);
        hint = myActivity.findViewById(R.id.hint_button);
        hint.setOnClickListener(this);

    }//setAsGui

    /**
     * returns the GUI's top view
     *
     * @return the GUI's top view
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    protected void initAfterReady() {
        myActivity.setTitle("Scrabble: " + allPlayerNames[0] + " vs. " + allPlayerNames[1]);

    }

    /**
     * callback method when the screen it touched. We're
     * looking for a screen touch
     * <p>
     * <p>
     * the motion event that was detected
     */
    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "up" event
//        if (event.getAction() != MotionEvent.ACTION_UP) return true;
        // get the x and y coordinates of the touch-location;
        // convert them to square coordinates (where both
        // values are in the range 0..2)
        int x = (int) event.getX();
        int y = (int) event.getY();
        int pHand = -1;
        Point pBoard = null;

        // The if statement insures that if the player doesnt click inside the board then it wont calculate its position on the board
        if ((surfaceView.getxL() <= event.getX() && event.getX() <= surfaceView.getxH()) && (surfaceView.getyL() <= event.getY() && event.getY() <= surfaceView.getyH())) {
            float bX = surfaceView.windowX(event.getX()); //Get the x percentage
            float bY = surfaceView.windowY(event.getY()); //Get the y percentage
            Log.d("BOARD", bX + ", " + bY);

            pBoard = surfaceView.mapTouchToBoard(bX, bY);

        }

        //If statement for the hand coordinate
        if ((surfaceView.getHx_L() <= event.getX() && event.getX() <= surfaceView.getHx_H()) && (surfaceView.getHy_L() <= event.getY() && event.getY() <= surfaceView.getHy_H())) {
            float H_bX = surfaceView.handX(event.getX()); //Get the x percentage
            float H_bY = surfaceView.handY(event.getY()); //Get the y percentage
            Log.d("HAND", H_bX + ", " + H_bY);

            pHand = surfaceView.mapTouchToHand(H_bY);

        }
        Log.d("CORDS", x + ", " + y);

        // if the location did not map to a legal square, flash
        // the screen; otherwise, create and send an action to
        // the game
        if (pBoard == null) {
            surfaceView.invalidate();
        } else {
            if (t != null) {
                ScrabblePlaceAction action = new ScrabblePlaceAction(this, t, pBoard.y, pBoard.x);
                Logger.log("onTouch", "Human player sending TTTMA ...");

                game.sendAction(action);
                surfaceView.invalidate();
                t = null;
            }

        }

        if (pHand == -1) {
            surfaceView.invalidate();
        } else {
            if (myHand.size() > pHand) {
                t = myHand.get(pHand);
            }

            surfaceView.invalidate();
        }

        surfaceView.reverseWindowX(0);
        Log.d("test", surfaceView.reverseWindowX(0) + "");

        // register that we have handled the event
        return true;
    }//onTouch

    @Override
    public void onClick(View view) {
        Button clicked = (Button) view;

        if (gameState.getWhoseMove() == playerNum) {
            if (clicked.getId() == R.id.play_button) {
                game.sendAction(new ScrabblePlayAction(this));
            } else if (clicked.getId() == R.id.skip_button) {
                game.sendAction(new ScrabbleSkipAction(this));
            } else if (clicked.getId() == R.id.reset_button) {
                game.sendAction(new ScrabbleResetAction(this));
            } else if (clicked.getId() == R.id.swap_button && t != null) {
                game.sendAction(new ScrabbleSwapAction(this, t));
            } else if (clicked.getId() == R.id.hint_button) {
                ArrayList<Tile> tilesToPlace = new ArrayList<>();
                ArrayList<Point> tilePoints = new ArrayList<>();

                ArrayList<Tile> myTiles;
                if (playerNum == 0) {
                    myTiles = gameState.player1Tiles;
                } else {
                    myTiles = gameState.player2Tiles;
                }

                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 15; j++) {
                        if (gameState.board[i][j].getLetter() != ' ') {
                            Tile toPlace = null;
                            if (!(j + 1 > 14 || i + 1 > 14)) {
                                if (gameState.board[i + 1][j].getLetter() == ' ' && gameState.board[i - 1][j].getLetter() == ' ') {
                                    toPlace = find2LetterWord(gameState.board[i][j], i + 1, j, myTiles);
                                    if (toPlace != null) {
                                        tilesToPlace.add(toPlace);
                                        tilePoints.add(new Point(i + 1, j));
                                        String word = "" + gameState.board[i][j].getLetter() + toPlace.getLetter();
                                        Toast.makeText(myActivity, "played: " + word, Toast.LENGTH_SHORT).show();
                                        game.sendAction(new ScrabbleComputerAction(this, tilesToPlace, tilePoints));
                                        return;
                                    }

                                    toPlace = find2LetterWord(gameState.board[i][j], i - 1, j, myTiles);
                                    if (toPlace != null) {
                                        tilesToPlace.add(toPlace);
                                        tilePoints.add(new Point(i - 1, j));
                                        String word = "" + toPlace.getLetter() + gameState.board[i][j].getLetter();
                                        Toast.makeText(myActivity, "played: " + word, Toast.LENGTH_SHORT).show();
                                        game.sendAction(new ScrabbleComputerAction(this, tilesToPlace, tilePoints));
                                        return;
                                    }
                                } else if (gameState.board[i][j + 1].getLetter() == ' ' && gameState.board[i][j - 1].getLetter() == ' ') {
                                    toPlace = find2LetterWord(gameState.board[i][j], i, j + 1, myTiles);
                                    if (toPlace != null) {
                                        tilesToPlace.add(toPlace);
                                        tilePoints.add(new Point(i, j + 1));
                                        String word = "" + gameState.board[i][j].getLetter() + toPlace.getLetter();
                                        Toast.makeText(myActivity, "played: " + word, Toast.LENGTH_SHORT).show();
                                        game.sendAction(new ScrabbleComputerAction(this, tilesToPlace, tilePoints));
                                        return;
                                    }

                                    toPlace = find2LetterWord(gameState.board[i][j], i, j - 1, myTiles);
                                    if (toPlace != null) {
                                        tilesToPlace.add(toPlace);
                                        tilePoints.add(new Point(i, j - 1));
                                        String word = "" + toPlace.getLetter() + gameState.board[i][j].getLetter();
                                        Toast.makeText(myActivity, "played: " + word, Toast.LENGTH_SHORT).show();
                                        game.sendAction(new ScrabbleComputerAction(this, tilesToPlace, tilePoints));
                                        return;
                                    }
                                }
                            }
                        }

                    }
                    Toast.makeText(myActivity, "No Hint Available", Toast.LENGTH_SHORT).show();
                }

                Log.d("BUTTON", "ButtonClick");
                surfaceView.invalidate();
            }
        }//onClick
    }
}
