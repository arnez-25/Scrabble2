package com.example.Scrabble2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.Scrabble2.infoMessage.SCBState;
import com.example.Scrabble2.infoMessage.Tile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SkipperBugTest {
    SCBState state;

    @Before
    public void setup() {
        String words = "AAA";
        state = new SCBState(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(words.getBytes(StandardCharsets.UTF_8)))));
    }
    @Test
    public void skipperIssue() {
        //initialize test hands
        ArrayList<Tile> p1Hand = new ArrayList<>();
        ArrayList<Tile> p2Hand = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            p1Hand.add(new Tile('A'));
            p2Hand.add(new Tile('A'));
        }

        //set both player hands to all 'A's
        state.player1Tiles = p1Hand;
        state.player2Tiles = p2Hand;

        //p1 plays tiles:
        state.placeTile(0, p1Hand.get(0), 7, 7);
        state.placeTile(0, p1Hand.get(0), 7, 8);
        state.placeTile(0, p1Hand.get(0), 7, 9);

        //p1 skips:
        state.skipper(0);

        //check if tiles went back into player's hand:
        assertEquals(7, state.player1Tiles.size());
    }
}