package service;

public interface Simulator {

    //runs the simulation for steps
    void run(int steps);

    //serialized the final chessboard into a string
    String getStringOutput();

    //get the grid into an array form
    int[][] getArrayOutput();

    //get the bounds of the grid
    int[] getBoard();





}
