package service;

import model.Direction;
import model.Grid;
import model.Tile;


public interface  ActorMachine {



    /* This method will move the actor in direction by calculating the new tile
    * adding it to the grid updates the current tile
    *
    *  */
     void move(Direction directon);

    //computes new direction and moves forward
     void act();

    //gets the next direction and
    //swaps the color of the current tile
    Direction getMovement(Direction direction);

    //resets the state of the machine to origin
     void reset();

     //gets the gridBounds by alsoincluding the agent position
    int[] getActorGridBounds();

    Grid getGrid();

    //gets the current location of the agent
    Tile getCurrentTile();

}
