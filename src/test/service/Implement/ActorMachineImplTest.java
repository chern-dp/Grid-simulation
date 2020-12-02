package service.Implement;

import model.Direction;
import model.Grid;
import model.Tile;
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
public class ActorMachineImplTest {

    @Inject
    private ActorMachineImpl actor;

    @Inject
    private Tile tile;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(ActorMachineImpl.class, Tile.class, Grid.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void move() {


        Assert.assertEquals("The actor is not initiallized at 0,0", actor.getCurrentTile(), new Tile(0, 0));

        actor.move(Direction.DOWN);
        Assert.assertEquals("The actor does not move as expected DOWN", actor.getCurrentTile(), new Tile(0, -1));

        actor.move(Direction.LEFT);
        Assert.assertEquals("The actor does not move as expected to LEFT", actor.getCurrentTile(), new Tile(-1, -1));

        actor.move(Direction.RIGHT);
        Assert.assertEquals("The actor does not move as expected to RIGHT", actor.getCurrentTile(), new Tile(0, -1));


        actor.move(Direction.UP);
        Assert.assertEquals("The actor does not move as expected to UP", actor.getCurrentTile(), new Tile(0, 0));

    }

    @Test
    public void getMovement() {

        // add a tile. The current background of the tile must be white
        // and thus rotate clockwise

        tile.setX(0);
        tile.setY(1);

        Direction dir = actor.getMovement(Direction.RIGHT);
        Assert.assertEquals(dir, Direction.DOWN);


        //add the same tile. Since it was added above, the color must be black
        // and thus rotate anticlockwise

        tile.setX(0);
        tile.setY(1);

        dir = actor.getMovement(Direction.DOWN);
        Assert.assertEquals(dir, Direction.RIGHT);

    }
}
