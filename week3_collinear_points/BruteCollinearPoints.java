import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lines = new ArrayList<>();
    private int lineCount = 0;

    public BruteCollinearPoints(Point[] points) {
        assertNotNull(points);
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        assertNoDupes(sortedPoints);

        int size = sortedPoints.length;
        for (int i = 0; i < size - 3; i++) {
            for (int j = i + 1; j < size - 2; j++) {
                for (int k = j + 1; k < size - 1; k++) {
                    for (int m = k + 1; m < size; m++) {
                        Point p = sortedPoints[i];
                        Point q = sortedPoints[j];
                        Point r = sortedPoints[k];
                        Point s = sortedPoints[m];

                        double pq = p.slopeTo(q);
                        double pr = p.slopeTo(r);
                        double ps = p.slopeTo(s);

                        // points are collinear
                        if (pq == pr && pr == ps) {
                            lines.add(new LineSegment(p, s));
                            lineCount++;
                        }
                        // first 3 points are not collinear
                        // if the first 3 points are not collinear, checking
                        // the fourth point is unnecessary.
                        else if (pq != pr) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private void assertNotNull(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }
    }

    private void assertNoDupes(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1].compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return lineCount;
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[this.lines.size()]);
    }

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

        int n = 0;
        if (!StdIn.isEmpty()) n = StdIn.readInt();

        Point[] points = new Point[n];
        int i = 0;
        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i++] = new Point(x, y);
        }

        BruteCollinearPoints brute = new BruteCollinearPoints(points);

        System.out.println(brute.numberOfSegments());
        for (LineSegment line : brute.segments()) System.out.println(line);
    }
}
