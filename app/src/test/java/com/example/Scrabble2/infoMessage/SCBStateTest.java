package com.example.Scrabble2.infoMessage;

import static org.junit.Assert.*;

import android.view.ContextThemeWrapper;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SCBStateTest extends ContextThemeWrapper {

    SCBState state;

    @Before
    public void setUp() {
        state = new SCBState(new BufferedReader(new InputStreamReader(new ByteArrayInputStream("AAA".getBytes(StandardCharsets.UTF_8)))));
    }

    @Test
    public void getWhoseMove() {
        state.playerToMove = 0;
        int whoseMove = state.getWhoseMove();
        assertEquals(0, whoseMove);
    }

    @Test
    public void calculateScore() {
        Tile t = new Tile('l');
        int score = t.getScore();
        assertEquals(0, score);
    }

    @Test
    public void skipper() {
        int player = state.getWhoseMove();
        assertTrue(state.skipper(player));
    }
}