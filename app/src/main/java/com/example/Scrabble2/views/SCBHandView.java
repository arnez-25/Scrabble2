package com.example.Scrabble2.views;


import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;

import com.example.GameFramework.utilities.FlashSurfaceView;
import com.example.Scrabble2.infoMessage.SCBState;
import com.example.Scrabble2.infoMessage.Tile;

/**
 * @author Riley Cameron
 * @author Alexx Blake
 * @author Nick Tabra
 * @author Jacob Arnez
 * @author David Leon
 *
 * @version 4/28/2023
 */
public class SCBHandView extends FlashSurfaceView {


    //Tag for logging
    private static final String TAG = "SCBHandView";

    /*
     * Instance variables
     */

    //The game state
    protected SCBState state;

    private Tile t;

    public SCBHandView(Context context) {
        super(context);
        init();
    }//SCBHandView

    public SCBHandView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }//SCHHandView
    /**
     * Helper-method for the constructors
     */
    private void init() { setBackgroundColor(Color.BLACK);} //init

    public void setState(SCBState state) { this.state = state; }//setState
}
