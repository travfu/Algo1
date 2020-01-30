import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] points;  // sorted copy of input
    private Point[] aux;     // auxilliary array for finding lines
    private ArrayList<LineSegment> lines = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        assertNotNull(points);
        this.points = points.clone();
        Arrays.sort(this.points);
        assertValidArg(this.points);

        for (Point p : this.points) {
            aux = this.points.clone();
            Arrays.sort(aux, p.slopeOrder());
            findLines(p);
        }
    }

    private void assertNotNull(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
    }

    private void assertValidArg(Point[] points) {
        Point prev = null;
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
            if (prev != null && prev.compareTo(p) == 0) {
                throw new IllegalArgumentException();
            }
            prev = p;
        }
    }

    private void findLines(Point p) {
        int lineStart = 0;
        for (int i = 3; i < aux.length; i++) {
            Point q = aux[i - 2];
            Point r = aux[i - 1];
            Point s = aux[i];

            if (isCollinear(p, q, r, s)) {
                lineStart = i - 2;
                int lineEnd = i;

                // end of array -> add line segment
                if (i == aux.length - 1) newLineSeg(p, lineStart, lineEnd);

                // continue iteration until end of line segment
                for (int j = i + 1; j < aux.length; j++) {
                    Point nextPoint = aux[j];
                    if (p.slopeTo(q) == p.slopeTo(nextPoint)) {
                        lineEnd = j;
                        if (j == aux.length - 1) {
                            newLineSeg(p, lineStart, lineEnd);
                            i = j;
                        }
                    }
                    else {
                        newLineSeg(p, lineStart, lineEnd);
                        i = j;
                        break;
                    }
                }
            }
        }
    }

    private void newLineSeg(Point p, int lineStart, int lineEnd) {
        int lineSize = lineEnd - lineStart + 2; // +1 for point p
        if (lineSize >= 4) {
            Point minPoint = aux[lineStart];
            Point maxPoint = aux[lineEnd];
            if (p.compareTo(minPoint) < 0) minPoint = p;      // p is min
            else if (p.compareTo(maxPoint) > 0) maxPoint = p; // p is max

            LineSegment newLine = new LineSegment(minPoint, maxPoint);
            if (assertNotDuplicateLine(p, minPoint)) lines.add(newLine);
        }
    }

    private boolean isCollinear(Point p, Point q, Point r, Point s) {
        double pq = p.slopeTo(q);
        double pr = p.slopeTo(r);
        double ps = p.slopeTo(s);
        if (pq == pr && pr == ps) return true;
        else return false;
    }

    private boolean assertNotDuplicateLine(Point p, Point lineStart) {
        // Assumption: anything to the left of Point p, has already been
        // accounted for.
        if (p.compareTo(lineStart) <= 0) return true;
        else return false;
    }

    public int numberOfSegments() {
        return lines.size();
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[this.lines.size()]);
    }

    public static void main(String[] args) {
        int n = 0;
        if (!StdIn.isEmpty()) n = StdIn.readInt();

        Point[] points = new Point[n];
        int i = 0;
        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i++] = new Point(x, y);
        }

        FastCollinearPoints fast = new FastCollinearPoints(points);

        System.out.println(fast.numberOfSegments());
        for (LineSegment p : fast.segments()) System.out.println(p);
    }
}
