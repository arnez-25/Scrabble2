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
public class ScrabbleSwapAction extends GameAction {

    private Tile t;//tile to be swapped

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ScrabbleSwapAction(GamePlayer player, Tile toSwap) {
        super(player);
        t = toSwap;
    }

    //Getter to receive info for Tile
    public Tile getTile(){
        return t;
    }
}
