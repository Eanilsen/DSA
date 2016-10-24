import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.InputStreamReader;

public class DSAStack implements Iterable<String> {
    private String[] items;
    private int size = 0;

    public DSAStack() {
        items = new String[2];
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Resize the underlying array holding the elements
	 * @param capacity usage...
     *
     **/
    public void resize(int capacity) {
        assert capacity >= size;
        String[] temp = new String[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    /**
     * Push a new item onto the stack
	 * @param item usage...
     *
     **/
    public void push(String item) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        items[size++] = item;
    }

    /**
     * Delete and return the item most recently added
     *
	 * @return object
     **/
    public String pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        String item = items[size-1];
        items[size-1] = null;
        size--;

        if (size > 0 && size == items.length/4) {
            resize(items.length/2);
        }
        return item;
    }

    public Iterator<String> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<String> {
        private int i = size-1;

        public boolean hasNext() {
            return i >= 0;
        }

        /**
         * Short Description
         *
         **/
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        /**
         * 
         *
	 * @return object
         **/
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[i--];
        }
    }

    public static void main(String[] args) {
        InputStreamReader in = new InputStreamReader(System.in);
        DSAStack stack = new DSAStack();
        while (in.isEmpty()) {
            String item = in.readString();
            if (!item.equals("-")) {
                stack.push(item);
            }
            else if (!stack.isEmpty()) {
                System.out.print(stack.pop() + " ");
            }
        }
        System.out.println("(" + stack.getSize() + " left on stack)");
    }
}
