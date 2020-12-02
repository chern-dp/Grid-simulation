package model;

import lombok.Getter;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class Grid {
    @Getter
    Set<Tile> blackTiles;

    public Grid(){
        blackTiles  = new HashSet<>();
    }

    public Color addTile (Tile newTile){

        if(blackTiles.contains(newTile)){
            blackTiles.remove(newTile);
            return Color.BLACK;
        }
        else
            blackTiles.add(newTile);
            return Color.WHITE;
    }
    public void clearTiles(){ blackTiles  = new HashSet<>();}

    public int[] getGridBounds(){

        //if empty return only one tile
        if(blackTiles.isEmpty())
            return new int[]{0,0,0,0};

        int ymin = blackTiles.stream()
                .mapToInt(Tile::getY)
                .min()
                .orElseThrow(NoSuchElementException::new);

        int ymax = blackTiles.stream()
                .mapToInt(Tile::getY)
                .max()
                .orElseThrow(NoSuchElementException::new);

        int xmax = blackTiles.stream()
                .mapToInt(Tile::getX)
                .max()
                .orElseThrow(NoSuchElementException::new);

        int xmin = blackTiles.stream()
                .mapToInt(Tile::getX)
                .min()
                .orElseThrow(NoSuchElementException::new);


      return new int[]{xmin, ymin, xmax, ymax};

    }
}
