/**
 * FloatingPoint.java - ADT used for safe handling of floating point numbers.
 *
 * @author Even A. Nilsen
 * @version v0.1
 */
public class FloatingPoint {
    private static double precision;

    /**
     * Default constructor
     * @param value Initial value of precision
     */
    public FloatingPoint(double value) {
        precision = value;
    }

    /**
     * Copy constructor
     * @param num FloatingPoint object to copy
     */
    public FloatingPoint(FloatingPoint num) {
        precision = num.precision;
    }

    /**
     * Checks if two FloatingPoint numbers are within precision of each other
	 * @param num Number to compare to the current
     *
	 * @return equal
     **/
    public boolean equals(FloatingPoint num) {
        boolean equal = false;

        return equal;
    }

    /**
     * Sets the precision to a new value
	 * @param value usage...
     *
     **/
    public static void setPrecision(double value) {
        if (value < 0.0) {
            System.out.println("Negative numbers are not accepted.");
            throw new IllegalArgumentException();
        }
        else {
            precision = value;
        }
    }

    /**
     * Adds given FloatingPoint number to the current
	 * @param num 
     *
     **/
    public void add(FloatingPoint num) {
        try {
            precision += num.precision;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public double getPrecision() {
        return precision;
    }
}
