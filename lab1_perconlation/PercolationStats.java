/******************************************************************************
 * Date: 08.02.20
 * Creator : Lynn
 * Description: Monte Carlo simulation:  estimate the percolation threshold
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] threshold;
    private final int trials;
    private final double parameter = 1.96;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        threshold = new double[trials];
        Percolation[] perc = new Percolation[trials];
        for (int i = 0; i < trials; i++) {
            perc[i] = new Percolation(n);
            while (!perc[i].percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!perc[i].isOpen(row, col)) {
                    perc[i].open(row, col);
                }
            }
            double numberOfOpenSites = perc[i].numberOfOpenSites();
            double numberOfGrids = n * n;
            threshold[i] = numberOfOpenSites / numberOfGrids;
        }
        mean = StdStats.mean(threshold);
        stddev = StdStats.stddev(threshold);

    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - parameter * stddev / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + parameter * stddev / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats percStats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + percStats.mean());
        System.out.println("stddev                  = " + percStats.stddev());
        System.out.println("95% confidence interval = [" + percStats.confidenceLo() + ", "
                + percStats.confidenceHi() + "]");
    }

}
