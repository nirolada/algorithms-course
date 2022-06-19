/* *****************************************************************************
 *  Name:              Nir Albo
 *  Coursera User ID:  123456
 *  Last modified:     06/19/2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

import java.util.Arrays;

public class PercolationStats {
    private int t;
    private double x_mean;
    private double s;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        t = trials;
        double[] x = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            x[i] = (double) p.numberOfOpenSites() / (n * n);
        }

        x_mean = StdStats.mean(x);
        s = StdStats.stddev(x);
    }

    // sample mean of percolation threshold
    public double mean() {
        return x_mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return x_mean - 1.96 * s / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return x_mean + 1.96 * s / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        double[] conf_interval = new double[] { ps.confidenceLo(), ps.confidenceHi() };
        StdOut.println("95% confidence interval = " + Arrays.toString(conf_interval));
    }
}
