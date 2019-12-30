/* *****************************************************************************
 *  Name: Percolation
 *  Date: 29 December 2019
 *  Description: https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[] openSites;
    private int openCount;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF fullSites;
    private final int n;
    private final int virtualTop;
    private final int virtualBot;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // Two sets of WeightedQuickUnion (WQU) connectivity array and an array
        // of open sites are used here to model percolation.

        // Both WQU represents connection between all open sites.
        // The first WQU contains two extra elements, a virtual top and bottom,
        // to enhance performance by forgoing iterating throuh first and last
        // rows to check if the system percolates. However, the virtual bottom
        // will inadvertently connect all non-full open sites, making it
        // incompatible for checking whether a site is full.
        // To solve this, the second WQU contains a virtual top but not bottom.
        // This models the connections between all open sites but only full open
        // sites will share a root connection value with the virtual top.
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }

        int gridSize = n * n + 2; // +2 for virtual top and bot
        openSites = new boolean[gridSize];
        grid = new WeightedQuickUnionUF(gridSize);
        fullSites = new WeightedQuickUnionUF(gridSize - 1);
        virtualTop = 0;
        virtualBot = gridSize - 1;
        this.n = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validCoord(row, col)) {
            String msg = String.format("Coord(%s, %s) is out of range",
                                       row, col);
            throw new IllegalArgumentException(msg);
        }

        int i = getIndexOf(row, col);
        openSites[i] = true;
        openCount++;

        // unionise with adjacent open sites (top, bot, left, right), if exists
        tryUnion(i, row - 1, col);
        tryUnion(i, row + 1, col);
        tryUnion(i, row, col - 1);
        tryUnion(i, row, col + 1);

        // if site is in first row, unionise with virtual top
        if (row == 1) {
            grid.union(virtualTop, i);
            fullSites.union(virtualTop, i);
        }

        // if site is in last row and is full, unionise with virtual bot
        if (row == n) {
            grid.union(i, virtualBot);
        }
    }

    private boolean validCoord(int row, int col) {
        if (row > 0 && row <= n && col > 0 && col <= n) {
            return true;
        }
        return false;
    }

    private int getIndexOf(int row, int col) {
        // Converts a 2-D matrix coord into a 1-D array index
        // Note: arrays contains an extra elements at start (virtual top) and
        //       may contain extra element at end (virtual bot) of array.
        int index = n * (row - 1) + col;
        return index;
    }

    private void tryUnion(int index1, int row, int col) {
        if (validCoord(row, col) && isOpen(row, col)) {
            int index2 = getIndexOf(row, col);
            grid.union(index1, index2);
            fullSites.union(index1, index2);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validCoord(row, col)) {
            String msg = String.format("Coord(%s, %s) is out of range",
                                       row, col);
            throw new IllegalArgumentException(msg);
        }
        int i = getIndexOf(row, col);
        return openSites[i];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validCoord(row, col)) {
            String msg = String.format("Coord(%s, %s) is out of range",
                                       row, col);
            throw new IllegalArgumentException(msg);
        }
        int i = getIndexOf(row, col);
        if (fullSites.find(i) == fullSites.find(virtualTop)) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        // if virtual bottom site is connected to virtual top, then it
        // percolates
        if (grid.find(virtualBot) == grid.find(virtualTop)) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);

        int n = in.readInt();
        Percolation p = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            p.open(i, j);
        }
    }
}
