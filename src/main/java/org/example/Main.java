package org.example;

import java.util.ArrayList;

// TODO: Most of project. Most the base object is there but needs filling.
// TODO: Game Logic
// TODO: Count bombs
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
    ArrayList<ArrayList<Tile>>  grid = new ArrayList<>(); // A grid is rows of columns of tiles


    public static void main(String[] args) {
        int gridXMax = 1;
        int gridYMax = 1;
        // These chars are effectively the sprites for the game grid
        char mine = '¤';
        char flag = '¶';
        char empty = '░';
        char undug = '█';
        char flagWrong = '╔';
        //TODO: A list called Grid that is made of tiles between 0 and gridMax should be built

        }

    public ArrayList<ArrayList<Tile>> constructGrid(int gridXMax, int gridYMax){
        ArrayList<ArrayList<Tile>> grid = new ArrayList<>(gridXMax);
        ArrayList<Tile> row = new ArrayList <>(gridYMax);

        for (int y = 0 ; y < gridYMax ; y++){
            for (int x = 0; x < gridYMax ; x++){
             row.set(x, new Tile());
            }
            grid.add(y,row);
        }
        return grid;
    }

    public class Tile {
        char displayChar = '█';
        boolean hasBomb = false;
        boolean isDug = false;
        boolean hasFlag = false;
        boolean isFlaggedCorrect = (hasBomb == hasFlag);
        int posX = 1;
        int posY = 1;
        int adjBomb = 0;



        public static void main(String[] args){
            char displayChar = '█';
            boolean hasBomb = false;
            boolean hasAdjacentBomb = false;
            boolean isDug = false;
            boolean hasFlag = false;
            boolean isFlaggedCorrect = (hasBomb == hasFlag);
            int posX = 1;
            int posY = 1;
            int adjBomb = 0;
        }
        public void dig() {
            if (!isDug) {
                if (!hasFlag) {
                    isDug = true;
                    if (hasBomb) {
                        System.out.println("Game over");
                        // TODO: make this end the game
                    } else {
                        adjBomb = getAdjBomb();
                        if (adjBomb == 0) {
                            this.reveal();
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

        public void checkFlag(){
            isFlaggedCorrect = (hasBomb == hasFlag);
        }
        private int getAdjMin(int x){
            if (x == 0) {return 0;}
            else {return x-1;}
        }
        private int getAdjMax(int position, int gridVMax){
            if (position == gridVMax) { return gridVMax;}
            else {return position+1;}
        }
        public int getAdjBomb() {
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

        public void reveal(){
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

        }

    }
}