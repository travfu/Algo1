/* *****************************************************************************
 *  Name: Percolation
 *  Date: 29 December 2019
 *  Description: https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] openSites;
    private WeightedQuickUnionUF grid;
    private int N;
    private int virtualTop;
    private int virtualBot;

    // creates n-by-n grid, with all sites initially blocked
    // grid also includes a virtual top and bot site, which all top and bottom
    // rows are connected to via a union.
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }

        int gridSize = n * n + 2; // +2 for virtual top and bot
        openSites = new boolean[gridSize];
        grid = new WeightedQuickUnionUF(gridSize);
        virtualTop = 0;
        virtualBot = gridSize - 1;
        N = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateCoord(row, col);
        int i = getFlatMatrixIndex(row, col);
        openSites[i] = true;
    }

    private void validateCoord(int row, int col) {
        if (row <= 0 || row > N) {
            throw new IllegalArgumentException("row is outside range");
        }
        if (col <= 0 || col > N) {
            throw new IllegalArgumentException("col is outside range");
        }
    }

    private int getFlatMatrixIndex(int row, int col) {
        // Converts a 2-D matrix coord into a 1-D array index
        // Note: arrays contains 2 extra elements at start (virtual top) and
        //       end (virtual bot) of array.
        int index = N * (row - 1) + col;
        System.out.println(index);
        return index;
    }

    // is the site (row, col) open?
    // public boolean isOpen(int row, int col)

    // is the site (row, col) full?
    // public boolean isFull(int row, int col)

    // returns the number of open sites
    // public int numberOfOpenSites()

    // does the system percolate?
    // public boolean percolates()

    // test client (optional)
    public static void main(String[] args) {

    }
}
