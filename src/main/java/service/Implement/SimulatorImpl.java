package service.Implement;

import model.Tile;
import service.ActorMachine;
import service.Simulator;


import javax.inject.Inject;
import java.util.ArrayList;


public class SimulatorImpl implements Simulator {

    public static final int WHITE_TILE = 0;
    public static final int BLACK_TILE = 1;
    public static final int AGENT_TILE = 2;

    private String errCode = null;

    @Inject
    private ActorMachine actor;

    public void run(int steps) {

        actor.reset();

        for (int i = 0; i < steps; i++) {
            try {
                actor.act();

            } catch (OutOfMemoryError e) {
                System.out.println("System out of memory");
                actor.reset();
                this.errCode = "OutOfMemorySim";
                break;
            }
        }
    }

    public int[] getBoard() {
        return actor.getGrid().getGridBounds();
    }

    public int[][] getArrayOutput() {

        if (this.errCode != null)
            return null;

        return SerializeGrid();
    }

    public String getStringOutput() {

        int[][] finalBoard = SerializeGrid();

        //check if there is a server error and propagate if there is
        if (finalBoard == null || this.errCode != null)
            return null;

        ArrayList<String> bf = new ArrayList<>();

        //convert finalBoard to String
        for (int[] row : finalBoard) {
            StringBuffer str = new StringBuffer();
            for (int col : row) {
                str.append(col);
            }
            bf.add(str.toString());
        }


        return String.join("\n", bf);

    }

    public int[][] SerializeGrid() {

        int[] bbox= actor.getActorGridBounds();
        int xmin = bbox[0];
        int ymin = bbox[1];

        int xmax = bbox[2];
        int ymax = bbox[3];

        int[][] finalBoard;

        try {
            finalBoard = new int[ymax - ymin + 1][xmax - xmin + 1];
        } catch (OutOfMemoryError e) {
            System.out.println("GC ran out of memory");
            return null;
        }

        for (int i = xmin; i < xmax + 1; i++) {
            for (int j = ymin; j < ymax + 1; j++) {

                //the minimum is subtracted from y to convert it to positive number that starts from 0
                //this will create y indexing where the bottom index of the chessboard is the lowest
                //thus this needs to be reversed in order to correctly store it in an array
                //To reverse indexing, the range is subtracted from the maximum possible value
                int[] rows = finalBoard[(ymax - ymin) - (j - ymin)];
                if (actor.getGrid().getBlackTiles().contains(new Tile(i, j))) {
                    //x shifted so that it is real number, starting from zero
                    rows[i - xmin] = BLACK_TILE;
                } else {
                    rows[i - xmin] = WHITE_TILE;
                }

                //place the actor, overwriting current color
                if (new Tile(i, j).equals(actor.getCurrentTile())) {
                    rows[i - xmin] = AGENT_TILE;
                }
            }

        }

        return finalBoard;

    }

}
