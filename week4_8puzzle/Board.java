import edu.princeton.cs.algs4.In;

public class Board {
    private final int n;
    private int[] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = setupBoard(tiles);
    }

    private int[] setupBoard(int[][] tiles) {
        int arraySize = n * n;
        int[] newBoard = new int[arraySize];

        int i = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                newBoard[i++] = tiles[row][col];
            }
        }
        return newBoard;
    }

    // string representation of this board
    public String toString() {
        String str = Integer.toString(n);
        for (int row = 0; row < n; row++) {
            str = str.concat("\n");
            for (int col = 0; col < n; col++) {
                int i = (row * n) + col;
                str = str.concat(" " + Integer.toString(board[i]));
            }
        }
        return str;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingSum = 0;
        // iterate over each board tile (except last one, for empty tile, 0)
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i] != i + 1) hammingSum++;
        }
        return hammingSum;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        // Implement neighbors() first
        int manhattanSum = 0;

        return manhattanSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // -1 to accommodate empty tile at end
        for (int i = 0; i < board.length - 1; i++) {
            int goalTileValue = i + 1;
            if (board[i] != goalTileValue) return false;
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        String thisClassName = this.getClass().getName();
        String thatClassName = y.getClass().getName();
        if (thisClassName.equals(thatClassName)) {
            return toString().equals(y.toString());
        }
        else return false;
    }

    // all neighboring boards
    // public Iterable<Board> neighbors()

    // a board that is obtained by exchanging any pair of tiles
    // public Board twin()

    // unit testing (not graded)
    public static void main(String[] args) {
        class TestHelper {
            public void printResults(boolean condition, String testName) {
                if (condition) pass(testName);
                else fail(testName);
            }

            private void pass(String txt) {
                System.out.printf("  %-20s %s\n", txt, ".");
            }

            private void fail(String txt) {
                System.out.printf("  %-20s %s\n", txt, "F");
            }

            public Board getBoard(String filename) {
                In in = new In(filename);
                int d = in.readInt();  // board dimensions
                int[][] tiles = new int[d][d];
                for (int row = 0; row < d; row++) {
                    for (int col = 0; col < d; col++) {
                        tiles[row][col] = in.readInt();
                    }
                }
                return new Board(tiles);
            }
        }
        TestHelper h = new TestHelper();

        // Test vars
        Board board;
        Board board1;
        Board board2;
        String testName;
        boolean testCondition;

        // dimension()
        board = h.getBoard("puzzle00.txt");
        testName = "dimension()";
        testCondition = board.dimension() == 10;
        h.printResults(testCondition, testName);

        // toString()
        board = h.getBoard("puzzle3x3-00.txt");
        testName = "toString()";
        String e = "3\n 1 2 3\n 4 5 6\n 7 8 0";
        testCondition = board.toString().equals(e);
        h.printResults(testCondition, testName);

        // hamming()
        board = h.getBoard("puzzle3x3-05.txt");
        testName = "hamming() [1]";
        testCondition = board.hamming() == 5;
        h.printResults(testCondition, testName);

        board = h.getBoard("puzzle4x4-10.txt");
        testName = "hamming() [2]";
        testCondition = board.hamming() == 7;
        h.printResults(testCondition, testName);

        // manhattan()
        board = h.getBoard("puzzle3x3-05.txt");
        testName = "manhattan() [1]";
        testCondition = board.manhattan() == 5;
        h.printResults(testCondition, testName);

        board = h.getBoard("puzzle3x3-20.txt");
        testName = "manhattan() [2]";
        testCondition = board.manhattan() == 12;
        h.printResults(testCondition, testName);

        // isGoal()
        board = h.getBoard("puzzle4x4-00.txt");
        board1 = h.getBoard("puzzle4x4-01.txt");
        testName = "isGoal()";
        testCondition = board.isGoal() == true && board1.isGoal() == false;
        h.printResults(testCondition, testName);

        // equals()
        board = h.getBoard("puzzle4x4-00.txt");
        board1 = h.getBoard("puzzle4x4-00.txt");
        board2 = h.getBoard("puzzle4x4-01.txt");
        testName = "equals()";
        testCondition = board.equals(board1) == true && board.equals(board2) == false;
        h.printResults(testCondition, testName);

    }

}
