import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about AList construction.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        int[] Ns = new int[] {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 10000000};
        ArrayList<Integer> list =  new ArrayList<>();
        ArrayList<Double> time =  new ArrayList<>();

        for (int i = 0; i < Ns.length; i++) {
            Stopwatch sw = new Stopwatch();
            AList<Integer> testing = new AList<>();
            for (int k = 0; k < Ns[i]; k++) {
                testing.addLast(k);
            }
            double timeInSeconds = sw.elapsedTime();
            list.add(testing.size());
            time.add(timeInSeconds);
        }

        printTimingTable(list, time, list);
    }


}
