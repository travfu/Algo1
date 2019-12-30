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
        if (!validCoord(row, col)) {
            String msg = String.format("Coord(%s, %s) is out of range",
                                       row, col);
            throw new IllegalArgumentException(msg);
        }

        int i = getIndexOf(row, col);
        openSites[i] = true;

        // if site is in first row, unionise with virtual top
        if (row == 1) {
            grid.union(virtualTop, i);
        }
        // if site is in last row, unionise with virtual bot
        else if (row == N) {
            grid.union(i, virtualBot);
        }
        // unionise with adjacent open sites (top, bot, left, right), if exists
        tryUnion(i, row - 1, col);
        tryUnion(i, row + 1, col);
        tryUnion(i, row, col - 1);
        tryUnion(i, row, col + 1);
    }

    private boolean validCoord(int row, int col) {
        if (row > 0 && row <= N && col > 0 && col <= N) {
            return true;
        }
        return false;
    }

    private int getIndexOf(int row, int col) {
        // Converts a 2-D matrix coord into a 1-D array index
        // Note: arrays contains 2 extra elements at start (virtual top) and
        //       end (virtual bot) of array.
        int index = N * (row - 1) + col;
        return index;
    }

    private void tryUnion(int index1, int row, int col) {
        if (validCoord(row, col) && isOpen(row, col)) {
            int index2 = getIndexOf(row, col);
            grid.union(index1, index2);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (validCoord(row, col)) {
            int i = getIndexOf(row, col);
            return openSites[i];
        }
        else {
            String msg = String.format("Coord(%s, %s) is out of range",
                                       row, col);
            throw new IllegalArgumentException(msg);
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (validCoord(row, col)) {
            int i = getIndexOf(row, col);
            if (grid.find(i) == virtualTop) {
                return true;
            }
            return false;
        }
        else {
            String msg = String.format("Coord(%s, %s) is out of range",
                                       row, col);
            throw new IllegalArgumentException(msg);
        }
    }

    // returns the number of open sites
    // public int numberOfOpenSites()

    // does the system percolate?
    public boolean percolates() {
        // if virtual bottom site is connected to virtual top, then it
        // percolates
        System.out.println(String.format("bot: %s", grid.find(virtualBot)));
        if (grid.find(virtualBot) == virtualTop) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        int x2 = p.grid.find(2);
        int x6 = p.grid.find(6);
        int x7 = p.grid.find(7);
        int x10 = p.grid.find(10);
        int x11 = p.grid.find(11);
        int x12 = p.grid.find(12);

        System.out.println(x2);
        System.out.println(x6);
        System.out.println(x7);
        System.out.println(x10);
        System.out.println(x11);
        System.out.println(x12);

        p.open(1, 2); // 2
        p.open(2, 2); // 6
        p.open(2, 3); // 7
        p.open(3, 3); // 11
        p.open(3, 4); // 12
        p.open(3, 2); // 10

        x2 = p.grid.find(2);
        x6 = p.grid.find(6);
        x7 = p.grid.find(7);
        x10 = p.grid.find(10);
        x11 = p.grid.find(11);
        x12 = p.grid.find(12);

        System.out.println(x2);
        System.out.println(x6);
        System.out.println(x7);
        System.out.println(x10);
        System.out.println(x11);
        System.out.println(x12);

        System.out.println(p.isFull(3, 1));
        System.out.println(p.isFull(3, 2));

        System.out.println(String.format("percolates? %s", p.percolates()));
        p.open(4, 2);
        System.out.println(String.format("percolates? %s", p.percolates()));
    }
}
