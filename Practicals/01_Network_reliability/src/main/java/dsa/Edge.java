package dsa;

import java.util.ArrayList;
/**
 * Edge - Connects two vertices in a network
 *
 * @author Even A. Nilsen
 * @version 06.08.2016
 */

public class Edge
{
    private String deviceType;
    private String componentName;
    private float reliability;
    private ArrayList<Vertex> vertexConnection;

    /**
     * Alternate constructor
     * @param deviceType The type of the device
     * @param componentName The name of the component
     */
    public Edge(String deviceType, String componentName) {
        setComponentName(componentName);
        setDeviceType(deviceType);
        setReliability(0.9f);
        vertexConnection = new ArrayList<Vertex>();
    }

    /**
     * Returns the device type
     * @return deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Returns the component name
     * @return componentName
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * Returns the reliability factor
     * @return reliability
     */
    public float getReliability() {
        return reliability;
    }

    /**
     * Returns a list of two vertices
     * @return vertexConnection
     */
    public ArrayList<Vertex> getVertexConnection() {
        return vertexConnection;
    }

    /**
     * Validates the component name by checking if it is blank and assigns it a value
     * @param componentName The name of the component
     */
    public void setComponentName(String componentName) {
        if ((componentName == null) || (componentName.equals("")))
            throw new IllegalArgumentException("Component name must not be blank");
        this.componentName = componentName;
    }

    /**
     * Validates the device type by checking if it is blank and assigns it a value
     * @param deviceType The type of the device
     */
    public void setDeviceType(String deviceType) {
        if ((deviceType == null) || (deviceType.equals("")))
            throw new IllegalArgumentException("Device type must not be blank");
        this.deviceType = deviceType;
    }

    /**
     * Checks if the reliability is a number between 0.0 and 1.0
     * @param reliability The reliability factor
     */
    public void setReliability(float reliability) {
        if ((reliability >= 0.0f) && (reliability <= 1.0f))
            this.reliability = reliability;
        else
            throw new IllegalArgumentException("Must be a value between 0.0 and 1.0");
    }

    /**
     * Creates a connection between to vertices if vertexConnection is empty
     * @param nodeA First vertex in connection
     * @param nodeB Second vertex in connection
     */
    public void createConnection(Vertex nodeA, Vertex nodeB) {
        if (vertexConnection.isEmpty()) {
            vertexConnection.add(nodeA);
            vertexConnection.add(nodeB);
            System.out.println("Connection established between " + nodeA.getComponentName() + " and "
                    + nodeB.getComponentName());
        }
        else {
            System.out.println("A connection has already been made.");
        }
    }
}
