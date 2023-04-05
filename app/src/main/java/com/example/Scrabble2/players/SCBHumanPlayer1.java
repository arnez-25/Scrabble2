package com.example.Scrabble2.players;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameHumanPlayer;
import com.example.GameFramework.utilities.Logger;
import com.example.Scrabble2.ScrabbleActionMessages.ScrabblePlaceAction;
import com.example.Scrabble2.infoMessage.Tile;
import com.example.gametestb.R;
import com.example.Scrabble2.views.SCBSurfaceView;

public class SCBHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener{

    //Tag for logging
    private static final String TAG = "SCBHumanPlayer1";

    // the surface view
    private SCBSurfaceView surfaceView;

    // the ID for the layout to use
    private int layoutId;

    private Tile t;


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
    }


    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {

        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = (SCBSurfaceView)myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener", "OnTouch");
        surfaceView.setOnTouchListener(this);
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
            t = new Tile('d');
            ScrabblePlaceAction action = new ScrabblePlaceAction(this, t, p.y, p.x);
            Logger.log("onTouch", "Human player sending TTTMA ...");
            game.sendAction(action);

            surfaceView.invalidate();
        }

        // register that we have handled the event
        return true;

    }
}
