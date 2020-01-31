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
        int i = 3;  // last index of first 3-tuple
        int lastIndex = aux.length - 1;
        while (i <= lastIndex) {
            Point q = aux[i - 2];
            Point r = aux[i - 1];
            Point s = aux[i];

            if (isCollinear(p, q, r, s)) {
                int lineStart = i - 2;  // index of point, q

                int j = i;
                while (j < lastIndex && isCollinear(p, q, aux[++j])) {
                    i++;
                }

                int lineEnd = i;
                if (isValidLine(p, lineStart, lineEnd)) {
                    newLineSeg(p, lineStart, lineEnd);
                }
            }
            i++;
        }
    }

    private boolean isCollinear(Point p, Point q, Point r, Point s) {
        double pq = p.slopeTo(q);
        double pr = p.slopeTo(r);
        double ps = p.slopeTo(s);
        return (pq == pr && pr == ps);
    }

    private boolean isCollinear(Point p, Point q, Point x) {
        double pq = p.slopeTo(q);
        double px = p.slopeTo(x);
        return (pq == px);
    }

    private boolean isValidLine(Point p, int lineStart, int lineEnd) {
        int lineSize = lineEnd - lineStart + 2; // +1 for point p
        if (lineSize < 4) return false;
        else {
            Point min = minPoint(p, aux[lineStart]);
            return assertNotDuplicateLine(p, min);
        }
    }

    private Point minPoint(Point v, Point w) {
        if (v.compareTo(w) < 0) return v;
        else return w;
    }

    private Point maxPoint(Point v, Point w) {
        if (v.compareTo(w) > 0) return v;
        else return w;
    }

    private boolean assertNotDuplicateLine(Point p, Point lineStart) {
        // Assumption: anything to the left of Point p, has already been
        // accounted for.
        return (p.compareTo(lineStart) <= 0);
    }

    private void newLineSeg(Point p, int lineStart, int lineEnd) {
        Point min = minPoint(p, aux[lineStart]);
        Point max = maxPoint(p, aux[lineEnd]);
        lines.add(new LineSegment(min, max));
    }

    public int numberOfSegments() {
        return lines.size();
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[this.lines.size()]);
    }
}
