/**
 * TowersOfHanoi - Implementation of the Towers of Hanoi algorithm
 *
 * @author Even A. Nilsen
 * @version 17.08.2016
 *
 **/
import java.util.ArrayList;

public class TowersOfHanoi {
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
     * Simulates a runthrough of Towers of Hanoi using recursion
     * @param n amount of disks
     * @param src source peg
     * @param dest destination peg
     *
     **/
    public void towers(int n, int src, int dest) {
        int tmp;

        System.out.println("towers(" + n + ", " + src + ", " + dest + ")");
        if (n == 1) {
            moveDisk(src, dest);
        }
        else {
            tmp = 6 - src - dest;

            System.out.println("n=" + n + ", src=" + src + ", dest=" + dest +
                    ", temp=" + tmp);

            towers(n-1, src, tmp);

            moveDisk(src, dest);
            towers(n-1, tmp, dest);
        }
    }
}
