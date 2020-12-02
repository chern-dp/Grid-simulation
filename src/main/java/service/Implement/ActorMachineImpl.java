package service.Implement;

import lombok.Getter;
import model.*;
import service.ActorMachine;

import javax.inject.Inject;


public class ActorMachineImpl implements ActorMachine {

    @Getter
    @Inject
    private Tile currentTile;

    @Inject
    @Getter
    private Grid grid;

    private Direction currentDirection;


    public ActorMachineImpl() {
    }

    public void reset(){

        currentTile.setX(0);
        currentTile.setY(0);

        currentDirection = Direction.RIGHT;
        grid.clearTiles();
    }

    private Direction moveClockwise(Direction direction) {
        switch (direction) {
            case DOWN:
                return Direction.LEFT;
            case LEFT:
                return Direction.UP;
            case UP:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.DOWN;
        }
        return null;
    }

    private Direction moveAntiClockwise(Direction direction) {
        switch (direction) {
            case DOWN:
                return Direction.RIGHT;
            case LEFT:
                return Direction.DOWN;
            case UP:
                return Direction.LEFT;
            case RIGHT:
                return Direction.UP;
        }
        return null;
    }

    //computation the direction the agent needs to move next
    public Direction getMovement(Direction direction){

        //Initial rotation if currentDirection is null
        if(direction == null)
            direction = Direction.RIGHT;

        //get the color -> grid contains only blacks
        // if the tile is in the grid, the it is black, if it is not then it is white
        Color currentColor = this.grid.addTile(new Tile(currentTile.getX(), currentTile.getY()));


        if (currentColor.equals(Color.WHITE)){
            return  moveClockwise(direction);
        } else
           return  moveAntiClockwise(direction);

    }

    public void act(){

            Direction direction = getMovement(currentDirection);
            move(direction);
            currentDirection = direction;



    }
    @Override
    public void move(Direction direction) {

        switch (direction) {
            case LEFT:
                this.currentTile.setX(this.currentTile.getX() - 1);
                break;
            case UP:
                this.currentTile.setY(this.currentTile.getY() + 1);
                break;
            case RIGHT:
                this.currentTile.setX(this.currentTile.getX() + 1);
                break;
            case DOWN:
                this.currentTile.setY(this.currentTile.getY() - 1);
                break;
        }

    }

    public int[] getActorGridBounds(){

        //get tile bounds excluding the agent
        int[] bbox = this.grid.getGridBounds();


        //get the latest location of the agent and include the agent into tile bounds
        return new int[]{Math.min(bbox[0], currentTile.getX()),
                Math.min(bbox[1], currentTile.getY()),
                Math.max(bbox[2], currentTile.getX()),
                Math.max(bbox[3], currentTile.getY())};


    }




}
