package service;

import model.Grid;
import model.Tile;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import service.Implement.ActorMachineImpl;
import service.Implement.SimulatorImpl;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class SimulatorImplTest {

    @Inject
    private SimulatorImpl sim;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(SimulatorImpl.class,  Grid.class, ActorMachineImpl.class, Tile.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void run() {

        sim.run(3);

        int[]  chessboard = sim.getBoard();
        Assert.assertArrayEquals("The generated chessboard is inncorect 3 steps", chessboard, new int[]{-1, -1, 0, 0});


        sim.run(4);
        chessboard = sim.getBoard();

        Assert.assertArrayEquals("The generated chessboard is inncorect 4 steps", chessboard, new int[]{-1, -1, 0, 0});


        sim.run(6);
        chessboard = sim.getBoard();

        Assert.assertArrayEquals("The generated chessboard is inncorect 4steps", chessboard, new int[]{-1, -1, 0, 1});


        sim.run(12);
        chessboard = sim.getBoard();

        Assert.assertArrayEquals("The generated chessboard is inncorect 12steps", chessboard,  new int[]{-1, -1, 1, 2});

    }

    @Test
    public void getOutput(){
        sim.run(3);
        Assert.assertEquals("The serialized chessboard is inncorect 3 steps", sim.getStringOutput(), "21\n11");

        sim.run(6);
        Assert.assertEquals("The serialized chessboard is inncorect 6 steps", sim.getStringOutput(), "012\n100\n110");

        sim.run(12);
        Assert.assertEquals("The serialized chessboard is inncorect 12 steps", sim.getStringOutput(), "120\n101\n111\n110");

        sim.run(10000);
    }
}
