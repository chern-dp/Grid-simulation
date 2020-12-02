package model;

import lombok.Data;

import javax.enterprise.inject.Model;


@Data
public class Tile
{
    private int x, y;
    private Color color;
    public Tile(){}

    public Tile(int x, int y){
        this.x = x;
        this.y = y;
    }

}
