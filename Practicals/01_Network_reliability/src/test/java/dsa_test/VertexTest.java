package dsa_test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import src.main.java.dsa.*;

/**
 * VertexTest - Vertex unit tests
 *
 * @author Even A. Nilsen
 * @version 08.08.2016
 */
public class VertexTest {

    Vertex vertex1 = new Vertex('R', "Router");

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        System.out.println("Vertex constructor");
        Vertex vertex2 = new Vertex('Z', "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetComponentName() {
        System.out.println("Vertex component Name");
        vertex1.setComponentName(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetReliability() {
        System.out.println("Vertex reliability");
        vertex1.setReliability(-1.0f);
    }
}
