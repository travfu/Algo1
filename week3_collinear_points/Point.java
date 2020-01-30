/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        boolean isVertical = false;
        boolean isHorizontal = false;

        if (this.x == that.x) isVertical = true;
        if (this.y == that.y) isHorizontal = true;

        if (isVertical && isHorizontal) return Double.NEGATIVE_INFINITY;
        else if (isVertical) return Double.POSITIVE_INFINITY;
        else if (isHorizontal) return 0.0;
        else return ((double) (that.y - this.y) / (that.x - this.x));
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        int xDiff = this.x - that.x;
        int yDiff = this.y - that.y;

        if (xDiff == 0 && yDiff == 0) return 0;   // equal
        else if (yDiff < 0 || yDiff == 0 && xDiff < 0) return -1;  // lesser
        else return 1;   // greater
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new BySlope(this);
    }

    private static class BySlope implements Comparator<Point> {
        private final Point ref;

        public BySlope(Point point) {
            this.ref = point;
        }

        public int compare(Point v, Point w) {
            double vSlope = this.ref.slopeTo(v);
            double wSlope = this.ref.slopeTo(w);

            if (vSlope == wSlope) {
                return 0;
            }
            else if (vSlope < wSlope) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        class TestHelper {
            public void pass(String txt) {
                System.out.printf("  %-20s %s\n", txt, ".");
            }

            public void fail(String txt) {
                System.out.printf("  %-20s %s\n", txt, "F");
            }
        }
        TestHelper print = new TestHelper();


        // slopeTo tests:
        System.out.println("Tests for slopeTo()");
        // vertical points should return positive infinity
        Point v1 = new Point(1, 1);
        Point v2 = new Point(1, 3);
        if (v1.slopeTo(v2) == Double.POSITIVE_INFINITY) print.pass("vertical");
        else print.fail("vertical");

        // horizontal points should return 0
        Point h1 = new Point(1, 1);
        Point h2 = new Point(3, 1);
        if (h1.slopeTo(h2) == 0.0) print.pass("horizontal");
        else print.fail("horizontal");

        // degenerate (equal) points should return negative infinity
        Point d1 = new Point(1, 1);
        Point d2 = new Point(1, 1);
        if (d1.slopeTo(d2) == Double.NEGATIVE_INFINITY) print.pass("degenerate");
        else print.fail("degenerate");

        // valid slope should return slope value
        Point s1 = new Point(1, 1);
        Point s2 = new Point(3, 3);
        if (s1.slopeTo(s2) == 1.0) print.pass("normal slope");
        else print.fail("normal slope");

        // assert int is casted into double
        // (int) 1 / 2 == 0         incorrect
        // (double) 1 / 2 == 0.5    correct
        Point a1 = new Point(1, 1);
        Point b1 = new Point(3, 2);
        if (a1.slopeTo(b1) == 0.5) print.pass("(double) casting");
        else print.fail("(double) casting");


        // compareTo tests:
        System.out.println("\nTests for compareTo()");

        // equal should return 0
        Point a2 = new Point(3, 4);
        if (a2.compareTo(a2) == 0) print.pass("equal points");
        else print.fail("equal points");

        // if smaller, should return negative int (-1); case 1
        Point a3 = new Point(1, 1);
        Point b3 = new Point(1, 2);
        if (a3.compareTo(b3) < 0) print.pass("a < b; case1");
        else print.fail("a < b; case1");

        // if smaller, should return negative int (-1); case 2
        Point a4 = new Point(1, 1);
        Point b4 = new Point(2, 1);
        if (a4.compareTo(b4) < 0) print.pass("a < b; case2");
        else print.fail("a < b; case2");

        // if larger, should return positive int (+1)
        Point a5 = new Point(2, 2);
        Point b5 = new Point(1, 1);
        if (a5.compareTo(b5) > 0) print.pass("a > b");
        else print.fail("a > b");


        // slopeOrder & BySlope Comparator tests:
        System.out.println("\nTests for slopeTo() Comparator");

        // v < w
        Point a6 = new Point(2, 2);
        Point v6 = new Point(5, 3);
        Point w6 = new Point(3, 5);
        Comparator<Point> comp6 = a6.slopeOrder();
        if (comp6.compare(v6, w6) == -1) print.pass("v < w");
        else print.fail("v < w");

        // v==w
        Point a7 = new Point(3, 3);
        Point v7 = new Point(5, 5);
        Point w7 = new Point(1, 1);
        Comparator<Point> C7 = a7.slopeOrder();
        if (C7.compare(v7, w7) == 0) print.pass("v==w (degenerate)");
        else print.fail("v==w (degenerate)");

        // v==w (horizontal)
        Point a8 = new Point(3, 2);
        Point v8 = new Point(1, 2);
        Point w8 = new Point(5, 2);
        Comparator<Point> C8 = a8.slopeOrder();
        if (C8.compare(v8, w8) == 0) print.pass("v==w (horizontal)");
        else print.fail("v==w (horizontal)");

        // v==w (vertical)
        Point a9 = new Point(3, 3);
        Point v9 = new Point(3, 5);
        Point w9 = new Point(3, 1);
        Comparator<Point> C9 = a9.slopeOrder();
        if (C9.compare(v9, w9) == 0) print.pass("v==w (vertical)");
        else print.fail("v==w (vertical)");

        // vertical + slope
        Point a10 = new Point(3, 3);
        Point v10 = new Point(3, 1);
        Point w10 = new Point(5, 5);
        Comparator<Point> C10 = a10.slopeOrder();
        if (C10.compare(v10, w10) == 1) print.pass("vertical + slope");
        else print.fail("vertical + slope");

        // horizonal + slope
        Point a11 = new Point(3, 3);
        Point v11 = new Point(5, 3);
        Point w11 = new Point(5, 5);
        Comparator<Point> C11 = a11.slopeOrder();
        if (C11.compare(v11, w11) == -1) print.pass("horizonal + slope");
        else print.fail("horizonal + slope");

        // degenerate + slope
        Point a12 = new Point(3, 3);
        Point v12 = a12;
        Point w12 = new Point(5, 5);
        Comparator<Point> C12 = a12.slopeOrder();
        if (C12.compare(v12, w12) == -1) print.pass("degenerate + slope");
        else print.fail("degenerate + slope");
    }
}
