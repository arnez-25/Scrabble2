package com.example.Scrabble2;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;

public class ScrabblePlaceAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ScrabblePlaceAction(GamePlayer player) {
        super(player);
    }
}
