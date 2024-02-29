package org.example;

import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

// TODO: Most of project. Most the base object is there but needs filling.
// TODO: Game Logic
// TODO: Count bombs
// TODO: Add bombs to the whole map
// TODO: Remove  this
// TODO: Count Flags on whole map
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static boolean digMode = true;
    static int tilesRevealed = 0;
    static int tilesHidden = 0;
    static int bombCount = 0;
    static int flagCount = 0;
    static int flagCountCorrect = 0;
    static int gridXMax = 1;
    static int gridYMax = 1;
    static char mine = '¤';
    static char flag = '¶';
    static char empty = '░';
    static char undug = '█';
    static char flagWrong = '╔';
    static boolean isGameOver = false;

    static ArrayList<Tile> col = new ArrayList<>(); // Columns are lists of tiles
    static ArrayList<ArrayList<Tile>> grid = new ArrayList<>(); // A grid is rows of columns of tiles

    static int tileCount = 0;
    static int bombPercent = 50;


    public static void main(String[] args) {// Main running loop, make this run good
        //TODO: Custom size
        gridXMax = 4;
        gridYMax = 4;
        tileCount = gridXMax * gridYMax;
        tilesHidden = tileCount;

        // On different difficulties, have different % of bombs.
        //TODO: Custom Difficulty
        bombPercent = 10; // normal 10% difficulty
        // These chars are effectively the sprites for the game grid
        char mine = '¤';
        char flag = '¶';
        char empty = '░';
        char undug = '█';
        char flagWrong = '╔';
        //TODO: A list called Grid that is made of tiles between 0 and gridMax should be built
        grid = constructGrid(gridXMax, gridYMax);
        printGrid();
        addBombs();

        Scanner scan = new Scanner(System.in);
        System.out.println(bombCount);


        // A test puts a bomb in the first tile
        /*Tile bombTile = grid.get(0).get(0);
        bombTile.hasBomb = true;
        ArrayList<Tile> slip = grid.getFirst();
        slip.set(0, bombTile);
        grid.set(0, slip);
        */

        for (;;) {
            System.out.println();
            System.out.printf("%d bombs, %d tiles, %d flags\n", bombCount, tilesHidden, flagCount);
            printGrid();
            // Digs where asked
            System.out.println("Mode 1) Flag - 2) Dig:"); //TODO: input protection
            int mode = scan.nextInt();
            System.out.println("Input X:");
            int x = scan.nextInt();
            System.out.println("Input Y:");
            int y = scan.nextInt();

            if (mode == 2) {
                dig(grid.get(y).get(x), false);
                if (isGameOver) {
                    printGrid();
                    break;
                }
                if (tilesHidden == bombCount || flagCountCorrect == bombCount) {
                    System.out.println("Victory!");
                    printGrid();
                    break;
                }
            }
            else if (mode == 1) {
                toggleFlag(grid.get(y).get(x));
                if (flagCountCorrect == bombCount){ //TODO: Fix weird flagging behaviour
                    System.out.println("Victory!");
                    printGrid();
                    break;
                }
            }

            //System.out.println(grid.get(1).get(1).displayChar);
        }
    }
    public static void toggleFlag(Tile tile){
        if (!tile.isDug) {
            int x = tile.posX;
            int y = tile.posY;
            ArrayList<Tile> strip = grid.get(y);

            if (tile.hasFlag) {
                flagCount--;
                if (tile.hasBomb) {
                    flagCountCorrect--;
                }
            } else {
                flagCount++;
                if (tile.hasBomb) {
                    flagCountCorrect++;
                }
            }
            tile.hasFlag = !tile.hasFlag;
            tile.displayChar = (tile.hasFlag) ? flag : undug;
            tile.isFlaggedCorrect = tile.hasFlag == tile.hasBomb;

            strip.set(y,tile);
            grid.set(x,strip);
        }
        else{
            System.out.println("Tile dug already");
        }

    }

    public static ArrayList<ArrayList<Tile>> constructGrid(int gridXMax, int gridYMax) {
        // Grids are made of y rows of x width
        ArrayList<ArrayList<Tile>> grid = new ArrayList<>();
        ArrayList<Tile> row = new ArrayList<>();

        for (int y = 0; y < gridYMax; y++) {
            for (int x = 0; x < gridXMax; x++) {
                row.add(new Tile(x, y, gridXMax, gridYMax));
            }
            grid.add(row);
            row = new ArrayList<>();
        }
        return grid;
    }

    public static void  addBombs() {
        Random rand = new Random();
        for (int i = 0; i < gridYMax; i++) {
            ArrayList<Tile> strip = grid.get(i);
            for (int j = 0; j < gridXMax; j++) {
                Tile tile = strip.get(j);
                if (rand.nextInt(100) <= bombPercent) {
                    bombCount++;
                    tile.hasBomb = true;
                    strip.set(j,tile);
                }
                grid.set(i,strip);
            }
        }
    }

    public static void printGrid() {
        // Printing a list is made of rows. Send all the rows to a buffer and print them one at a time
        String buffer = "";
        for (ArrayList<Tile> Row : grid) {
            for (Tile tile : Row) {
                buffer += tile.displayChar;
                buffer += ' ';
            }
            buffer += '\n';
        }
        System.out.println(buffer);
    }


    public static int getAdjBomb(Tile tile) { //
        int posX = tile.posX;
        int posY = tile.posY;

        int xMin = Math.max(posX - 1, 0);
        int xMax = Math.min(posX + 1, gridXMax - 1);
        int yMin = Math.max(posY - 1, 0);
        int yMax = Math.min(posY + 1, gridYMax - 1);
        int bombCount = 0;
        for (int x = xMin; x <= xMax; x++) {//TODO: Check this maths
            for (int y = yMin; y <= yMax; y++) {
                if (grid.get(y).get(x).hasBomb) {
                    bombCount++;
                }
            }
        }
        return bombCount;
    }

    public static void reveal(Tile tile) {
        //TODO this is for a reveal where there's no adjacent bombs
        int posX = tile.posX;
        int posY = tile.posY;
        ArrayList<Tile> strip = new ArrayList<>();

        int xMin = Math.max(posX - 1, 0);
        int xMax = Math.min(posX + 1, gridXMax - 1);
        int yMin = Math.max(posY - 1, 0);
        int yMax = Math.min(posY + 1, gridYMax - 1);
        for (int y = yMin; y <= yMax; y++) {//TODO: Check this arithmatic
            //printGrid(grid);
            for (int x = xMin; x <= xMax; x++) {
                strip = grid.get(y);
                strip.set(x, dig(grid.get(y).get(x), true));

            }
        }

    }


    public static Tile dig(Tile tile, boolean isRipple) {
        if (!tile.isDug) {
            if (!tile.hasFlag) {
                tile.isDug = true;
                if (tile.hasBomb) {
                    System.out.println("Game over");
                    isGameOver = true;
                    tile.displayChar = mine;
                    return tile;
                    // TODO: make this end the game
                } else {
                    int adjBomb = getAdjBomb(tile);
                    if (adjBomb == 0) {
                        reveal(tile);
                        tile.displayChar = empty; //
                        tilesHidden--;
                    } else {
                        tile.displayChar = '0';
                        tile.displayChar += adjBomb;
                        tilesHidden--;
                    }

                }
            } else if (!isRipple) {
                System.out.println("Careful, that's a flag!");
            }
        } else if (!isRipple) {
            System.out.println("Tile Already Dug!");
        }
        return tile;
    }
}