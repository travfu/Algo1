import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {
    private final int n;
    private int[][] board;
    private final int indexOffset = 1;  // array to board offset value
    private final int emptyTile = 0;    // value of empty tile

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = copy(tiles);
    }

    private int[][] copy(int[][] tiles) {
        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = tiles[i][j];
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
                str = str.concat(" " + Integer.toString(board[row][col]));
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
        int expected = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                expected++;
                int tileValue = board[row][col];
                if (tileValue != emptyTile && tileValue != expected) {
                    hammingSum++;
                }
            }
        }
        return hammingSum;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanSum = 0;
        int expected = 1;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] != expected++ && board[row][col] != emptyTile) {
                    int tileValue = board[row][col];
                    int goalCol = (tileValue - indexOffset) % n;
                    int goalRow = (tileValue - goalCol - indexOffset) / n;
                    int rowDiff = row - goalRow;
                    int colDiff = col - goalCol;
                    manhattanSum += Math.abs(rowDiff) + Math.abs(colDiff);
                }
            }
        }
        return manhattanSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int expected = 0;
        int lastCol = n - indexOffset;
        int lastRow = n - indexOffset;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                expected++;
                if (row == lastRow && col == lastCol) continue; // expected empty
                if (board[row][col] != expected) return false;
            }
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
    public Iterable<Board> neighbors() {
        LinkedList<Board> boards = new LinkedList<Board>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == emptyTile) {
                    if (hasTop(i, j)) boards.add(getNeighBoard(i, j, i - 1, j));
                    if (hasLeft(i, j)) boards.add(getNeighBoard(i, j, i, j - 1));
                    if (hasBot(i, j)) boards.add(getNeighBoard(i, j, i + 1, j));
                    if (hasRight(i, j)) boards.add(getNeighBoard(i, j, i, j + 1));
                }
            }
        }
        return boards;
    }

    private boolean hasLeft(int row, int col) {
        int leftEdgeCol = 0;
        return col > leftEdgeCol;
    }

    private boolean hasRight(int row, int col) {
        int rightEdgeCol = n - indexOffset;
        return col < rightEdgeCol;
    }

    private boolean hasTop(int row, int col) {
        int topEdgeRow = 0;
        return row > topEdgeRow;
    }

    private boolean hasBot(int row, int col) {
        int botEdgeRow = n - indexOffset;
        return row < botEdgeRow;
    }

    private void swap(int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    private Board getNeighBoard(int row1, int col1, int row2, int col2) {
        Board newBoard = new Board(board);
        newBoard.swap(row1, col1, row2, col2);
        return newBoard;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = new Board(board);
        int row1, col1, row2, col2;
        do {
            row1 = StdRandom.uniform(n);
            col1 = StdRandom.uniform(n);
            row2 = StdRandom.uniform(n);
            col2 = StdRandom.uniform(n);
        } while (row1 == row2 && col1 == col2);
        twinBoard.swap(row1, col1, row2, col2);
        return twinBoard;
    }

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
        int index = 0;

        // dimension()
        testName = "dimension()";
        board = h.getBoard("puzzle00.txt");
        testCondition = board.dimension() == 10;
        h.printResults(testCondition, testName);

        // toString()
        testName = "toString()";
        board = h.getBoard("puzzle3x3-00.txt");
        String e = "3\n 1 2 3\n 4 5 6\n 7 8 0";
        testCondition = board.toString().equals(e);
        h.printResults(testCondition, testName);

        // hamming()
        testName = "hamming() [1]";
        board = h.getBoard("puzzle3x3-05.txt");
        testCondition = board.hamming() == 5;
        h.printResults(testCondition, testName);

        testName = "hamming() [2]";
        board = h.getBoard("puzzle4x4-10.txt");
        testCondition = board.hamming() == 7;
        h.printResults(testCondition, testName);

        // manhattan()
        testName = "manhattan() [1]";
        board = h.getBoard("puzzle3x3-05.txt");
        testCondition = board.manhattan() == 5;
        h.printResults(testCondition, testName);

        testName = "manhattan() [2]";
        board = h.getBoard("puzzle3x3-20.txt");
        testCondition = board.manhattan() == 12;
        h.printResults(testCondition, testName);

        // isGoal()
        testName = "isGoal()";
        board = h.getBoard("puzzle4x4-00.txt");
        board1 = h.getBoard("puzzle4x4-01.txt");
        testCondition = board.isGoal() == true && board1.isGoal() == false;
        h.printResults(testCondition, testName);

        // equals()
        testName = "equals()";
        board = h.getBoard("puzzle4x4-00.txt");
        board1 = h.getBoard("puzzle4x4-00.txt");
        board2 = h.getBoard("puzzle4x4-01.txt");
        testCondition = board.equals(board1) == true && board.equals(board2) == false;
        h.printResults(testCondition, testName);

        // swap()
        testName = "swap()";
        board = h.getBoard("puzzle01.txt");
        // puzzle01.txt
        //  1  0
        //  3  2
        // swap tiles 3 and 2
        board.swap(1, 0, 1, 1);
        int[][] eBoard = new int[2][2];
        eBoard[0][0] = 1;
        eBoard[0][1] = 0;
        eBoard[1][0] = 2;
        eBoard[1][1] = 3;

        testCondition = true;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                if (board.board[row][col] != eBoard[row][col]) {
                    testCondition = false;
                    break;
                }
            }
        }
        h.printResults(testCondition, testName);

        // neighbors() [1]
        //   original:   neighbor 1:   neighbor 2:
        //     1 2 3       1 2 3         1 2 3
        //     4 5 6       4 5 0         4 5 6
        //     7 8 0       7 8 6         7 0 8
        testName = "neighbors() [1]";
        int[][] ogBoard = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        int[][] nBoard1 = { { 1, 2, 3 }, { 4, 5, 0 }, { 7, 8, 6 } };
        int[][] nBoard2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } };
        int[][][] nBoards = { nBoard1, nBoard2 };
        board = new Board(ogBoard);

        testCondition = true;
        index = 0;
        for (Board x : board.neighbors()) {
            if (!Arrays.deepEquals(x.board, nBoards[index++])) {
                testCondition = false;
                break;
            }
        }
        h.printResults(testCondition, testName);

        // neighbors() [2]
        //   original:   neighbor 1:   neighbor 2:   neighbor 3:   neighbor 4:
        //     1 2 3       1 0 3         1 2 3         1 2 3         1 2 3
        //     4 0 5       4 2 5         0 4 5         4 7 5         4 5 0
        //     6 7 8       6 7 8         6 7 8         6 0 8         6 7 8
        testName = "neighbors() [2]";
        int[][] ogBoard1 = { { 1, 2, 3 }, { 4, 0, 5 }, { 6, 7, 8 } };
        int[][] nBoard11 = { { 1, 0, 3 }, { 4, 2, 5 }, { 6, 7, 8 } };
        int[][] nBoard12 = { { 1, 2, 3 }, { 0, 4, 5 }, { 6, 7, 8 } };
        int[][] nBoard13 = { { 1, 2, 3 }, { 4, 7, 5 }, { 6, 0, 8 } };
        int[][] nBoard14 = { { 1, 2, 3 }, { 4, 5, 0 }, { 6, 7, 8 } };
        int[][][] nBoards1 = { nBoard11, nBoard12, nBoard13, nBoard14 };
        board = new Board(ogBoard1);

        testCondition = true;
        index = 0;
        for (Board x : board.neighbors()) {
            if (!Arrays.deepEquals(x.board, nBoards1[index++])) {
                testCondition = false;
                break;
            }
        }
        h.printResults(testCondition, testName);

        // twin()
        testName = "twin()";
        board = h.getBoard("puzzle2x2-01.txt");
        Board twinBoard = board.twin();
        int diffCount = 0;
        for (int i = 0; i < board.n; i++) {
            for (int j = 0; j < board.n; j++) {
                if (board.board[i][j] != twinBoard.board[i][j]) diffCount++;
            }
        }
        testCondition = diffCount == 2;
        h.printResults(testCondition, testName);
    }
}


