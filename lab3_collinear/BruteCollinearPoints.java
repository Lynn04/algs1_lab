import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/******************************************************************************
 *  Brute force.
 *  Write a program BruteCollinearPoints.java that examines 4 points
 *  at a time and checks whether they all lie on the same line segment, returning
 *  all such line segments. To check whether the 4 points p, q, r, and s are
 *  collinear, check whether the three slopes between p and q, between p and r,
 *  and between p and s are all equal.
 *
 * Performance requirement.
 * The order of growth of the running time of your program should be n4 in the
 * worst case and it should use space proportional to n plus the number of line
 * segments returned
 ******************************************************************************/

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segList = new ArrayList<LineSegment>();
    private int segNumber;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        Arrays.sort(copy); // 无Comparator，就根据自己的compareTo()排序，前提，这个object[] implements Comparable
        for (int i = 0; i < copy.length - 1; i++) {
            if (copy[i].compareTo(copy[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        // generate the segments
        for (int p = 0; p < copy.length; p++) {
            for (int q = p + 1; q < copy.length; q++) {
                for (int r = q + 1; r < copy.length; r++) {
                    for (int s = r + 1; s < copy.length; s++) {
                        double s1 = copy[p].slopeTo(copy[q]);
                        double s2 = copy[q].slopeTo(copy[r]);
                        double s3 = copy[r].slopeTo(copy[s]);
                        if (s1 == s2 && s2 == s3) {
                            LineSegment seg = new LineSegment(copy[p], copy[s]);
                            segList.add(seg);
                        }
                    }
                }
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
        LineSegment[] segArray = new LineSegment[segNumber];
        int i = 0;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
