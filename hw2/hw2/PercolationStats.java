package hw2;

public class PercolationStats {
    /* fraction of open sites per experiment.*/
    private double[] frac;

    /* Perform T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        Percolation p = pf.make(N);
        frac = new double[T];

        for (int i = 0; i < T; i++) {
            while (!p.percolates()) {
                int r = edu.princeton.cs.introcs.StdRandom.uniform(N);
                int c = edu.princeton.cs.introcs.StdRandom.uniform(N);
                p.open(r, c);
            }
            frac[i] = ((double) p.numberOfOpenSites()) / (double) (N * N);
        }
    }

    /* Sample mean of percolation threshold. */
    public double mean() {
        return edu.princeton.cs.introcs.StdStats.mean(frac);
    }

    /* Sample standard deviation of percolation threshold. */
    public double stddev() {
        return edu.princeton.cs.introcs.StdStats.stddev(frac);
    }

    /* Low endpoint of 95% confidence interval. */
    public double confidenceLow() {
        return (mean() - ((1.96 * stddev())) / Math.sqrt(frac.length));
    }

    /* High endpoint of 95% confidence interval. */
    public double confidenceHigh() {
        return (mean() + ((1.96 * stddev())) / Math.sqrt(frac.length));
    }
}
