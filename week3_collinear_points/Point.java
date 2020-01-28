/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
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
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        boolean isVertical = false;
        boolean isHorizontal = false;

        if (this.x == that.x) isVertical = true;
        if (this.y == that.y) isHorizontal = true;

        if (isVertical && isHorizontal) return Double.NEGATIVE_INFINITY;
        else if (isVertical)            return Double.POSITIVE_INFINITY;
        else if (isHorizontal)          return 0.0;
        else                            return ((double)(that.y - this.y) / (that.x - this.x));
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        int x_diff = this.x - that.x;
        int y_diff = this.y - that.y;

        if (x_diff == 0 && y_diff == 0)                   return 0;   // equal
        else if (y_diff < 0 || y_diff == 0 && x_diff < 0) return -1;  // lesser
        else                                              return 1;   // greater
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
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
        // slopeTo tests:
        System.out.println("Tests for slopeTo()");
        // vertical points should return positive infinity
        Point v1 = new Point(1, 1);
        Point v2 = new Point(1, 3);
        System.out.printf("  %-20s", "vertical");
        if (v1.slopeTo(v2) == Double.POSITIVE_INFINITY) System.out.println(".");
        else                                            System.out.println("F");

        // horizontal points should return 0
        Point h1 = new Point(1, 1);
        Point h2 = new Point(3, 1);
        System.out.printf("  %-20s", "horizontal");
        if (h1.slopeTo(h2) == 0.0) System.out.println(".");
        else                       System.out.println("F");

        // degenerate (equal) points should return negative infinity
        Point d1 = new Point(1, 1);
        Point d2 = new Point(1, 1);
        System.out.printf("  %-20s", "degenerate");
        if (d1.slopeTo(d2) == Double.NEGATIVE_INFINITY) System.out.println(".");
        else                                            System.out.println("F");

        // valid slope should return slope value
        Point s1 = new Point(1, 1);
        Point s2 = new Point(3, 3);
        System.out.printf("  %-20s", "normal slope");
        if (s1.slopeTo(s2) == 1.0) System.out.println(".");
        else                       System.out.println("F");

        // assert int is casted into double
        // (int) 1 / 2 == 0         incorrect
        // (double) 1 / 2 == 0.5    correct
        Point a1 = new Point(1, 1);
        Point b1 = new Point(3, 2);
        System.out.printf("  %-20s", "(double) casting");
        if (a1.slopeTo(b1) == 0.5) System.out.println(".");
        else                       System.out.println("F");


        // compareTo tests:
        System.out.println("\nTests for compareTo()");

        // equal should return 0
        Point a2 = new Point(3, 4);
        System.out.printf("  %-20s", "equal points");
        if (a2.compareTo(a2) == 0) System.out.println(".");
        else                       System.out.println("F");

        // if smaller, should return negative int (-1); case 1
        Point a3 = new Point(1, 1);
        Point b3 = new Point(1, 2);
        System.out.printf("  %-20s", "a < b; case1");
        if (a3.compareTo(b3) < 0)  System.out.println(".");
        else                       System.out.println("F");

        // if smaller, should return negative int (-1); case 2
        Point a4 = new Point(1, 1);
        Point b4 = new Point(2, 1);
        System.out.printf("  %-20s", "a < b; case2");
        if (a4.compareTo(b4) < 0)  System.out.println(".");
        else                       System.out.println("F");

        // if larger, should return positive int (+1)
        Point a5 = new Point(2, 2);
        Point b5 = new Point(1, 1);
        System.out.printf("  %-20s", "a < b; case2");
        if (a5.compareTo(b5) > 0)  System.out.println(".");
        else                       System.out.println("F");
    }
}
