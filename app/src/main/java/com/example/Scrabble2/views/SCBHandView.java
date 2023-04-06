package com.example.Scrabble2.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.example.GameFramework.utilities.FlashSurfaceView;
import com.example.Scrabble2.infoMessage.SCBState;
import com.example.Scrabble2.infoMessage.Tile;

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
    }


    public SCBHandView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }// ctor
    /**
     * Helper-method for the constructors
     */
    private void init() { setBackgroundColor(Color.BLACK);} //init

    public void setState(SCBState state) { this.state = state; }

}
