import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**********************************************************************************
 * A faster,sorting-based solution.
 * Given a point p, the following method determines whether p participates in a set
 * of 4or more collinear points.
 * - Think of p as the origin.
 * - For each other point q,determine the slope it makes with p.
 * - Sort the points according to the slopes they makes with p.
 * - Check if any 3(or more)adjacent points in the sorted order have equal slopes
 *   with respect to p.If so,these points,together with p,are collinear.
 *
 * Performance requirement.
 * The order of growth of the running time of your program should be n2 log n in the
 * worst case and it should use space proportional to n plus the number of line segments
 * returned. FastCollinearPoints should work properly even if the input has 5 or more
 * collinear points.
 ******************************************************************************/

public class FastCollinearPoints {
    private final ArrayList<LineSegment> segList = new ArrayList<LineSegment>();
    private final int segNumber;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // Throw an IllegalArgumentException if the argument to the constructor is null
        if (points == null) {
            throw new IllegalArgumentException();
        }

        // Throw an IllegalArgumentException if any point in the array is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        // Throw an IllegalArgumentException if the argument to the constructor contains a repeated point
        Point[] copy = new Point[points.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = points[i];
        }
        Arrays.sort(copy); // // 无Comparator，就根据自己的compareTo()排序，前提，这个object[] implements Comparable
        for (int i = 0; i < copy.length - 1; i++) {
            if (copy[i].compareTo(copy[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        // generate the line segments
        for (int p = 0; p < copy.length; p++) { // Think of p as the origin.
            Point[] otherPoints = new Point[copy.length - 1]; // Make an array for other remaining points
            int i = 0;
            for (int q = 0; q < copy.length; q++) {
                if (q != p) {
                    otherPoints[i++] = copy[q];
                }
            }
            Arrays.sort(otherPoints, copy[p].slopeOrder()); // Sort the array in SLOPE_ORDER

            Point max = null;
            Point min = null;
            int pNumber = 2;
            for (int q = 0; q < otherPoints.length - 1; q++) {
                // find all collinear points by comparing slope
                // find the max and min point in the collinear points
                if (copy[p].slopeTo(otherPoints[q]) == copy[p].slopeTo(otherPoints[q + 1])) {
                    if (min == null) {
                        if (copy[p].compareTo(otherPoints[q]) < 0) {
                            min = copy[p];
                            max = otherPoints[q];
                        } else {
                            min = otherPoints[q];
                            max = copy[p];
                        }
                    }
                    if (min.compareTo(otherPoints[q + 1]) > 0) {
                        min = otherPoints[q + 1];
                    }
                    if (max.compareTo(otherPoints[q + 1]) < 0) {
                        max = otherPoints[q + 1];
                    }
                    pNumber++;

                } else {
                    if (pNumber > 3 && copy[p].compareTo(min) == 0) {
                        LineSegment seg = new LineSegment(min, max);
                        segList.add(seg);
                    }
                    // restart: try to find another set of collinear points
                    min = null;
                    max = null;
                    pNumber = 2;
                }

            }
            if (pNumber > 3 && copy[p].compareTo(min) == 0) {
                LineSegment seg = new LineSegment(min, max);
                segList.add(seg);
            }
        }
        segNumber = segList.size();

    }

    // the number of line segments
    public int numberOfSegments() {
        return segNumber;
    }

    // the line segments
    public LineSegment[] segments() {
        int i = 0;
        LineSegment[] segArray = new LineSegment[segNumber];
        for (LineSegment seg : segList) {
            segArray[i++] = seg;
        }
        return segArray;
    }

    // test client
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
