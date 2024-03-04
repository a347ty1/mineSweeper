package org.example;

import java.util.ArrayList;

public class Tile {
    char mine = '¤';
    char flag = '¶';
    char empty = '░';
    char undug = '█';
    char flagWrong = '╔';

    public char displayChar = '█';
    public boolean hasBomb = false;
    boolean isDug = false;
    boolean hasFlag = false;
    boolean isFlaggedCorrect = (hasBomb == hasFlag);
    int posX = 0;
    int posY = 0;
    int adjBomb = 0;
    int gridXMax = 0;
    int gridYMax = 0;
    boolean isExploded = false;


    Tile(int x, int y, int gridXMaxx, int gridYMaxx) {
        displayChar = undug;
        hasBomb = false;
        isDug = false;
        hasFlag = false;
        isFlaggedCorrect = (hasBomb == hasFlag);
        gridXMax = gridXMaxx;
        gridYMax = gridYMaxx;
        posX = x;
        posY = y;
        adjBomb = 0;
    }

    public void toggleFlag() {
        if (!isDug) {
            if (hasFlag) {
                hasFlag = false;
                displayChar = undug;
            } else {
                hasFlag = true;
                displayChar = flag;
            }
        }
    }

    public void checkFlag() {
        if (hasFlag) {
            isFlaggedCorrect = hasBomb;
        }
    }


    public static void main(String[] args) {
    }
}