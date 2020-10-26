package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    /* basic sanity check of ArrayHeapMinPQ constructor. */
    @Test
    public void testSanityCheckConstructor() {
        ArrayHeapMinPQ<String> heap1 = new ArrayHeapMinPQ<>();
        ArrayHeapMinPQ<String> heap2 = new ArrayHeapMinPQ<>();
        ArrayHeapMinPQ<String> heap3 = new ArrayHeapMinPQ<>();
    }

    /**
     * simple test for contains method.
     * assume method add is correct.
     **/
    @Test
    public void testNaiveContains() {
        ArrayHeapMinPQ<Double> heap = new ArrayHeapMinPQ<>();

        /* test empty state. */
        assertFalse(heap.contains(0.0));

        /* tests contains with 1000 random inputs. */
        for (int i = 0; i < 1000; i++) {
            heap.add(Math.random(), Math.random());
        }
        heap.add(0.23, Math.random());

        assertTrue(heap.contains(0.23));
        assertFalse(heap.contains(1.0));
    }

    /* simple test of add and size method. */
    @Test
    public void testNaiveAdd() {
        ArrayHeapMinPQ<String> heap = new ArrayHeapMinPQ<>();

        String[] keys = {"a", "b", "c", "d", "e"};
        double[] priortities = {0.0, 6.9, 2.2, 2.1, 7.4};
        HashMap<String, Integer> expectedHeap = new HashMap<>();
        expectedHeap.put("a", 1);
        expectedHeap.put("b", 4);
        expectedHeap.put("c", 3);
        expectedHeap.put("d", 2);
        expectedHeap.put("e", 5);

        for (int i = 0; i < keys.length; i++) {
            heap.add(keys[i], priortities[i]);
        }

        int expectedSize = 5;
        int actualSize = heap.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedHeap, heap.getItems());
    }

    /**
     * simple test of getSmallest and removeSmallest method.
     * @source stackoverflow.com on the try and catch exception test section.
     **/
    @Test
    public void testNaiveGetRemoveSmallest() {
        ArrayHeapMinPQ<String> heap = new ArrayHeapMinPQ<>();

        /* test exception. */
        boolean thrown = false;
        try {
            heap.removeSmallest();
        } catch (NoSuchElementException e) {
            thrown = true;
        }

        assertTrue(thrown);

        /* test if methods work for simple cases. */
        String[] keys = {"a", "b", "c", "d", "e"};
        double[] priortities = {0.0, 6.9, 2.2, 2.1, 7.4};
        HashMap<String, Integer> expectedHeap = new HashMap<>();
        expectedHeap.put("a", 1);
        expectedHeap.put("b", 4);
        expectedHeap.put("c", 3);
        expectedHeap.put("d", 2);
        expectedHeap.put("e", 5);

        for (int i = 0; i < keys.length; i++) {
            heap.add(keys[i], priortities[i]);
        }

        heap.removeSmallest();
        String expected = "d";
        String actual = heap.getSmallest();

        assertEquals(expected, actual);

        /* see if is errors when everything is removed from heap. */
        heap.removeSmallest();
        heap.removeSmallest();
        heap.removeSmallest();
        heap.removeSmallest();

        boolean thrown1 = false;
        try {
            heap.removeSmallest();
        } catch (NoSuchElementException e) {
            thrown1 = true;
        }

        assertEquals(true, thrown1);
    }

    /* simple test of change priority method. */
    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> heap = new ArrayHeapMinPQ<>();
        heap.add("a", 1);
        heap.add("b", 4);
        heap.add("c", 3);
        heap.add("d", 2);
        heap.add("e", 5);

        /* test empty state. */
        boolean thrown = false;
        try {
            heap.changePriority("t", 2);
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);

        /* checks original smallest. */
        String expected = "a";
        String actual = heap.getSmallest();
        assertEquals(expected, actual);

        /* test smallest after changed priority. */
        heap.changePriority("a", 6);
        String expected1 = "d";
        String actual1 = heap.getSmallest();
        assertEquals(expected1, actual1);

        /* a node only sinks when its priority is greater than both children. */
        heap.changePriority("d", 4);
        String expected2 = "d";
        String actual2 = heap.getSmallest();
        assertEquals(expected2, actual2);

    }

    /* test if code returns valid getSmallest for 1000 random values. */
    @Test
    public void testRandom1000() {
        ArrayHeapMinPQ<Double> heap = new ArrayHeapMinPQ<>();

        for (int i = 0; i < 1000; i++) {
            heap.add(Math.random(), Math.random());
        }

        heap.add(1.0, -1.0);
        double actual = heap.getSmallest();
        double expected = 1.0;
        assertEquals(expected, actual, 0.01);

        // smallest stays the same if priorities are equal.
        heap.add(2.0, -1.0);
        double actual1 = heap.getSmallest();
        double expected1 = 1.0;
        assertEquals(expected1, actual1, 0.01);

        // new smallest will be item 2.0.
        heap.removeSmallest();
        double actual2 = heap.getSmallest();
        double expected2 = 2.0;
        assertEquals(expected2, actual2, 0.01);
    }

    /* more intensive test with comparison to NaiveMinPQ. */
    @Test
    public void testIntensiveRandom() {
        ArrayHeapMinPQ<Double> heap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> naive = new NaiveMinPQ<>();

        /* test if add methods yield same smallest. */
        for (int i = 0; i < 100000; i++) {
            double key = Math.random();
            double pri = Math.random();
            heap.add(key, pri);
            naive.add(key, pri);
        }
        double actual = heap.getSmallest();
        double expected = naive.getSmallest();
        assertEquals(expected, actual, 0.01);

        /* test if remove methods yield same smallest. */
        for (int i = 0; i < 2000; i++) {
            heap.removeSmallest();
            naive.removeSmallest();
        }
        double actual1 = heap.getSmallest();
        double expected1 = naive.getSmallest();
        assertEquals(expected1, actual1, 0.01);

        /* test if change priority methods yield same smallest. */
        for (int i = 0; i < 1000; i++) {
            double p = Math.random();
            heap.changePriority(actual1, p);
            naive.changePriority(expected1, p);
            double actual2 = heap.getSmallest();
            double expected2 = naive.getSmallest();
            assertEquals(expected2, actual2, 0.01);
        }

    }

    /**
     * Printing the speed tests.
     * @source lab5 spec.
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

    /* tests if add method is of big O Nlog(N) runtime. */
    @Test
    public void addSpeedTestAH() {
        ArrayHeapMinPQ<Double> heap = new ArrayHeapMinPQ<>();
        int[] totalRuns = {10, 100, 1000, 10000, 30000, 50000, 100000, 500000, 1000000, 1500000};
        List<Integer> addN = new ArrayList<>();
        List<Double> addTimes = new ArrayList<>();
        List<Integer> addOpCounts = new ArrayList<>();

        for (int i : totalRuns) {
            addN.add(i);
            int ops = 0;
            Stopwatch sw = new Stopwatch();
            for (int k = 0; k < i; k++) {
                heap.add(Math.random(), Math.random());
                ops++;
            }
            double elapse = sw.elapsedTime();
            addTimes.add(elapse);
            addOpCounts.add(ops);
        }

        System.out.println("ArrayHeap Time Table for Add");
        printTimingTable(addN, addTimes, addOpCounts);
        System.out.println();
    }

    /* tests if add method runtime is less than of ArrayHeap. */
    @Test
    public void addSpeedTestNaive() {
        NaiveMinPQ<Double> naive = new NaiveMinPQ<>();
        int[] totalRuns = {10, 100, 1000, 10000, 30000, 50000, 100000, 500000, 1000000, 1500000};
        List<Integer> addN = new ArrayList<>();
        List<Double> addTimes = new ArrayList<>();
        List<Integer> addOpCounts = new ArrayList<>();

        for (int i : totalRuns) {
            addN.add(i);
            int ops = 0;
            Stopwatch sw = new Stopwatch();
            for (int k = 0; k < i; k++) {
                naive.add(Math.random(), Math.random());
                ops++;
            }
            double elapse = sw.elapsedTime();
            addTimes.add(elapse);
            addOpCounts.add(ops);
        }

        System.out.println("Naive Heap Time Table for Add");
        printTimingTable(addN, addTimes, addOpCounts);
        System.out.println();
    }

    /* tests if change priority method is of big O log(N) runtime. */
    @Test
    public void changePrioritySpeedTest() {
        ArrayHeapMinPQ<Double> heap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> naive = new NaiveMinPQ<>();
        int[] queries = {10, 100, 1000, 2000, 3000, 4000};
        List<Integer> N = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        List<Integer> naiveN = new ArrayList<>();
        List<Double> naiveTimes = new ArrayList<>();
        List<Integer> naiveOpCounts = new ArrayList<>();


            for (int q = 0; q < 1000000; q++) {
                double k = Math.random();
                double p = Math.random();
                heap.add(k, p);
                naive.add(k, p);
            }

            for (int q : queries) {
                N.add(q);
                naiveN.add(q);

                double[] ps = new double[q];
                for (int z = 0 ; z < q; z++) {
                    ps[z] = Math.random();
                }

                int ops = 0;
                Stopwatch sw = new Stopwatch();
                for (int w = 0; w < q; w++) {
                    heap.changePriority(heap.getSmallest(), ps[w]);
                    ops++;
                }
                double elapse = sw.elapsedTime();
                times.add(elapse);
                opCounts.add(ops);

                int ops1 = 0;
                Stopwatch sw1 = new Stopwatch();
                for (int w = 0; w < q; w++) {
                    naive.changePriority(naive.getSmallest(), ps[w]);
                    ops1++;
                }
                double elapse1 = sw1.elapsedTime();
                naiveTimes.add(elapse1);
                naiveOpCounts.add(ops1);
            }

        System.out.println("Naive Heap Time Table for Change Priority");
        printTimingTable(N, times, opCounts);
        System.out.println();
        System.out.println("ArrayHeap Time Table for Change Priority");
        printTimingTable(naiveN, naiveTimes, naiveOpCounts);
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayHeapMinPQTest test = new ArrayHeapMinPQTest();
        test.changePrioritySpeedTest();

    }
}
