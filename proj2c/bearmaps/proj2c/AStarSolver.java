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
    private HashMap<Vertex, Double> hueristic;
    private int statesVisited;
    private double inf;
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
        hueristic = new HashMap<>();
        statesVisited = 0;
        solutionWeight = 0;
        solution = new ArrayList<>();
        inf = Double.POSITIVE_INFINITY;
        queue = new DoubleMapPQ<>();

        setUpMaps(start, end);
        //setUpPQ(start, end);
        queue.add(start, 0.0);
        distTo.replace(start, 0.0);

        Vertex ver = queue.getSmallest();
        Stopwatch sw = new Stopwatch();
        timeSpent = sw.elapsedTime();
        while (queue.size() > 0 /*&& timeSpent < timeout*/) {
            Vertex smallest = queue.removeSmallest();
            statesVisited += 1;
            relax(smallest);
            solutionWeight = distTo.get(smallest);
            solution.add(smallest);

            if (ver.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                return;
            }
            if (queue.size() == 0) {
                outcome = SolverOutcome.UNSOLVABLE;
                solutionWeight = 0;
                solution = new ArrayList<>();
                return;
            }

            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solution = new ArrayList<>();
                solutionWeight = 0;
                return;
            }


            ver = queue.getSmallest();
            timeSpent = sw.elapsedTime();
        }


    }

    /**
     * relax all edges going out from the source vertex in StartGraph input.
     */
    private void relax(Vertex e, AStarGraph<Vertex> input) {
        List<WeightedEdge<Vertex>> n = input.neighbors(e);

        for (WeightedEdge<Vertex> we : n) {
            Vertex parent = we.from();
            Vertex child = we.to();
            double weight = we.weight();

            if (distTo.get(parent) + weight < distTo.get(child)) {
                distTo.replace(child, distTo.get(parent) + weight);

                if (queue.contains(child)) {
                    queue.changePriority(child, distTo.get(child) + hueristic.get(child));
                } else if (!queue.contains(child)) {
                    queue.add(child, distTo.get(child) + hueristic.get(child));
                }

            }
        }

    }

    /**
     * relax all edges going out from the source vertex e.
     */
    private void relax(Vertex e) {
        relax(e, graph);
    }

    /**
     * set up initial distTo and hueristic Maps.
     */
    private void setUpMaps(Vertex s, Vertex g) {
        if (!distTo.containsKey(s)) {
            distTo.put(s, inf);
            hueristic.put(s, graph.estimatedDistanceToGoal(s, g));
            setUpMaps(s, g);
        } else if (distTo.containsKey(s)) {
            return;
        }

        List<WeightedEdge<Vertex>> n = this.graph.neighbors(s);

        for (WeightedEdge<Vertex> we : n) {
            Vertex v = we.to();
            setUpMaps(v, g);
        }

        return;
    }

    /**
     * set up initial priority queue.
     */
    private void setUpPQ(Vertex start, Vertex end) {
        if (start.equals(end)) {
            if (!queue.contains(start)) {
                queue.add(start, 0.0);
            }
            return;
        }

        List<WeightedEdge<Vertex>> n = graph.neighbors(start);

        for (WeightedEdge<Vertex> edge : n) {
            Vertex parent = edge.from();
            Vertex child = edge.to();
            double weight = edge.weight() + hueristic.get(parent);

            if (!queue.contains(parent)) {
                queue.add(parent, weight);
            }

            setUpPQ(child, end);
        }

        return;
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
