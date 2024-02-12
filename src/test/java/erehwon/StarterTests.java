package erehwon;
import java.awt.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class StarterTests {
    @Test
    public void testCorrectCreation() {
        RedBlueGrid rbGrid = new RedBlueGrid(10, 1, 0.3, 0.4, 0.35);
        // complete the test by verifying that the grid has the correct number
        // of vacant, red, and blue cells
    }
    //@Test
    public void testSetColor() {
        RedBlueGrid rbGrid = new RedBlueGrid(10, 1, 0.3, 0.4, 0.35);
        // complete the test by setting a cell's colour and verifying that
        // the colour was correctly changed
    }
    @Test
    public void simpleTest(){
        RedBlueGrid rbGrid = new RedBlueGrid(5,1,0.10,0.5,0.25);
        rbGrid.simulate(20);
        assertEquals(rbGrid.fractionHappy(), 1.0);
    }
    @Test
    public void shiftColorTest(){
        RedBlueGrid rbGrid = new RedBlueGrid(5,1,0.1,5,0.25);
        rbGrid.setColor(1,1, Color.WHITE);
        rbGrid.shiftColor(1,1);
        assertEquals(rbGrid.getColor(1,1), Color.RED);
        rbGrid.shiftColor(1,1);
        assertEquals(rbGrid.getColor(1,1), Color.BLUE);
        rbGrid.shiftColor(1,1);
        assertEquals(rbGrid.getColor(1,1), Color.WHITE);
        rbGrid.reset(0.2,0.4,0.3);
    }
    @Test
    public void getColorTest(){
        RedBlueGrid rbGrid = new RedBlueGrid(5,1,0.1,0.5,0.25);
        assertEquals(rbGrid.getColor(10,10), null);
    }
    @Test
    public void setColorTest(){
        RedBlueGrid rbGrid = new RedBlueGrid(5,1,0.1,0.5,0.25);
        assertEquals(rbGrid.setColor(10,10,Color.WHITE), false);
        assertEquals(rbGrid.setColor(1,1,Color.RED), true);
        assertEquals(rbGrid.getColor(1,1), Color.RED);
        assertThrows(IllegalArgumentException.class, ()-> rbGrid.setColor(1,1, Color.YELLOW));
    }
    @Test
    public void isHappyTestTrue(){
        RedBlueGrid rbGrid= new RedBlueGrid(3,1,0.1,0.4,0.05);
        rbGrid.setColor(0,1, Color.WHITE);
        rbGrid.setColor(0,0,Color.BLUE);
        rbGrid.setColor(1,1,Color.RED);
        rbGrid.setColor(2,0,Color.RED);
        rbGrid.setColor(2,1,Color.RED);
        rbGrid.setColor(2,2,Color.RED);
        assertEquals(rbGrid.isHappy(1,1), true);
    }
    @Test
    public void isHappyTestFalse(){
        RedBlueGrid rbGrid= new RedBlueGrid(3,1,0.1,0.4,0.9);
        rbGrid.setColor(0,1, Color.WHITE);
        rbGrid.setColor(0,0,Color.BLUE);
        rbGrid.setColor(1,1,Color.RED);
        rbGrid.setColor(2,0,Color.BLUE);
        rbGrid.setColor(2,1,Color.BLUE);
        rbGrid.setColor(2,2,Color.BLUE);
        assertEquals(rbGrid.isHappy(1,1), false);
    }
    @Test
    public void redsTest(){
        RedBlueGrid rbGrid = new RedBlueGrid(15,2,0.1,0.5,0.25);
        rbGrid.reset(0.1,0.5,0.25);
        rbGrid.oneTimeStep();
    }
    @Test
    public void vacantsTest(){
        RedBlueGrid rbGrid = new RedBlueGrid(15,2,0.8,0.2,0.25);
        rbGrid.reset(0.8,0.2,0.05);
        rbGrid.oneTimeStep();
    }
    @Test
    public void setColorsBranchCoverageTest(){
        RedBlueGrid rbGrid = new RedBlueGrid(5,1,0.5,0.5,0.25);
        assertFalse(rbGrid.setColor(5,4,Color.WHITE));
        assertFalse(rbGrid.setColor(4,5,Color.WHITE));
        assertFalse(rbGrid.setColor(-1,4,Color.WHITE));
        assertFalse(rbGrid.setColor(4,-1,Color.WHITE));
    }
    @Test
    public void getColorsBranchCoverageTest(){
        RedBlueGrid rbGrid = new RedBlueGrid(5,1,0.5,0.5,0.25);
        assertEquals(rbGrid.getColor(5,4), null);
        assertEquals(rbGrid.getColor(4,5), null);
        assertEquals(rbGrid.getColor(-1,4), null);
        assertEquals(rbGrid.getColor(4,-1), null);
    }

    @Test
    public void efficientTimeStepTest(){

        RedBlueGrid rbGrid = new RedBlueGrid(20,1,0.2,0.5,0.25);
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.reset(0.2,0.5,0.25);
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        rbGrid.efficientTimeStep();
        assertEquals(rbGrid.fractionHappy(),0.95,0.06);
    }
    @Test
    public void GroupTimeStepTest(){
        RedBlueGrid rbGrid = new RedBlueGrid(20,1,0.2,0.5,0.25);
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.reset(0.2,0.5,0.25);
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        rbGrid.oneGroupStep();
        assertEquals(rbGrid.fractionHappy(),0.95,0.06);
    }
    @Test
    public void simulateTest(){
        RedBlueGrid rbGrid = new RedBlueGrid(20,1,0.2,0.5,0.25);
        rbGrid.simulate(40);
        assertEquals(rbGrid.fractionHappy(),0.95,0.06);
    }
}