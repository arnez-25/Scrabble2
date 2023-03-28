package com.example.Scrabble2.players;
import android.view.View;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameHumanPlayer;

public class SCBHumanPlayer1 extends GameHumanPlayer{

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
    public SCBHumanPlayer1(String name) {
        super(name);
    }

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
}
