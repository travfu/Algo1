/* *****************************************************************************
 *  Name: Percolation
 *  Date: 29 December 2019
 *  Description: https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] openSites;
    private WeightedQuickUnionUF grid;
    private int virtualTop;
    private int virtualBot;

    // creates n-by-n grid, with all sites initially blocked
    // grid also includes a virtual top and bot site, which all top and bottom
    // rows are connected to via a union.
    public Percolation(int n) {
        int gridSize = n * n + 2; // +2 for virtual top and bot
        openSites = new boolean[gridSize];
        grid = new WeightedQuickUnionUF(gridSize);
        virtualTop = 0;
        virtualBot = gridSize - 1;
    }

    // opens the site (row, col) if it is not open already
    // public void open(int row, int col)

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
