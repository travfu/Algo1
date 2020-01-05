/* *****************************************************************************
 *  Name: PercolationStats
 *  Date: 29 December 2019
 *  Description: https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int[] threshold;
    private final int trials;
    private final int n;
    private final double confLevel = 1.96;

    private double meanValue;
    private double stddevValue;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n > 0; T > 0");
        }

        threshold = new int[trials];
        this.trials = trials;
        this.n = n;

        for (int i = 0; i < trials; i++) {
            int openCount = 0;

            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    openCount++;
                }
            }
            threshold[i] = openCount;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double mean = StdStats.mean(threshold);
        int totalSites = n * n;
        double sampleMean = mean / totalSites;
        meanValue = sampleMean;
        return sampleMean;
    }

    private double getMean() {
        if (meanValue != 0.0d) {
            return meanValue;
        }
        return mean();
    }


    // sample standard deviation of percolation threshold
    public double stddev() {
        double stddev = StdStats.stddev(threshold);
        int totalSites = n * n;
        double sampleStddev = stddev / totalSites;
        stddevValue = sampleStddev;
        return sampleStddev;
    }

    private double getStddev() {
        if (stddevValue != 0.0d) {
            return stddevValue;
        }
        return stddev();
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = getMean();
        double stddev = getStddev();
        double loCI = mean - (confLevel * stddev) / Math.sqrt(trials);
        return loCI;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = getMean();
        double stddev = getStddev();
        double hiCI = mean + (confLevel * stddev) / Math.sqrt(trials);
        return hiCI;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats pStats = new PercolationStats(n, t);

        double mean = pStats.mean();
        double stddev = pStats.stddev();
        double loCI = pStats.confidenceLo();
        double hiCI = pStats.confidenceHi();

        System.out.println(String.format("mean   = %s", mean));
        System.out.println(String.format("stddev = %s", stddev));
        System.out.println(String.format("95%% CI = [%s, %s]", loCI, hiCI));
    }
}
