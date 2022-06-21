/* *****************************************************************************
 *  Name:              Nir Albo
 *  Coursera User ID:  123456
 *  Last modified:     06/19/2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int t;
    private final double xMean;
    private final double s;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        t = trials;
        double[] x = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            }
            x[i] = (double) p.numberOfOpenSites() / (n * n);
        }

        xMean = StdStats.mean(x);
        s = StdStats.stddev(x);
    }

    // sample mean of percolation threshold
    public double mean() {
        return xMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return xMean - CONFIDENCE_95 * s / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return xMean + CONFIDENCE_95 * s / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + Double.toString(ps.confidenceLo()) + ", "
                               + Double.toString(ps.confidenceHi()) + "]");
    }
}
