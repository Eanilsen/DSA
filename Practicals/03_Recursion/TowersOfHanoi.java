/**
 * TowersOfHanoi - Implementation of the Towers of Hanoi algorithm
 *
 * @author Even A. Nilsen
 * @version 17.08.2016
 *
 **/

public class TowersOfHanoi {


    /**
     * Indents line
	 * @param lvl usage...
     *
     **/
    public void indent(int lvl) {
        if (lvl > 0) {
            String s = "    ";
            System.out.print(s);
            indent(lvl-1);
        }
    }

    /**
     * Moves disks from source peg to destination peg
	 * @param src source peg
	 * @param dest destination peg
     *
     **/
    public void moveDisk(int src, int dest) {
        System.out.println("Moving top disk from peg " + src + " to peg " + dest);
    }

    /**
     * Simulates a run through of Towers of Hanoi using recursion
     * @param n amount of disks
     * @param src source peg
     * @param dest destination peg
     *
     **/
    public void towers(int n, int src, int dest, int lvl) {
        int tmp;

        indent(lvl);
        System.out.println("towers(" + n + ", " + src + ", " + dest + ")");
        if (n == 1) {
            indent(lvl);
            moveDisk(src, dest);
        }
        else {
            tmp = 6 - src - dest;

            indent(lvl);
            System.out.println("n=" + n + ", src=" + src + ", dest=" + dest +
                    ", temp=" + tmp);

            towers(n-1, src, tmp, lvl+1);

            indent(lvl);
            moveDisk(src, dest);
            towers(n-1, tmp, dest, lvl+1);
        }
    }
}
