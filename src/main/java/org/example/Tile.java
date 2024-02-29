package org.example;

import java.util.ArrayList;

public class Tile {

    char displayChar = '█';
    boolean hasBomb = false;
    boolean isDug = false;
    boolean hasFlag = false;
    boolean isFlaggedCorrect = (hasBomb == hasFlag);
    int posX = 0;
    int posY = 0;
    int adjBomb = 0;
    int gridXMax = 0;
    int gridYMax = 0;


    Tile(int x, int y, int gridXMax, int gridYMax) {
        char displayChar = '█';
        boolean hasBomb = false;
        boolean isDug = false;
        boolean hasFlag = false;
        boolean isFlaggedCorrect = (hasBomb == hasFlag);
        this.gridXMax = gridXMax;
        this.gridYMax = gridYMax;
        int posX = x;
        int posY = y;
        int adjBomb = 0;
    }



    public static void main(String[] args){
    }
    public void dig() {
        char empty = '░';
        if (!isDug) {
            if (!hasFlag) {
                isDug = true;
                if (hasBomb) {
                    System.out.println("Game over");
                    // TODO: make this end the game
                } else {
                    adjBomb = getAdjBomb();
                    if (adjBomb == 0) {
                        reveal();
                        displayChar = empty; //
                    }
                    else{
                        displayChar = '0';
                        displayChar += adjBomb;
                    }
                }
            }
            System.out.println("Can't dig flag!");
        }
        System.out.println("Tile Already Dug");

    }
    private static int getAdjMin(int x){
        if (x == 0) {return 0;}
        else {return x-1;}
    }
    public static int getAdjMax(int position, int gridVMax){
        if (position == gridVMax) { return gridVMax;}
        else {return position+1;}
    }
    public int getAdjBomb(ArrayList<ArrayList<Tile>> grid) { //
        int xMin = getAdjMin(posX);
        int xMax = getAdjMax(posX, gridXMax);
        int yMin = getAdjMin(posY);
        int yMax = getAdjMax(posY, gridYMax);
        int bombCount = 0;
        for (int x = xMin; x < xMax; x++) {//TODO: Check this arithmatic
            for (int y = yMin; y < yMax; y++) {
                if (grid.get(x).get(y).hasBomb) {
                    bombCount++;
                }
            }
        }
        return bombCount;
    }

    public ArrayList<ArrayList<Tile>> reveal(ArrayList<ArrayList<Tile>> grid){
        //TODO this is for a reveal where
        int xMin = getAdjMin(posX);
        int xMax = getAdjMax(posX, gridXMax);
        int yMin = getAdjMin(posY);
        int yMax = getAdjMax(posY, gridYMax);
        for (int x = xMin; x < xMax; x++) {//TODO: Check this arithmatic
            for (int y = yMin; y<yMax; y++){
                grid.get(x).get(y).dig();
            }
        }
        return grid;

    }

}