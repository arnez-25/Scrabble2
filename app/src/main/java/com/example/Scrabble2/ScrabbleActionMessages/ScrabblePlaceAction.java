package com.example.Scrabble2.ScrabbleActionMessages;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.Scrabble2.infoMessage.Tile;

/**
 * @author Riley Cameron
 * @author Alexx Blake
 * @author Nick Tabra
 * @author Jacob Arnez
 * @author David Leon
 *
 * @Version 4/5/2023
 */
public class ScrabblePlaceAction extends GameAction {

    //Variables for the Place Action Class
    private Tile tile;
    private int row;
    private int col;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ScrabblePlaceAction(GamePlayer player, Tile t, int row, int col) {
        super(player);

        this.tile = t;
      //  this.row = row;
       // this.col = col;
        this.row = Math.max(0, Math.min(15, row));
        this.col = Math.max(0, Math.min(15, col));
    }


    //Getters for information for placements.
    public Tile getTile() {
        return tile;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
