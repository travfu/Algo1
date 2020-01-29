import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lines = new ArrayList<>();
    private int lineCount = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        assertNotNull(points);
        Arrays.sort(points);

        int size = points.length;
        for (int i = 0; i < size - 3; i++) {
            for (int j = i + 1; j < size - 2; j++) {
                for (int k = j + 1; k < size - 1; k++) {
                    for (int m = k + 1; m < size; m++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];

                        assertNotNull(p);
                        assertNotNull(q);
                        assertNotNull(r);
                        assertNotNull(s);

                        double pq = p.slopeTo(q);
                        double pr = p.slopeTo(r);
                        double ps = p.slopeTo(s);

                        assertNotDuplicate(pq);
                        assertNotDuplicate(pr);
                        assertNotDuplicate(ps);

                        // points are collinear
                        if (pq == pr && pr == ps) {
                            lines.add(new LineSegment(p, q));
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
    }

    private void assertNotNull(Point point) {
        if (point == null) throw new IllegalArgumentException();
    }

    private void assertNotDuplicate(double slope) {
        if (slope == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineCount;
    }

    // the line segments
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

        // should have 1 line segment
        if (brute.numberOfSegments() == 2) print.pass("# of segments");
        else print.fail("# of segments");
    }
}
