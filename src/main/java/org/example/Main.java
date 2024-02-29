package org.example;

import java.util.ArrayList;

// TODO: Most of project. Most the base object is there but needs filling.
// TODO: Game Logic
// TODO: Count bombs
// TODO: Add bombs to the whole map
// TODO: Remove  this
// TODO: Count Flags on whole map
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    int gridXMax = 1;
    int gridYMax = 1;
    char mine = '¤';
    char flag = '¶';
    char empty = '░';
    char undug = '█';
    char flagWrong = '╔';

    ArrayList<Tile> col = new ArrayList<>(); // Columns are lists of tiles
    ArrayList<ArrayList<Tile>> grid = new ArrayList<>(); // A grid is rows of columns of tiles


    public static void main(String[] args) {// Main running loop, make this run good
        int gridXMax = 4;
        int gridYMax = 5;
        // These chars are effectively the sprites for the game grid
        char mine = '¤';
        char flag = '¶';
        char empty = '░';
        char undug = '█';
        char flagWrong = '╔';
        //TODO: A list called Grid that is made of tiles between 0 and gridMax should be built
        ArrayList<ArrayList<Tile>> grid = constructGrid(gridXMax , gridYMax);
        printGrid(grid);
        //System.out.println(grid.get(1).get(1).displayChar);
    }

    public static ArrayList<ArrayList<Tile>> constructGrid(int gridXMax, int gridYMax) {
        // Grids are made of y rows of x width
        ArrayList<ArrayList<Tile>> grid = new ArrayList<>();
        ArrayList<Tile> row = new ArrayList<>();

        for (int y = 0; y < gridYMax; y++) {
            for (int x = 0; x < gridXMax; x++) {
                row.add(new Tile(x,y, gridXMax, gridYMax));
            }
            grid.add(row);
            row = new ArrayList<>();
        }
        return grid;
    }

    public static void printGrid(ArrayList<ArrayList<Tile>> grid) {
        // Printing a list is made of rows. Send all the rows to a buffer and print them one at a time
        String buffer = "";
        for (ArrayList<Tile> Row : grid) {
            for (Tile tile : Row) {
                buffer += tile.displayChar;
            }
            System.out.println(buffer);
            buffer = "";
        }
    }
    /*public static class Tile{
        char displayChar = '█';
        public static void main(String[] args) {
            char displayChar = '█';
        }
    }*/
    public int getAdjBomb(Tile tile) { //
        int posX = tile.posX;
        int posY = tile.posY;

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
    private static int getAdjMin(int position){
        if (position == 0) {return 0;}
        else {return position-1;}
    }
    public static int getAdjMax(int position, int gridVMax){
        if (position == gridVMax) { return gridVMax;}
        else {return position+1;}
    }

    public void reveal(Tile tile){
        //TODO this is for a reveal where
        int posX = tile.posX;
        int posY = tile.posY;
        ArrayList<Tile> strip = new ArrayList<>();

        int xMin = getAdjMin(posX);
        int xMax = getAdjMax(posX, gridXMax);
        int yMin = getAdjMin(posY);
        int yMax = getAdjMax(posY, gridYMax);
        for (int x = xMin; x < xMax; x++) {//TODO: Check this arithmatic
            for (int y = yMin; y<yMax; y++){
                strip = grid.get(y);
                strip.set(x,dig(tile));
            }
        }

    }
    public Tile dig(Tile tile) {

        char empty = '░';
        if (!tile.isDug) {
            if (!tile.hasFlag) {
                tile.isDug = true;
                if (tile.hasBomb) {
                    System.out.println("Game over");
                    // TODO: make this end the game
                } else {
                    int adjBomb = getAdjBomb(tile);
                    if (adjBomb == 0) {
                        reveal(tile);
                        tile.displayChar = empty; //
                    }
                    else{
                        tile.displayChar = '0';
                        tile.displayChar += adjBomb;
                    }
                }
            }
            System.out.println("Can't dig flag!");
        }
        System.out.println("Tile Already Dug");

        return tile;

    }
}