package bearmaps.proj2c;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;

    private DoubleMapPQ<Vertex> queue;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private int statesVisited;
    private AStarGraph<Vertex> graph;


    /**
     * @source project 2c spec for the following comment.
     * Constructor which finds the solution.
     * Computes everything necessary for all other methods to return their results in constant time.
     * Note that the timeout passed in is in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        /** Plan of attack:
         * first initialize all instance variables.
         * while loop to create a pq and compute a solution.
         * return computed solution and updated instance variables.
         */

        graph = input;
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        statesVisited = 0;
        solutionWeight = 0;
        solution = new ArrayList<>();
        queue = new DoubleMapPQ<>();

        queue.add(start, 0.0);
        distTo.put(start, 0.0);
        edgeTo.put(start, start);

        Stopwatch sw = new Stopwatch();
        timeSpent = sw.elapsedTime();
        while (queue.size() > 0 && timeSpent < timeout) {
            Vertex smallest = queue.removeSmallest();
            statesVisited += 1;
            relax(smallest, end);

            if (smallest.equals(end)) {
                Vertex from = edgeTo.get(smallest);
                Vertex to = smallest;

                while (!from.equals(to)) {
                    solution.add(0, to);
                    to = from;
                    from = edgeTo.get(to);
                }

                solution.add(0, to);
                solutionWeight = distTo.get(smallest);
                outcome = SolverOutcome.SOLVED;
                return;
            }

            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solutionWeight = 0;
                return;
            }

            timeSpent = sw.elapsedTime();
        }

        outcome = SolverOutcome.UNSOLVABLE;
        solutionWeight = 0;
        return;

    }

    /**
     * relax all edges going out from the source vertex in StartGraph input.
     */
    private void relax(Vertex e, Vertex goal) {
        List<WeightedEdge<Vertex>> n = graph.neighbors(e);

        for (WeightedEdge<Vertex> we : n) {
            Vertex parent = we.from();
            Vertex child = we.to();
            double weight = we.weight();

            if (!distTo.containsKey(child)) {
                distTo.put(child, Double.POSITIVE_INFINITY);
            }
            if (!edgeTo.containsKey(child)) {
                edgeTo.put(child, parent);
            }

            if (distTo.get(parent) + weight < distTo.get(child)) {
                distTo.replace(child, distTo.get(parent) + weight);
                edgeTo.replace(child, parent);
                double hueristic = graph.estimatedDistanceToGoal(child, goal);

                if (queue.contains(child)) {
                    queue.changePriority(child, distTo.get(child) + hueristic);
                } else if (!queue.contains(child)) {
                    queue.add(child, distTo.get(child) + hueristic);
                }

            }
        }

    }

    /**
     * @source project 2c spec for the following comment.
     * Returns one of SolverOutcome.SOLVED, SolverOutcome.TIMEOUT, or SolverOutcome.UNSOLVABLE.
     * Should be SOLVED if the AStarSolver was able to complete all work in the time given.
     * UNSOLVABLE if the priority queue became empty.
     * TIMEOUT if the solver ran out of time.
     * You should check to see if you have run out of time every time you dequeue.
     */
    public SolverOutcome outcome() {
        return this.outcome;
    }

    /**
     * @source project 2c spec for the following comment.
     * A list of vertices corresponding to a solution.
     * Should be empty if result was TIMEOUT or UNSOLVABLE.
     */
    public List<Vertex> solution() {
        return this.solution;
    }

    /**
     * @source project 2c spec for the following comment.
     * The total weight of the given solution, taking into account edge weights.
     * Should be 0 if result was TIMEOUT or UNSOLVABLE.
     */
    public double solutionWeight() {
        return this.solutionWeight;
    }

    /**
     * @source project 2c spec for the following comment.
     * The total number of priority queue dequeue operations.
     */
    public int numStatesExplored() {
        return this.statesVisited;
    }

    /**
     * @source project 2c spec for the following comment.
     * The total time spent in seconds by the constructor.
     */
    public double explorationTime() {
        return this.timeSpent;
    }
}
