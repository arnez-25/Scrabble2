package com.example.Scrabble2.ScrabbleActionMessages;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;

public class ScrabbleResetAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ScrabbleResetAction(GamePlayer player) {
        super(player);
    }
}
