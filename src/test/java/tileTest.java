import org.example.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.example.Main.*;
import static org.example.Tile.*;


public class tileTest {
    static int gridXMax = 10;
    static int gridYMax = 10;
    static int testBombCount = 0;
    ArrayList<ArrayList<Tile>> grid = constructGrid(gridXMax,gridYMax);
    @Test
    public void main(){

        // Involved here are groups of tests to check certain parts of the game generation.
        // Each group contains a few tests to cover each function possible.
        gridTests();
        bombTests();

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

        Assertions.assertTrue(testBombCount == 0, "Bomb appeared somewhere before generation");
        addBombs(grid);

        for (ArrayList<Tile> Row : grid) {
            for (Tile tile : Row) {
                if (tile.hasBomb) {
                    testBombCount++;
                }
            }
        }

        Assertions.assertFalse(testBombCount == 0, "No Bombs after Generation");
        dig(grid.get(5).get(5), false, grid);
        Assertions.assertFalse(grid.get(5).get(5).displayChar == '█', "Tile digging Failed");

    }
}
