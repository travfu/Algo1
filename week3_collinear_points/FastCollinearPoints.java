import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] aux;     // auxilliary array for finding lines
    private final ArrayList<LineSegment> lines = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        assertNotNull(points);
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        assertNoDupes(sortedPoints);

        for (Point p : sortedPoints) {
            aux = sortedPoints.clone();
            Arrays.sort(aux, p.slopeOrder());
            findLines(p);
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

    private void findLines(Point p) {
        for (int i = 3; i < aux.length; i++) {
            Point q = aux[i - 2];
            Point r = aux[i - 1];
            Point s = aux[i];

            if (isCollinear(p, q, r, s)) {
                int lineStart = i - 2;
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
        return (pq == pr && pr == ps);
    }

    private boolean assertNotDuplicateLine(Point p, Point lineStart) {
        // Assumption: anything to the left of Point p, has already been
        // accounted for.
        return (p.compareTo(lineStart) <= 0);
    }

    public int numberOfSegments() {
        return lines.size();
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[this.lines.size()]);
    }
}
