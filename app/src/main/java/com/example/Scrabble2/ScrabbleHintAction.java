package com.example.Scrabble2;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;

public class ScrabbleHintAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ScrabbleHintAction(GamePlayer player) {
        super(player);
    }
}
