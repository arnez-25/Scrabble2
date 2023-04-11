package com.example.Scrabble2.players;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.GameFramework.players.GameHumanPlayer;
import com.example.GameFramework.utilities.Logger;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlaceAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlayAction;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabbleSkipAction;
import com.example.Scrabble2.infoMessage.SCBState;
import com.example.Scrabble2.infoMessage.Tile;
import com.example.gametestb.R;
import com.example.Scrabble2.views.SCBSurfaceView;

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

    // the ID for the layout to use
    private final int layoutId;

    private Tile t;

    private Button[] playerHand = null;

    private Button play;
    private Button skip;


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

                for (int i = 0; i < gameState.player1Tiles.size() && i < 7; i++) {
                    if (gameState != null) {
                        String ch = "" + gameState.player1Tiles.get(i).getLetter();
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
            if (clicked.getId() == R.id.tile0 && gameState.player1Tiles.size() >= 1) {
                t = gameState.player1Tiles.get(0);
            } else if (clicked.getId() == R.id.tile1 && gameState.player1Tiles.size() >= 2) {
                t = gameState.player1Tiles.get(1);
            } else if (clicked.getId() == R.id.tile2 && gameState.player1Tiles.size() >= 3) {
                t = gameState.player1Tiles.get(2);
            } else if (clicked.getId() == R.id.tile3 && gameState.player1Tiles.size() >= 4) {
                t = gameState.player1Tiles.get(3);
            } else if (clicked.getId() == R.id.tile4 && gameState.player1Tiles.size() >= 5) {
                t = gameState.player1Tiles.get(4);
            } else if (clicked.getId() == R.id.tile5 && gameState.player1Tiles.size() >= 6) {
                t = gameState.player1Tiles.get(5);
            } else if (clicked.getId() == R.id.tile6 && gameState.player1Tiles.size() >= 7) {
                t = gameState.player1Tiles.get(6);
            } else if (clicked.getId() == R.id.play_button) {
                game.sendAction(new ScrabblePlayAction(this));
            } else if (clicked.getId() == R.id.skip_button) {
                game.sendAction(new ScrabbleSkipAction(this));
            }
        }

        Log.d("BUTTON", "ButtonClick");
    }
}
