import org.junit.Test;

import static org.junit.Assert.*;

public class GrassBlockTest {

    @Test
    public void getColour() {
        GrassBlock grassBlock = new GrassBlock();

        assertEquals(grassBlock.getColour(),"green");
    }

    @Test
    public void getBlockType() {
        GrassBlock grassBlock = new GrassBlock();

        assertEquals(grassBlock.getBlockType(),"grass");
    }

    @Test
    public void isCarryable() {
        GrassBlock grassBlock = new GrassBlock();

        assertEquals(grassBlock.isCarryable(),false);
    }

    @Test
    public void isMoveable() {
        GrassBlock grassBlock = new GrassBlock();
        assertEquals(grassBlock.isMoveable(),false);

    }

    @Test
    public void isDiggable() {
        GrassBlock grassBlock = new GrassBlock();
        assertEquals(grassBlock.isDiggable(),true);
    }
}