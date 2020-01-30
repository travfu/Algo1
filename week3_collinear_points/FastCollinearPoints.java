import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] aux;
    private ArrayList<LineSegment> lines = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        assertNotNull(points);
        Arrays.sort(points);
        assertValidArg(points);

        for (Point p : points) {
            aux = points.clone();
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
        boolean lineFound = false;
        for (int i = 1; i < aux.length - 2; i++) {
            Point q = aux[i];
            Point r = aux[i + 1];
            Point s = aux[i + 2];

            if (!lineFound && isCollinear(p, q, r, s)) {
                // case 1: beginning of line found
                lineFound = true;
                lineStart = i;
                if (i == aux.length - 3) {
                    // case 3: beginning of line found at the end of the array
                    int lineEnd = aux.length - 1;
                    newLineSeg(p, lineStart, lineEnd);
                }
            }

            else if (lineFound && !isCollinear(p, q, r, s) ||
                    lineFound && i == aux.length - 3) {
                // case 2: end of line found
                lineFound = false;
                int lineEnd = i + 1;
                newLineSeg(p, lineStart, lineEnd);
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
