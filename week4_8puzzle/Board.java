import edu.princeton.cs.algs4.StdIn;

public class Board {
    private final int n;
    private int[] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
    }

    // string representation of this board
    // public String toString()

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    // public int hamming()

    // sum of Manhattan distances between tiles and goal
    // public int manhattan()

    // is this board the goal board?
    // public boolean isGoal()

    // does this board equal y?
    // public boolean equals(Object y)

    // all neighboring boards
    // public Iterable<Board> neighbors()

    // a board that is obtained by exchanging any pair of tiles
    // public Board twin()

    // unit testing (not graded)
    public static void main(String[] args) {
        class TestHelper {
            public void results(boolean condition, String testName) {
                if (condition) pass(testName);
                else fail(testName);
            }

            private void pass(String txt) {
                System.out.printf("  %-20s %s\n", txt, ".");
            }

            private void fail(String txt) {
                System.out.printf("  %-20s %s\n", txt, "F");
            }
        }
        TestHelper print = new TestHelper();

        // prepare input
        int n = 0;
        if (!StdIn.isEmpty()) n = StdIn.readInt();

        int[][] tiles = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                tiles[row][col] = StdIn.readInt();
            }
        }
        Board board = new Board(tiles);

        // Tests
        String testName;
        boolean testCondition;

        // dimension()
        testName = "dimension()";
        testCondition = board.dimension() == n;
        print.results(testCondition, testName);


    }

}
