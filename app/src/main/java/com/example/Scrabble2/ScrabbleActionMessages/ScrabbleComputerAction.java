package com.example.Scrabble2.ScrabbleActionMessages;

import android.graphics.Point;

import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.Scrabble2.infoMessage.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScrabbleComputerAction extends GameAction {
    private ArrayList<Tile> tilesToPlace;
    private ArrayList<Point> tilePoints;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ScrabbleComputerAction(GamePlayer player, ArrayList<Tile> tiles, ArrayList<Point> tilePositions) {
        super(player);
        tilesToPlace = tiles;
        tilePoints = tilePositions;
    }

    public ArrayList<Tile> getTilesToPlace() {
        return tilesToPlace;
    }

    public ArrayList<Point> getTilePoints() {
        return tilePoints;
    }
}
