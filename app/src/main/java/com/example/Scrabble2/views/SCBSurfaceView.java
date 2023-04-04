package com.example.Scrabble2.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import com.example.GameFramework.utilities.FlashSurfaceView;
import com.example.Scrabble2.infoMessage.SCBState;


/**
 * A SurfaceView which allows which an animation to be drawn on it by a
 * Animator.
 *
 * @author Jacob Arnez
 * @version April 2023
 *
 *
 */
public class SCBSurfaceView extends FlashSurfaceView {

    //Tag for logging
    private static final String TAG = "SCBSurfaceView";



    /*
     * Instance variables
     */

    //The game state
    protected SCBState state;

    //TODO: Add any instance variables for

    /**
     * Constructor for the TTTSurfaceView class.
     *
     * @param context - a reference to the activity this animation is run under
     */
    public SCBSurfaceView(Context context) {
        super(context);
        init();

    }


    /**
     * An alternate constructor for use when a subclass is directly specified
     * in the layout.
     *
     * @param context - a reference to the activity this animation is run under
     * @param attrs   - set of attributes passed from system
     */
    public SCBSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }// ctor

    /**
     * Helper-method for the constructors
     */
    private void init() { setBackgroundColor(backgroundColor());} //init

    public void setState(SCBState state) { this.state = state; }

    /**
     * @return
     * 		the color to paint the tic-tac-toe lines, and the X's and O's
     */
    public int foregroundColor() {
        return Color.BLACK;
    }

    /**
     * @return
     * 		the color to paint the tic-tac-toe lines, and the X's and O's
     */
    public int backgroundColor() {
        return Color.RED;
    }

    public void onDraw(Canvas g) {


    }



}
