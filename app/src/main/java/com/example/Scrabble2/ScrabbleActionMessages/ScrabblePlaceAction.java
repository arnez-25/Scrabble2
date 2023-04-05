package com.example.Scrabble2.ScrabbleActionMessages;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.Scrabble2.infoMessage.Tile;

public class ScrabblePlaceAction extends GameAction {

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
        this.row = row;
        this.col = col;
    }


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
