package dsa;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Network - Consists of vertices and edges
 *
 * @author Even A. Nilsen
 * @version 07.08.2016
 */
public class Network {
    private ArrayList<Edge> edgeList;
    private ArrayList<Vertex> vertexList;

    /**
     * Default constructor
     */
    public Network() {
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();
    }

    /**
     * Adds a vertex to the network if it is not already in the network
     * @param deviceType The vertex's device type
     * @param componentName The vertex's component name
     */
    public void addVertex(char deviceType, String componentName) {
        if (findVertex(componentName) == null) {
            Vertex v = new Vertex(deviceType, componentName);
            vertexList.add(v);
        }
        else {
            System.out.println("Vertex is already in the system");
        }
    }

    /**
     * Finds and returns a vertex using component name
     * @param componentName What to search for
     * @return vertex
     */
    protected Vertex findVertex(String componentName) {
        Iterator<Vertex> it = vertexList.iterator();
        boolean found = false;
        Vertex vertex = null;

        while (it.hasNext() && !found) {
            vertex = it.next();
            String cN = vertex.getComponentName();

            if (cN.equals(componentName)) {
                found = true;
            }
        }

        if (found) {
            return vertex;
        }
        else {
            return null;
        }
    }
}
