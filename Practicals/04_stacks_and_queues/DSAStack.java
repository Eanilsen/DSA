/**
 * DSAStack - More to come
 *
 * @author  Even A. Nilsen
 * @version 24.08.2016
 **/

public class DSAStack {
    private Object[] stack;
    private int count;
    private static final int DEFAULT_CAPACITY = 100; 
    
    public DSAStack() {
        this.stack = new Object[DEFAULT_CAPACITY];
        this.count = 0;
    }

    public DSAStack(int maxCapacity) {
        this.stack = new Object[maxCapacity];
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    /**
     * Checks if the stack is empty.
     * Returns true if empty and false if not empty
     *
	 * @return object
     **/
    public boolean isEmpty() {
        return false;
    }
}
