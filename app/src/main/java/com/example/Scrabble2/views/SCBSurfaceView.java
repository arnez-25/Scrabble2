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
import android.util.Log;

import com.example.GameFramework.utilities.FlashSurfaceView;
import com.example.Scrabble2.infoMessage.SCBState;
import com.example.Scrabble2.infoMessage.Tile;


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

    private final static float BORDER_PERCENT = 5;
    private final static float SQUARE_SIZE_PERCENT = 6; // size of each of our 9 squares
    // private final static float LINE_WIDTH_PERCENT = 3; // width of a tic-tac-toe line
    private final static float LINE_WIDTH_PERCENT = 1;
    private final static float SQUARE_DELTA_PERCENT = SQUARE_SIZE_PERCENT
            + LINE_WIDTH_PERCENT; // distance from left (or top) edge of square to the next one

    private final static float BORDER_PERCENT2 = 5;
    private final static float SQUARE_SIZE_PERCENT2 = 6; // size of each of our 9 squares
    // private final static float LINE_WIDTH_PERCENT = 3; // width of a tic-tac-toe line
    private final static float LINE_WIDTH_PERCENT2 = 1;
    private final static float SQUARE_DELTA_PERCENT2 = SQUARE_SIZE_PERCENT
            + LINE_WIDTH_PERCENT;
    //Coordinate boundaries for the board and player hand

    //Board

    //IMPORTANT!: @Jacob
    //TOPLEFT BOARD: (500, 82)
    //BOTRIGHT BOARD: (1440, 1020)

    // x Low and High
    private float xL = 500;
    private float xH = 1440;

    // y Low and High
    private float yL = 82;
    private float yH = 1020;

    //Hand

    //IMPORTANT!:@jacob
    //TOPLEFT HAND: (1640, 73)
    //BOTRIGHT HAND: (1807, 1000)


    private float Hx_L = 1640;
    private float Hy_L = 73;
    private float Hx_H = 1807;
    private float Hy_H = 1000;


    /*
     * Instance variables
     */

    // the offset from the left and top to the beginning of our "middle square"; one
    // of these will always be zero
    protected float hBase;
    protected float vBase;

    // the size of one edge of our "middle square", or -1 if we have not determined
    // size
    protected float fullSquare;

    /*
     * Instance variables
     */

    //The game state
    protected SCBState state;

    private Tile t;

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

    public int testColor() {
        return Color.GREEN;
    }



    public void onDraw(Canvas g) {
        // update the variables that relate
        // to the dimensions of the animation surface
        updateDimensions(g);

        // paint the TTT-board's horizontal and vertical lines
        Paint p = new Paint();
        p.setColor(foregroundColor());

        float leftVert = 500;
        float topLeftVert = 82;
        float rightVert = 510;
         float bottomRightVert = 1013;




        float leftHor = 80;
        float topLeftHor = 500;
        float rightHor = 90;
        float bottomRightHor = 1440;


       // g.drawRect(topLeftHor, leftHor, bottomRightHor, rightHor, p);
        /*
        for (int j = 0; j <= 15; j++) {
            g.drawRect(leftVert + (j*100), topLeftVert, rightVert + (j*100), bottomRightVert, p); //vertical
            g.drawRect(topLeftHor, leftHor + (j*100), bottomRightHor, rightHor + (j*100), p);
        }
        */


        for (int j = 0; j <= 15; j++) {
            g.drawRect(leftVert + (j*62), topLeftVert, rightVert + (j*62), bottomRightVert, p); //vertical
            g.drawRect(topLeftHor, leftHor + (j*62), bottomRightHor, rightHor + (j*62), p);
        }



        //TO MAKE THE HAND
        onHand(g); //draw the tile bag

        // if we don't have any state, there's nothing more to draw, so return
        if (state == null) {
            return;
        }

        // for each square that has an X or O, draw it on the appropriate
        // place on the canvas

        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                //char result =
               Tile t = state.board[row][col]; // get piece
               char letter = t.getLetter();
               drawSymbol(g, letter, col, row);
            }

        }



        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                if(state.pointKey[i][j] == state.D_WORD){

                }

                if(state.pointKey[i][j] == state.D_LETTER){

                }

                if(i == 7 && j == 7){

                }
            }
        }



    }

    public void onHand(Canvas g) {
        // update the variables that relate
        // to the dimensions of the animation surface
        updateDimensions(g);

        // paint the TTT-board's horizontal and vertical lines
        Paint p = new Paint();
        p.setColor(testColor());

        int i = 0;
        float variable1 = BORDER_PERCENT + SQUARE_SIZE_PERCENT
                + (i * SQUARE_DELTA_PERCENT);
        float variable2 = variable1 + LINE_WIDTH_PERCENT;
        float fixed1 = BORDER_PERCENT;
        float fixed2 = 100 - BORDER_PERCENT;
        float hardFixed = 103;

        float x = h(variable1);
        float y = v(fixed1);
        float x2 = h(variable2);
        float y2 = v(fixed2);

        float left = 2340;
        float topLeft = 82;
        float right = 2357;
       // float bottomRight = 1558;
        float bottomRight = 1000;




      //  g.drawRect(h(variable1), v(fixed1), h(variable2), v(fixed2), p);
        g.drawRect(left - 700, topLeft, right - 700, bottomRight, p); //vertical line 1
        g.drawRect(left + 150 - 700, topLeft, right + 150 - 700, bottomRight, p); //vertical line 2




       // g.drawRect(82, 640, 1558, 657, p);
        g.drawRect(2340, 983, 2500, 1000, p);
        float leftHor = 1640;
        float topLeftHor = 983;
        float rightHor = 1807;
        float bottomRightHor = 1000;

        for (int z = 0; z <= 7; z++) {
            g.drawRect(leftHor, topLeftHor - (z*130), rightHor, bottomRightHor - (z*130), p);

        }






        // if we don't have any state, there's nothing more to draw, so return
        if (state == null) {
            return;
        }

        // for each square that has an X or O, draw it on the appropriate
        // place on the canvas

        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                //char result =
                Tile t = state.board[row][col]; // get piece
                char letter = t.getLetter();
                drawSymbol(g, letter, col, row);
            }

        }
    }



    /**
     * update the instance variables that relate to the drawing surface
     *
     * @param g
     * 		an object that references the drawing surface
     */
    private void updateDimensions(Canvas g) {

        // initially, set the height and width to be that of the
        // drawing surface
        int width = g.getWidth();
        int height = g.getHeight();

        // Set the "full square" size to be the minimum of the height and
        // the width. Depending on which is greater, set either the
        // horizontal or vertical base to be partway across the screen,
        // so that the "playing square" is in the middle of the screen on
        // its long dimension
        if (width > height) {
            fullSquare = height;
            vBase = 0;
            hBase = (width - height) / (float) 2.0;
        } else {
            fullSquare = width;
            hBase = 0;
            vBase = (height - width) / (float) 2.0;
        }

    }

    // x- and y-percentage-coordinates for a polygon that displays the X's
    // first slash
    private static float[] xPoints1 = { 6.25f, 12.5f, 87.5f, 93.75f };
    private static float[] yPoints1 = { 12.5f, 6.25f, 93.75f, 87.5f };

    // x- and y-percentage-coordinates for a polygon that displays the X's
    // second slash
    private static float[] xPoints2 = { 87.5f, 6.25f, 93.75f, 12.5f };
    private static float[] yPoints2 = { 6.25f, 87.5f, 12.5f, 93.75f };

    /**
     * Draw a symbol (X or O) on the canvas in a particular location
     *
     * @param g
     *            the graphics object on which to draw
     * @param sym
     *            the symbol to draw (X or O)
     * @param col
     *            the column number of the square on which to draw (0, 1 or 2)
     * @param col
     *            the row number of the square on which to draw (0, 1 or 2)
     */
    protected void drawSymbol(Canvas g, char sym, int col, int row) {

        // compute the pixel-location
        float xLoc = BORDER_PERCENT + col * SQUARE_DELTA_PERCENT; // compute ...
        float yLoc = BORDER_PERCENT + row * SQUARE_DELTA_PERCENT; // ... location

        Paint text  = new Paint();
        text.setColor(Color.WHITE);
        text.setTextSize(55);


        // set the paint color to be the foreground color
        Paint p = new Paint();
        p.setColor(foregroundColor());

        String letter = String.valueOf(sym);
                g.drawText(letter, h(xLoc + 2), v(yLoc + 4), text);

    }

    /**
     * helper-method to create a scaled polygon (Path) object from a list of points
     *
     * @param xPoints
     * 		list of x-coordinates, taken as percentages
     * @param yPoints
     * 		corresponding list of y-coordinates--should have the same length as xPoints
     * @param scale
     * 		factor by which to scale them
     * @return
     */
    private Path createPoly(float[] xPoints, float[] yPoints, float scale) {

        // in case array-lengths are different, take the minimim length, to avoid
        // array-out-of-bounds errors
        int count = Math.min(xPoints.length, yPoints.length);

        // run through the points, adding each to the Path object, scaling as we go
        Path rtnVal = new Path();
        rtnVal.moveTo(xPoints[0] * scale / 100, yPoints[0] * scale / 100);
        for (int i = 1; i < count; i++) {
            float xPoint = xPoints[i] * scale / 100;
            float yPoint = yPoints[i] * scale / 100;
            rtnVal.lineTo(xPoint, yPoint);
        }

        // close the Path into a polygon; return the object
        rtnVal.close();
        return rtnVal;
    }

    /**
     * maps a point from the canvas' pixel coordinates to "square" coordinates
     *
     * @param x
     * 		the x pixel-coordinate
     * @param y
     * 		the y pixel-coordinate
     * @return
     *		a Point whose components are in the range 0-14, indicating the
     *		column and row of the corresponding square on the tic-tac-toe
     * 		board, or null if the point does not correspond to a square
     */
    public Point mapPixelToSquare(int x, int y) {

        // loop through each square and see if we get a "hit"; if so, return
        // the corresponding Point in "square" coordinates
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                float left = h(BORDER_PERCENT + (i * SQUARE_DELTA_PERCENT));
                float right = h(BORDER_PERCENT + SQUARE_SIZE_PERCENT
                        + (i * SQUARE_DELTA_PERCENT));
                float top = v(BORDER_PERCENT + (j * SQUARE_DELTA_PERCENT));
                float bottom = v(BORDER_PERCENT + SQUARE_SIZE_PERCENT
                        + (j * SQUARE_DELTA_PERCENT));
                //System.out.println(left + " " + right + " " + top + " " + bottom);
                if ((x > left) != (x > right) && (y > top) != (y > bottom)) {
                    return new Point(i, j);
                }
            }
        }

        // no match: return null
        return null;
    }

    /**
     * method to map touch on board to a specific grid on the board
     * @param Bx
     *      the X percentage that was clicked on the board
     * @param By
     *      the Y percentage that was clicked on the board
     * @return
     *      returns A point that should contain the index of the grid touched
     */

    public Point mapTouchToBoard(float Bx, float By){
        float width = (float) 0.0666666;
        float bound_X = 0;
        float bound_Y = 0;
        int point_X = 0;
        int point_Y = 0;
        for(int y = 0; y < 15; y++){
            if(bound_Y <= By && By <= (bound_Y + width)){
                point_Y = y;
                break;
            }
            else {
                bound_Y+=width;
            }

        }
        for(int x = 0; x < 15; x++){
            if(bound_X <= Bx && Bx <= (bound_X + width)){
                point_X = x;
                break;
            }
            else{
                bound_X+=width;
            }

        }
        Log.d("BOARD_GRID", point_X + ", " + point_Y);
        return new Point(point_X, point_Y);
    }

    /**
     * method to map a touch on the hand to an index in the hand
     * @param By
     *      percentage for y that was touched on the hand
     * @return
     *      index in the hand that matches with what was touched in the surface view
     */
    public int mapTouchToHand(float By){
        //width is 0.142
        float width = (float) 0.142;
        float bound_Y = 0;
        int hand_Index = 0;
        for (int i = 0; i < 7;i++){
            if(bound_Y <= By && By <= (bound_Y + width)){
                hand_Index = i;
                break;
            }
            else {
                bound_Y+=width;
            }
        }
        Log.d("HAND_GRID", hand_Index + "");
        return hand_Index;
    }

    /**
     * helper-method to convert from a percentage to a horizontal pixel location
     *
     * @param percent
     * 		the percentage across the drawing square
     * @return
     * 		the pixel location that corresponds to that percentage
     */
    private float h(float percent) {
        return hBase + percent * fullSquare / 100;
    }

    /**
     * helper-method to convert from a percentage to a vertical pixel location
     *
     * @param percent
     * 		the percentage down the drawing square
     * @return
     * 		the pixel location that corresponds to that percentage
     */
    private float v(float percent) {
        return vBase + percent * fullSquare / 100;
    }

    /**
     * helper-method to find the x percentage clicked in board
     *
     * @param x
     *      the x cordinate touched on the screen
     * @return
     *      the x percentage clicked on the board
     */
    public float windowX(float x){
        float bX = (x - xL) / (xH - xL);
        return bX;

    }


    public float reverseWindowX(float bX){
        return bX * (xH - xL) + xL;
    }

    /**
     * helper-method to find the y percentage clicked in the board
     * @param y
     *      the y coordinate touched on the screen
     * @return
     *      the y percentage clicked on the board
     */
    public float windowY(float y){
        float bY = (y - yL) / (xH - xL);
        return bY;
    }


    public float reverseWindowY(float bY){
        return bY * (yH - yL) + yL;
    }


    /**
     * helper-method to find the x percentage clicked in the hand
     * @param x
     *      the x coordinate touched on the screen
     * @return
     *      the x percentage clicked on the hand
     */
    public float handX(float x){
        float bX = (x - Hx_L) / (Hx_H - Hx_L);
        return bX;
    }



    /**
     * helper-method to find the y percentage clicked in the hand
     * @param y
     *      the y coordinate touched on the screen
     * @return
     *      the y percentage clicked on the hand
     */
    public float handY(float y){
        float bY = (y - Hy_L) / (Hy_H - Hy_L);
        return bY;
    }

    /*
        Getters for surface view
     */
    public float getxL() {
        return xL;
    }

    public float getxH() {
        return xH;
    }

    public float getyL() {
        return yL;
    }

    public float getyH() {
        return yH;
    }

    public float getHx_L() {
        return Hx_L;
    }


    public float getHy_L() {
        return Hy_L;
    }

    public float getHx_H() {
        return Hx_H;
    }

    public float getHy_H() {
        return Hy_H;
    }



}


