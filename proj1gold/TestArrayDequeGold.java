import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestArrayDequeGold {

    @Test
    public void findFaulty() {
        boolean faulty = false;
        List<String> commands = new ArrayList<>();
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        int method = 0;
        while (!faulty) {
            int input = StdRandom.uniform(1000);

            if (student.isEmpty() || solution.isEmpty()) {
                method = StdRandom.uniform(0, 2);
            } else {
                method = StdRandom.uniform(0, 4);
            }

            if (method == 0) {
                commands.add("addFirst(" + input + ")");
                student.addFirst(input);
                solution.addFirst(input);
            } else if (method == 1) {
                commands.add("addLast(" + input + ")");
                student.addLast(input);
                solution.addLast(input);
            } else if (method == 2) {
                commands.add("removeFirst()");
                Integer studentSol = student.removeFirst();
                Integer solutionSol = solution.removeFirst();
                assertEquals(printCommands(commands), solutionSol, studentSol);
            } else if (method == 3) {
                commands.add("removeLast()");
                Integer studentSol = student.removeLast();
                Integer solutionSol = solution.removeLast();
                assertEquals(printCommands(commands), solutionSol, studentSol);
            }
        }
    }

    private String printCommands(List<String> list) {
        String result = "";
        for (String s : list) {
            result += "\n" + s;
        }
        return result;
    }
}
