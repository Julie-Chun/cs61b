import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about SLList getLast method.
 */
public class TimeSLList {
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        int[] Ns = new int[] {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        ArrayList<Integer> list =  new ArrayList<>();
        ArrayList<Double> time =  new ArrayList<>();
        ArrayList<Integer> op =  new ArrayList<>();

        for (int i = 0; i < Ns.length; i++) {
            SLList<Integer> testing = new SLList<>();
            int opNum = 0;
            for (int k = 0; k < Ns[i]; k++) {
                testing.addLast(k);
            }
            list.add(testing.size());

            Stopwatch sw = new Stopwatch();
            for (int k = 0; k < 10000; k++) {
                testing.getLast();
                opNum++;
            }
            double timeInSeconds = sw.elapsedTime();
            time.add(timeInSeconds);
            op.add(opNum);
        }

        printTimingTable(list, time, op);
    }

}
