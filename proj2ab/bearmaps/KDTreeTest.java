package bearmaps;

import org.junit.Test;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class KDTreeTest {

    /**
     * tests generic constructor.
     * based on simple example in lecture.
     **/
    @Test
    public void sanityConstructorTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(4, 2);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree t1 = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        KDTree t2 = new KDTree(List.of(p4, p7, p3, p6, p5, p1, p3));
        KDTree t3 = new KDTree(List.of());
        KDTree t4 = new KDTree(List.of(p4));
        KDTree t5 = new KDTree(List.of(p2, p1, p5, p7));
    }

    /**
     * simple test for nearest.
     * test sourced from the example in lecture 19.
     */
    @Test
    public void simpleNearestTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(1, -45);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(4, 2);
        Point p7 = new Point(1, 5);
        Point p8 = new Point(4, 4);
        Point p9 = new Point(2, 7);
        Point p10 = new Point(2, 6);
        Point p11 = new Point(2, 8);

        NaivePointSet naive = new NaivePointSet(List.of(p1, p2, p3, p4,
                p5, p6, p7, p8, p9, p10, p11));
        Point nPoint = naive.nearest(0, 7);
        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
        Point kdPoint = kd.nearest(0, 7);
        Point expected = p9;

        double kdDistance = Point.distance(kdPoint, new Point(0, 7));
        double nDistance = Point.distance(nPoint, new Point(0, 7));

        assertFalse(kdPoint.equals(p1));
        assertTrue(kdPoint.equals(expected));
        assertEquals(expected, kdPoint);
        assertEquals(kdDistance, nDistance, 0.00000001);
    }

    private static Random r = new Random();

    /**
     * creates list of random points from -1000 to 1000.
     * insprired and sourced from walkthrough video.
     */
    private static List<Point> pointList(int num) {
        List<Point> list = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            double x = 2000 * r.nextDouble() - 1000;
            double y = 2000 * r.nextDouble() - 1000;
            list.add(new Point(x, y));
        }
        return list;
    }

    /**
     * tests nearest of 500 locations with 1000000 points.
     * compares results between naive tree and KD tree.
     * points might not be the same, but the distance should be equal.
     * inspired by the walkthrough video.
     **/
    @Test
    public void moreComplexNearestTest() {
        List<Point> pList = pointList(50000);

        KDTree kd = new KDTree(pList);
        NaivePointSet naive = new NaivePointSet(pList);

        List<Point> queries = pointList(2000);
        for (Point p : queries) {
            Point kdPoint = kd.nearest(p.getX(), p.getY());
            Point nPoint = naive.nearest(p.getX(), p.getY());

            double kdDistance = Point.distance(kdPoint, p);
            double naiveDistance = Point.distance(nPoint, p);
            //System.out.println("target: (" + p.getX() + ", " + p.getY() + ")");
            //System.out.println("KD Point: (" + kdPoint.getX() + ", " + kdPoint.getY() + ")");
            //System.out.println("Naive Point: (" + nPoint.getX() + ", " + nPoint.getY() + ")");
            assertEquals(naiveDistance, kdDistance, 0.00000001);
        }
    }

    /**
     * Printing the speed tests.
     * printTimingTable sourced from lab5.
     **/
    private static void printTimingTable(List<Integer> N, List<Double> times,
                                         List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < N.size(); i += 1) {
            int n = N.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", n, time, opCount, timePerOp);
        }
    }

    /* Testing the speed of kd-tree constructor. */
    public static void timeKDConstruction() {
        int[] N = new int[] {31250, 62500, 125000, 250000, 500000, 1000000, 2000000};
        List<Double> testedTimes = new ArrayList<>();
        List<Integer> nList = new ArrayList<>();

        for (int i : N) {
            List<Point> pList = pointList(i);
            nList.add(i);

            Stopwatch sw = new Stopwatch();
            KDTree tree = new KDTree(pList);
            double timeInSeconds = sw.elapsedTime();
            testedTimes.add(timeInSeconds);
        }

        System.out.println("Time Table for Kd-Tree Constructor");
        printTimingTable(nList, testedTimes, nList);
        System.out.println();
    }


    /* Testing the speed of naive tree nearest. */
    public static void timeNaiveNearest() {
        int[] N = new int[] {125, 250, 500, 1000};
        List<Double> testedTimes = new ArrayList<>();
        List<Integer> nList = new ArrayList<>();
        List<Integer> opList = new ArrayList<>();

        for (int i : N) {
            int opCount = 0;
            nList.add(i);
            opList.add(1000000);
            List<Point> pList = pointList(i);
            List<Point> queries = pointList(1000000);

            NaivePointSet ntree = new NaivePointSet(pList);

            Stopwatch sw = new Stopwatch();
            for (Point p : queries) {
                ntree.nearest(p.getX(), p.getY());
                opCount++;
            }
            double timeInSeconds = sw.elapsedTime();
            testedTimes.add(timeInSeconds);
            opList.add(opCount);
        }

        System.out.println("Time Table for Naive Tree Nearest");
        printTimingTable(nList, testedTimes, opList);
        System.out.println();
    }


    /* Testing the speed of KD tree nearest. */
    public static void timeKDNearest() {
        int[] N = new int[] {31250, 62500, 125000, 250000, 500000, 1000000};
        List<Double> testedTimes = new ArrayList<>();
        List<Integer> nList = new ArrayList<>();
        List<Integer> opList = new ArrayList<>();

        for (int i : N) {
            int opCount = 0;
            nList.add(i);
            List<Point> pList = pointList(i);
            List<Point> queries = pointList(1000000);

            KDTree kd = new KDTree(pList);

            Stopwatch sw = new Stopwatch();
            for (Point p : queries) {
                opCount++;
                kd.nearest(p.getX(), p.getY());
            }
            double timeInSeconds = sw.elapsedTime();
            testedTimes.add(timeInSeconds);
            opList.add(opCount);
        }

        System.out.println("Time Table for KD Tree Nearest");
        printTimingTable(nList, testedTimes, opList);
        System.out.println();
    }

    public static void main(String[] args) {
        timeKDConstruction();
        timeNaiveNearest();
        timeKDNearest();
    }

}
