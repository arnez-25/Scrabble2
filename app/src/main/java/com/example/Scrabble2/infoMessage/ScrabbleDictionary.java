package com.example.Scrabble2.infoMessage;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class that implements a scrabble dictionary as a search tree
 * 
 * @author Riley Cameron
 * @version 3/6/2023
 * 
 * sources: https://www.cs.cmu.edu/afs/cs/academic/class/15451-s06/www/lectures/scrabble.pdf 
 */

//node class for the dictionary tree, contains a character and a list of child nodes
//if a node is the last letter in a playable word, isWord is set to true
class Node {
    char ch;
    boolean isWord;
    HashMap<Character,Node> childNodes;

    public Node(char ch, boolean isWord) {
        this.ch = ch;
        this.isWord = isWord;
        this.childNodes = new HashMap<Character,Node>();
    }

    //Getter methods
    public boolean getIsWord() {
        return isWord;
    }

    public char getChar() {
        return ch;
    }

    public Node getChild(char c) {
        return childNodes.get(c);//if node doesn't exist, null is returned
    }
    //Adds the child to the nodes
    public void addChild(char c, boolean end) {
        childNodes.put(c, new Node(c,end));//adds a node with given char to the hashmap
    }
}

public class ScrabbleDictionary {
    Node root = null;
    BufferedReader reader;

    public ScrabbleDictionary(BufferedReader r) {

        root = new Node('0', false);

        reader = r;
        try {
            String line = reader.readLine();
            while (line != null) {
                this.addWord(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    //Adds words
    public void addWord(String word) {
        int strLen = word.length();
        Node currentNode = root;
        boolean wordEnd = false;

        for (int i = 0; i < strLen; i++) {
            if (i == strLen-1) {wordEnd = true;}

            if (currentNode.getChild(word.charAt(i)) != null) {
                currentNode = currentNode.getChild(word.charAt(i));
            } else {
                currentNode.addChild(word.charAt(i), wordEnd);
                currentNode = currentNode.getChild(word.charAt(i));
            }
        }

    }
    //Checks if word exists
    public boolean checkWord(String word) {
        int strLen = word.length();
        Node currentNode = root;

        for (int i = 0; i < strLen; i++) {
            if (currentNode.getChild(word.charAt(i)) == null) {
                Log.d("DICT", word + " is not a legal word");
                return false;
            } else {
                currentNode = currentNode.getChild(word.charAt(i));

                if (currentNode.getIsWord() && i == strLen-1) {
                    Log.d("DICT", word + " is a legal word");
                    return true;
                }
            }
        }
        Log.d("DICT", word + " is not a legal word");
        return false;
    }
}

