package dsa_test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import src.main.java.dsa.*;

/**
 * EdgeTest - Edge unit tests
 *
 * @author Even A. Nilsen
 * @version 08.08.2016
 */
public class EdgeTest {
    Edge edge = new Edge("CAT5", "Cable");

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        System.out.println("Edge constructor");
        Edge faultyEdge = new Edge("","");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetComponentName() {
        System.out.println("Edge component name");
        edge.setComponentName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetReliability() {
        System.out.println("Edge reliability");
        edge.setReliability(1.1f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDeviceType() {
        System.out.println("Edge device type");
        edge.setDeviceType(null);
    }

    @Test
    public void testGetComponentName() {
        assertEquals("Cable", edge.getComponentName());
    }

    @Test
    public void testConnection() {
        System.out.println("Edge connection");
        Vertex nodeA = new Vertex('R', "Router");
        Vertex nodeB = new Vertex('2', "Computer");
        edge.createConnection(nodeA, nodeB);
        assertEquals("Router", edge.getVertexConnection().get(0).getComponentName());
        assertEquals("Computer", edge.getVertexConnection().get(1).getComponentName());
    }

}
