package dsa;

/**
 * Vertex
 *
 * @author Even A. Nilsen
 * @version 06.08.2016
 */

public class Vertex
{
    public static final char DEVICETYPE_RV325 = 'R';
    public static final char DEVICETYPE_2921K9 = '2';

    private char deviceType;
    private String componentName;
    private float reliability;

    /**
     * Alternate constructor
     * @param deviceType Name of the device
     * @param componentName Name of the component
     */
    public Vertex(char deviceType, String componentName) {
        if ((deviceType != Vertex.DEVICETYPE_RV325) &&
                (deviceType != Vertex.DEVICETYPE_2921K9)) {
            throw new IllegalArgumentException("Invalid device type provided");
        }

        setComponentName(componentName);
        setReliability(0.9f);
        this.deviceType = deviceType;
    }

    /**
     * Returns the device type
     * @return deviceType
     */
    public char getDeviceType() {
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
     * Returns the reliability represented by a number between 0 and 1
     * @return reliability
     */
    public float getReliability() {
        return reliability;
    }

    /**
     * Validates component name and assigns it a value
     * @param componentName The name of the component
     */
    public void setComponentName(String componentName) {
        if ((componentName == null))
            throw new IllegalArgumentException("Name must not be empty");
        this.componentName = componentName;
    }

    /**
     * Checks if reliability is between 0.0 and 1.0
     * @param reliability The degree of reliability
     */
    public void setReliability(float reliability) {
        if ((reliability >= 0.0f) && (reliability <= 1.0f))
            this.reliability = reliability;
        else
            throw new IllegalArgumentException("Must be a value between 0.0 and 1.0");
    }
}
