package com.example.Scrabble2.players;
import android.view.MotionEvent;
import android.view.View;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameHumanPlayer;
import com.example.gametestb.R;

public class SCBHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener{

    //Tag for logging
    private static final String TAG = "SCBHumanPlayer1";

    // the surface view
    //private TTTSurfaceView surfaceView;

    // the ID for the layout to use
    private int layoutId;


    /**
     * constructor
     *
     * @param name the name of the player
     */
    public SCBHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    /**
     * returns the GUI's top view
     *
     * @return
     * 		the GUI's top view
     */
    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {

    }

    /**
     * callback method when the screen it touched. We're
     * looking for a screen touch
     *
     * @param motionEvent
     * 		the motion event that was detected
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
