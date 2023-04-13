package com.example.Scrabble2.infoMessage;

/**
 * @author Riley Cameron
 * @author Alexx Blake
 * @author Nick Tabra
 * @author Jacob Arnez
 * @author David Leon
 *
 * @Version 3/16/2023
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Class Tile
 *
 * Is the tile class used to represent the letter tiles in the game.
 */

public class Tile {

    /**
     * Private Variable for tile class include:
     *
     * letter - to represent the letter of the tile using the Character Object
     * onBoard - Boolean variable to check if the tile is onBoard in a valid position when the player end their turn
     * score - represents the score of the tiles letter using the Integer Object
     */
    //Establishes the Score Variable
    private Integer score;
    //Establishes variable for letter
    private Character letter;

    //Retrieves letter when called
    public Character getLetter() {
        return letter;
    }

    //Checks value of the onBoard variable
    public boolean isOnBoard() {
        return onBoard;
    }
    //Sets the onBoard variable to the object's onBoard
    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }
    //Establishes the onBoard variable
    private boolean onBoard;
    //Gets the current score
    public Integer getScore() {
        return score;
    }
    //Sets the current score when updated
    public void setScore(Integer score) {
        this.score = score;
    }

    public void drawMe(Canvas g, float x, float y, float size) {
        if (letter == ' ')return;
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        g.drawRoundRect(x, y, x+size, y+size, size/10, size/10, paint);

        Paint text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(size/2);
        text.setTextAlign(Paint.Align.CENTER);
        g.drawText(letter.toString(), x+size/2, y+size/2, text);

        text.setTextSize(size/5);
        g.drawText(score.toString(), x+size/2, y+size-size/4, text);
    }

    //Configures the tile based on the character
    public Tile(Character l){
        letter = l;
        onBoard = false;
        score = 0;
    }

    /**
     * Deep copy constructor.
     *
     * @param t the tile object in use
     */
    public Tile(Tile t){
        letter = t.getLetter();
        score = t.getScore();
        onBoard = t.isOnBoard();
    }

}
