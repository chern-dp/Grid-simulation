package model;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;


@RunWith(Arquillian.class)
public class GridTest {
    @Inject
    private Tile tile;

    @Inject
    private Grid grid;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(Grid.class, Tile.class,Color.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setUp() throws Exception{


    }

    @Test
    public void addTile() {
        tile.setX(0);
        tile.setY(0);

        Color color= grid.addTile(tile);



        //check if the tile was added to the grid
        Assert.assertEquals("The tile was not added successfully", grid.getBlackTiles().size() , 1);

        //check the color of the tile
        Assert.assertEquals("The color must be black", color , Color.WHITE);

        //add the same tile. Since it is the same, it should be deleted from the grid (inverted to white)
        color = grid.addTile(tile);
        Assert.assertEquals("The color of the tile was not flipped", grid.getBlackTiles().size() , 0);

        //check the color of the tile
        Assert.assertEquals("The color must be WHITE", color , Color.BLACK);

        //the same test, but with more tiles
        Tile tile5 = new Tile(-3, -3);
        Tile tile6 = new Tile(-4, 3);
        Tile tile7 = new Tile(3, 0);
        Tile tile8 =  new Tile(-3, -3);

        grid.addTile(tile5);
        grid.addTile(tile6);
        grid.addTile(tile7);
        grid.addTile(tile8);

        Assert.assertEquals("The color of the tile was not flipped", grid.getBlackTiles().size() , 2);



    }

    @Test
    /* This test checks if the grid bounng box is correctly
     computed in a grid with randomly added black tiles
     */
    public void getGridBounds() {

        Grid testGrid = new Grid();

        Assert.assertArrayEquals("The Bounding box is not correct for no tiles",  testGrid.getGridBounds(), new int[]{0,0,0,0});

        Tile tile1 = new Tile(0, 0);
        Tile tile2 = new Tile(1, -1);

        testGrid.addTile(tile1);

        Assert.assertArrayEquals("The Bounding box is not correct for 1 Tile",  testGrid.getGridBounds(), new int[]{0, 0, 0, 0});

        testGrid.addTile(tile2);
        Assert.assertArrayEquals("The Bounding box is not correct for 2 diagonal Tiles",  testGrid.getGridBounds(), new int[]{0, -1, 1, 0});

        Tile tile3 = new Tile(0, -1);
        Tile tile4 = new Tile(1, 0);

        testGrid.addTile(tile3);
        testGrid.addTile(tile4);
        Assert.assertArrayEquals("The Bounding box is not correct for 4 tiles",  testGrid.getGridBounds(), new int[]{0, -1, 1, 0});


        Tile tile5 = new Tile(-3, -3);
        Tile tile6 = new Tile(-4, 3);
        Tile tile7 = new Tile(3, 0);

        testGrid.addTile(tile5);
        testGrid.addTile(tile6);
        testGrid.addTile(tile7);
        Assert.assertArrayEquals("The Bounding box is not correct for 7 tiles",  testGrid.getGridBounds(), new int[]{-4, -3, 3, 3});

    }
}
