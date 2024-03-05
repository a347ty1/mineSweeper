import org.example.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.example.Main.*;


public class TileTests {

    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_BLACK = "\u001B[30m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_BLUE = "\u001B[34m";
    final String ANSI_PURPLE = "\u001B[35m";
    final String ANSI_CYAN = "\u001B[36m";
    final String ANSI_WHITE = "\u001B[37m";

    final String BLACK_BACKGROUND = "\u001B[40m";
    final String RED_BACKGROUND = "\u001B[41m";
    final String GREEN_BACKGROUND = "\u001B[42m";
    final String YELLOW_BACKGROUND = "\u001B[43m";
    final String BLUE_BACKGROUND = "\u001B[44m";
    final String PURPLE_BACKGROUND = "\u001B[45m";
    final String CYAN_BACKGROUND	= "\u001B[46m";
    final String WHITE_BACKGROUND	= "\u001B[47m";

    static final char MINE = '¤';
    static final char FLAG = '¶';
    static final char EMPTY = '░';
    static final  char UNDUG = '█';
    static final char FLAG_WRONG = '╔';

    static int gridXMax = 10;
    static int gridYMax = 10;
    static int testBombCount = 0;
    ArrayList<ArrayList<Tile>> grid = constructGrid(gridXMax,gridYMax);
    @Test
    public void main(){

        // Involved here are groups of tests to check certain parts of the game generation.
        // Each group contains a few tests to cover each function possible.
        colorTest();
        gridTests();
        bombTests();

    }

    @Test
    public void colorTest(){
        Assertions.assertEquals(getColor(MINE), (ANSI_CYAN), "Wrong Colour returned from colour Test of: MINE");
        Assertions.assertEquals(getColor('1'), (ANSI_BLUE), "Wrong Colour returned from colour Test of: 1");
        Assertions.assertEquals(getColor('8'), (ANSI_BLACK + WHITE_BACKGROUND), "Wrong Colour returned from colour Test of: 8");
        Assertions.assertNotEquals(getColor('4'), '4', "Wrong Colour returned from colour Test of: 4");
    }

    @Test
    public void gridTests() {
        Assertions.assertEquals(grid.size(), 10, "Grid Y wrong size");
        Assertions.assertEquals(grid.get(0).size(), 10, "Grid X wrong size");
    }
    @Test
    public void bombTests() {
        Assertions.assertFalse(grid.get(0).get(0).hasBomb);
        Assertions.assertEquals(getAdjBomb(grid.get(5).get(5)), 0, "Bomb detected, shouldn't be there yet.");
        Assertions.assertEquals(grid.get(0).get(0).displayChar, '█', "Generated wrong character");

        for (ArrayList<Tile> Row : grid) {
            for (Tile tile : Row) {
                Assertions.assertFalse(tile.hasBomb, "Bomb found before added");
                if (tile.hasBomb) {
                    testBombCount++;
                }
            }
        }

        //Assertions.assertTrue(testBombCount == 0, "Bomb appeared somewhere before generation");
        addBombs(grid);

        for (ArrayList<Tile> Row : grid) {
            for (Tile tile : Row) {
                if (tile.hasBomb) {
                    testBombCount++;
                }
            }
        }
        Assertions.assertNotEquals(0, testBombCount, "No Bombs after Generation");

        dig(grid.get(5).get(5), false, grid);
        Assertions.assertNotEquals('█', grid.get(5).get(5).displayChar, "Tile digging Failed");

    }
}
