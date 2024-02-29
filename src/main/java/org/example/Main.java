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
        int gridXMax = 1;
        int gridYMax = 1;
        // These chars are effectively the sprites for the game grid
        char mine = '¤';
        char flag = '¶';
        char empty = '░';
        char undug = '█';
        char flagWrong = '╔';
        //TODO: A list called Grid that is made of tiles between 0 and gridMax should be built
        ArrayList<ArrayList<Tile>> grid = constructGrid(gridXMax, gridYMax);
        System.out.println(grid);
        printGrid(grid);
    }

    public static ArrayList<ArrayList<Tile>> constructGrid(int gridXMax, int gridYMax) {
        // Grids are made of y rows of x width
        ArrayList<ArrayList<Tile>> grid = new ArrayList<>(gridXMax);
        ArrayList<Tile> row = new ArrayList<>(gridYMax);

        for (int y = 0; y < gridYMax; y++) {
            for (int x = 0; x < gridYMax; x++) {
                row.set(x, new Tile());
            }
            grid.add(y, row);
        }
        return grid;
    }

    public static void printGrid(ArrayList<ArrayList<Tile>> grid) {
        String buffer = "";
        for (ArrayList<Tile> Row : grid) {
            for (Tile tile : Row) {
                buffer += Row;
                ;
            }
            System.out.println(buffer);
            buffer = "";
        }
    }


}