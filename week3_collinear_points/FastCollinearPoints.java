import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] aux;
    private Point min = null;
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
                lineFound = true;
                lineStart = i;
            }

            else if (lineFound && !isCollinear(p, q, r, s)) {
                lineFound = false;
                int lineEnd = i + 1;
                int lineSize = lineEnd - lineStart + 2; // +1 for point p
                if (lineSize >= 4) {
                    LineSegment newLine;
                    Point minPoint = aux[lineStart];
                    if (p.compareTo(aux[lineStart]) < 0) {
                        newLine = new LineSegment(p, aux[lineEnd]);
                        minPoint = p;
                    }
                    else if (p.compareTo(aux[lineEnd]) > 0) {
                        newLine = new LineSegment(aux[lineStart], p);
                    }
                    else {
                        newLine = new LineSegment(aux[lineStart], aux[lineEnd]);
                    }

                    if (assertNotDuplicateLine(p, minPoint)) {
                        lines.add(newLine);
                    }
                }
            }
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
}
