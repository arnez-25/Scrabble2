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
public class SCBHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener , View.OnClickListener{



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

    private Button[] playerHand = null;

    private Button play;
    private Button skip;

    private Button hint;

    private Button reset;

    private Button swap;


    /**
     * constructor
     *
     * @param name the name of the player
     *
     * @param layoutId
     *       the id of the layout to use
     */
    public SCBHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
        t = null;
    }


    @Override
    public void receiveInfo(GameInfo info) {
        Logger.log(TAG, "Human player " + playerNum + " received info");
        if (surfaceView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        }
        else if (!(info instanceof SCBState))
            // if we do not have a TTTState, ignore
            return;
        else {
            surfaceView.setState((SCBState)info);
            surfaceView.invalidate();

            this.gameState = (SCBState) info;
            if (playerNum == 0) {
                myHand = gameState.player1Tiles;
            } else {
                myHand = gameState.player2Tiles;
            }

            Logger.log(TAG, "receiving");
        }


    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener", "OnTouch");
        surfaceView.setOnTouchListener(this);
        playerHand = new Button[]{
                myActivity.findViewById(R.id.tile0),
                myActivity.findViewById(R.id.tile1),
                myActivity.findViewById(R.id.tile2),
                myActivity.findViewById(R.id.tile3),
                myActivity.findViewById(R.id.tile4),
                myActivity.findViewById(R.id.tile5),
                myActivity.findViewById(R.id.tile6),

        };

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

        for(int i = 0; i < 7; i++) {
            playerHand[i].setOnClickListener(this);
        }
    }

    /**
     * returns the GUI's top view
     *
     * @return
     * 		the GUI's top view
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
        myActivity.setTitle("Scrabble: "+allPlayerNames[0]+" vs. "+allPlayerNames[1]);

    }



    /**
     * callback method when the screen it touched. We're
     * looking for a screen touch
     *
     *
     * 		the motion event that was detected
     */
    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "up" event
//        if (event.getAction() != MotionEvent.ACTION_UP) return true;
        // get the x and y coordinates of the touch-location;
        // convert them to square coordinates (where both
        // values are in the range 0..2)
        int x = (int) event.getX();
        int y = (int) event.getY();

        // The if statement insures that if the player doesnt click inside the board then it wont calculate its position on the board
        if((surfaceView.getxL() <= event.getX() && event.getX() <= surfaceView.getxH()) && (surfaceView.getyL() <= event.getY() && event.getY() <= surfaceView.getyH())) {
            float bX = surfaceView.windowX(event.getX()); //Get the x percentage
            float bY = surfaceView.windowY(event.getY()); //Get the y percentage
            Log.d("BOARD", bX + ", " + bY);

            surfaceView.mapTouchToBoard(bX, bY);

        }


        //If statement for the hand coordinate
        if((surfaceView.getHx_L() <= event.getX() && event.getX() <= surfaceView.getHx_H()) && (surfaceView.getHy_L() <= event.getY() && event.getY() <= surfaceView.getHy_H())){
            float H_bX = surfaceView.handX(event.getX()); //Get the x percentage
            float H_bY = surfaceView.handY(event.getY()); //Get the y percentage
            Log.d("HAND", H_bX + ", " + H_bY);

            surfaceView.mapTouchToHand(H_bY);

        }
        Log.d("CORDS", x + ", " + y);
        Point p = surfaceView.mapPixelToSquare(x, y);

        // if the location did not map to a legal square, flash
        // the screen; otherwise, create and send an action to
        // the game
        if (p == null) {
            surfaceView.flash(Color.RED, 50);
        } else {
            if (t != null) {
                ScrabblePlaceAction action = new ScrabblePlaceAction(this, t, p.y, p.x);
                Logger.log("onTouch", "Human player sending TTTMA ...");
                //redraw button text:
                for (Button b : playerHand) {
                    b.setText("");
                }

                for (int i = 0; i < myHand.size() && i < 7; i++) {
                    if (gameState != null) {
                        String ch = "" + myHand.get(i).getLetter();
                        playerHand[i].setText(ch);//TODO: if statement to select the right players tiles
                    }
                }


                game.sendAction(action);
                surfaceView.invalidate();
                t = null;
            }

        }

        // register that we have handled the event
        return true;
    }

    @Override
    public void onClick(View view) {
        Button clicked = (Button) view;

        if (gameState.getWhoseMove() == playerNum) {
            if (clicked.getId() == R.id.tile0 && myHand.size() >= 1) {
                t = myHand.get(0);
            } else if (clicked.getId() == R.id.tile1 && myHand.size() >= 2) {
                t = myHand.get(1);
            } else if (clicked.getId() == R.id.tile2 && myHand.size() >= 3) {
                t = myHand.get(2);
            } else if (clicked.getId() == R.id.tile3 && myHand.size() >= 4) {
                t = myHand.get(3);
            } else if (clicked.getId() == R.id.tile4 && myHand.size() >= 5) {
                t = myHand.get(4);
            } else if (clicked.getId() == R.id.tile5 && myHand.size() >= 6) {
                t = myHand.get(5);
            } else if (clicked.getId() == R.id.tile6 && myHand.size() >= 7) {
                t = myHand.get(6);
            } else if (clicked.getId() == R.id.play_button) {
                game.sendAction(new ScrabblePlayAction(this));
            } else if (clicked.getId() == R.id.skip_button) {
                game.sendAction(new ScrabbleSkipAction(this));
            } else if (clicked.getId() == R.id.reset_button) {
                game.sendAction(new ScrabbleResetAction(this));
            } else if (clicked.getId() == R.id.swap_button && t != null) {
                game.sendAction(new ScrabbleSwapAction(this, t));
            } else if (clicked.getId() == R.id.hint_button) {
                Toast.makeText(myActivity, "No Hint Available", Toast.LENGTH_SHORT).show();//TODO: provide a hint
            }
        }

        Log.d("BUTTON", "ButtonClick");
    }
}
