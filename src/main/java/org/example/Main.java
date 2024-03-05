package org.example;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static boolean digMode = true;
    static int tilesRevealed = 0;
    static int tilesHidden = 0;
    static int bombCount = 0;
    static int flagCount = 0;
    static int flagCountCorrect = 0;
    static int gridXMax = 1;
    static int gridYMax = 1;
    static final char MINE = '¤';
    static final char FLAG = '¶';
    static final char EMPTY = '░';
    static final  char UNDUG = '█';
    static final char FLAG_WRONG = '╔';
    static boolean isGameOver = false;

    static ArrayList<Tile> col = new ArrayList<>(); // Columns are lists of tiles
    static ArrayList<ArrayList<Tile>> grid = new ArrayList<>(); // A grid is rows of columns of tiles

    static int tileCount = 0;
    static int bombPercent = 100;
    static boolean firstDig = true;
    static int sizeMax = 32;


    public static void main(String[] args) {// Main running loop, make this run good
        while(true) {
            System.out.println("Input Grid Size");
            System.out.println("X:");
            gridXMax = getInputInt(sizeMax);
            System.out.println("Y:");
            gridYMax = getInputInt(sizeMax);

            if (gridXMax >= 1 || gridYMax >= 1){
                break;
            }
            else {
                System.out.printf("Grid size invalid. Please input value between 1 and %d for each dimension.", sizeMax);
            }
        }

        tileCount = gridXMax * gridYMax;
        tilesHidden = tileCount;

        // On different difficulties, have different % of bombs.
        bombPercent = -1; // normal 10% difficulty
        do {
            System.out.println("""
                    Difficulty (% tiles that are bombs):
                    Easy (10)
                    Medium (15)
                    Hard (20)""");
            bombPercent = getInputInt(100);
        } while  (bombPercent < 0 || bombPercent > 100);

        // These chars are effectively the sprites for the game grid
        char flagWrong = '╔';
        grid = constructGrid(gridXMax, gridYMax);
        printGrid(false);
        addBombs(grid);

        Scanner scan = new Scanner(System.in);
        System.out.println(bombCount);


        int x = -1;
        int y = -1;
        int mode = -1;

        while (true) {
            System.out.println();
            // Digs where asked

            System.out.print("\033[H\033[2J");
            System.out.flush();


            System.out.printf("%d bomb(s), %d tile(s), %d flag(s)\n", bombCount, tilesHidden, flagCount);
            printGrid(false);
            while (true) {
                System.out.println("""
                Mode:
                - 1) Flag
                - 2) Dig:
                """);
                mode = getInputInt(2);
                if (mode == 1 || mode == 2) {
                    break;
                }
            }
            while (true) {
                System.out.println("Input X (-1 to go back):");
                x = getInputInt(gridXMax - 1);
                if (x == -1) {
                    mode = -1;
                    break;
                }
                System.out.println("Input Y (-1 to go back):");
                y = getInputInt(gridYMax - 1);
                if (y == -1) {
                    mode = -1;
                    break;
                }
                if (x >= 0 && y >= 0) {
                    break;
                }
            }


            if (mode == 2) {
                dig(grid.get(y).get(x), false, grid);
                firstDig = false;
                if (isGameOver) {
                    printGrid(true);
                    break;
                }
                if (tilesHidden == bombCount || (flagCountCorrect == bombCount && flagCount == flagCountCorrect)) {
                    System.out.println("Victory!");
                    printGrid(true);
                    break;
                }
            } else if (mode == 1) {
                toggleFlag(grid.get(y).get(x));
                if (flagCountCorrect == bombCount) {
                    System.out.println("Victory!");
                    printGrid(true);
                    break;
                }
            }
        }
        //System.out.println(grid.get(1).get(1).displayChar);
    }

    public static void toggleFlag(Tile tile) {
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
            tile.displayChar = (tile.hasFlag) ? FLAG : UNDUG;
            tile.isFlaggedCorrect = tile.hasFlag == tile.hasBomb;

            strip.set(x, tile);
            grid.set(y, strip);
        } else {
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

    public static void addBombs(ArrayList<ArrayList<Tile>> grid) {
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < gridYMax; i++) {
            ArrayList<Tile> strip = grid.get(i);
            for (int j = 0; j < gridXMax; j++) {
                Tile tile = strip.get(j);
                if (rand.nextInt(100) <= bombPercent) {
                    bombCount++;
                    tile.hasBomb = true;
                    strip.set(j, tile);
                }
                grid.set(i, strip);
            }
        }
    }

    public static void printGrid(boolean isEndGame) {
        // Printing a list is made of rows. Send all the rows to a buffer and print them one at a time
        String buffer = "\t";
        int iSkip = 0;
        for (int i = 0; i < gridXMax; i++) {
            if (i != 0){
                buffer += " ";
            }
            if (i % 5 == 0) {
                buffer += i;
            } else if (((i + 1) % 5 == 0) && i >= 10 && i <= 100) {
                buffer += ++i;
                buffer += " ";
            } else if ((i + 2) % 5 == 0 && i >= 100 && i <= 1000) {
                buffer += (i + 2);
                i += 2;
                buffer += "  ";
            } else {
                buffer += " ";
            }
        }

        buffer += '\n';
        for (ArrayList<Tile> Row : grid) {
            if (Row.get(0).posY % 5 == 0) {
                buffer += Row.get(0).posY;
            }
            buffer += "\t";
            for (Tile tile : Row) {
                if (isEndGame) {
                    if (tile.hasFlag && tile.hasBomb) {
                        tile.displayChar = tile.flag;
                    } else if (tile.hasFlag && !tile.hasBomb) {
                        tile.displayChar = tile.flagWrong;
                    } else if (tile.hasBomb) {
                        tile.displayChar = tile.mine;
                    }

                }
                buffer += getColor(tile.displayChar) + tile.displayChar + getColor('0');
                buffer += ' ';
            }
            if (Row.get(0).posY % 5 == 0)
            {
                buffer += "\t" + Row.get(0).posY;
            }
            buffer += '\n';
        }
        buffer += '\t';
        for (int i = 0; i < gridXMax; i++) {
            if (i != 0){
                buffer += " ";
            }
            if (i % 5 == 0) {
                buffer += i;
            } else if (((i + 1) % 5 == 0) && i >= 10 && i <= 100) {
                buffer += ++i;
                buffer += " ";
            } else if ((i + 2) % 5 == 0 && i >= 100 && i <= 1000) {
                buffer += (i + 2);
                i += 2;
                buffer += "  ";
            } else {
                buffer += " ";
            }
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
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                if (grid.get(y).get(x).hasBomb) {
                    bombCount++;
                }
            }
        }
        return bombCount;
    }

    public static void reveal(Tile tile) {
        int posX = tile.posX;
        int posY = tile.posY;
        ArrayList<Tile> strip = new ArrayList<>();

        int xMin = Math.max(posX - 1, 0);
        int xMax = Math.min(posX + 1, gridXMax - 1);
        int yMin = Math.max(posY - 1, 0);
        int yMax = Math.min(posY + 1, gridYMax - 1);
        for (int y = yMin; y <= yMax; y++) {
            //printGrid(grid);
            for (int x = xMin; x <= xMax; x++) {
                strip = grid.get(y);
                strip.set(x, dig(grid.get(y).get(x), true, grid));

            }
        }

    }


    public static Tile dig(Tile tile, boolean isRipple, ArrayList<ArrayList<Tile>> grid) {
        if (!tile.isDug) {
            if (!tile.hasFlag) {
                if (tile.hasBomb) {
                    if (firstDig){
                        firstDig = false;
                        tile.hasBomb = false;
                        bombCount--;
                        return dig(tile, true, grid);
                    }
                    System.out.println("Game over");
                    isGameOver = true;
                    tile.displayChar = MINE;
                    return tile;
                } else {
                    tile.isDug = true;
                    int adjBomb = getAdjBomb(tile);
                    if (adjBomb == 0) {
                        reveal(tile);
                        tile.displayChar = EMPTY; //
                    } else {
                        tile.displayChar = '0';
                        tile.displayChar += adjBomb;
                    }
                    tilesHidden--;

                }
            } else if (!isRipple) {
                System.out.println("Careful, that's a flag!");
            }
        } else if (!isRipple) {
            System.out.println("Tile Already Dug!");
        }
        return tile;
    }

    public static int getInputInt(int max) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            if (scan.hasNextInt()) {
                int val = scan.nextInt();
                if (val >= -1 && val <= max){
                    return val;
                } else {
                    System.out.println("Invalid Value, try again:");
                }
            }
        }
    }

    public static String getColor(char character){
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLACK = "\u001B[30m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_WHITE = "\u001B[37m";

        final String RED_BACKGROUND = "\u001B[41m";
        final String PURPLE_BACKGROUND = "\u001B[45m";
        final String WHITE_BACKGROUND	= "\u001B[47m";

        switch(character){
            case '1':
                return ANSI_BLUE;
            case '2':
                return ANSI_GREEN;
            case '3':
                return ANSI_RED;
            case '4':
                return ANSI_PURPLE;
            case '5':
                return ANSI_CYAN;
            case '6':
                return ANSI_WHITE + RED_BACKGROUND;
            case '7':
                return ANSI_WHITE + PURPLE_BACKGROUND;
            case '8':
                return ANSI_BLACK + WHITE_BACKGROUND;
            case MINE:
                return ANSI_CYAN;
            case FLAG:
                return ANSI_GREEN;
            case FLAG_WRONG:
                return ANSI_RED;
            default:
                return ANSI_RESET;
        }
    }
}