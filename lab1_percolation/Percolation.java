/******************************************************************************
 * Date: 07.02.20
 * Creator : Lynn
 * Description: Model a percolation system
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid; // close = 0; open = 1
    private final WeightedQuickUnionUF ufPerc;
    private final WeightedQuickUnionUF ufFull;
    private final int n;
    private int sumOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        sumOpen = 0;
        ufPerc = new WeightedQuickUnionUF(n * n + 2);
        ufFull = new WeightedQuickUnionUF((n * n + 2));
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }

        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            sumOpen++;
            // top
            if (row != 1 && grid[row - 2][col - 1]) {
                ufPerc.union(n * (row - 1) + col - 1, n * (row - 2) + col - 1);
                ufFull.union(n * (row - 1) + col - 1, n * (row - 2) + col - 1);

            }
            if (row == 1) {
                ufPerc.union(n * (row - 1) + col - 1, n * n);
                ufFull.union(n * (row - 1) + col - 1, n * n);
            }
            // down
            if (row != n && grid[row][col - 1]) {
                ufPerc.union(n * (row - 1) + col - 1, n * row + col - 1);
                ufFull.union(n * (row - 1) + col - 1, n * row + col - 1);
            }
            if (row == n) {
                ufPerc.union(n * (row - 1) + col - 1, n * n + 1);

            }
            // left
            if (col != 1 && grid[row - 1][col - 2]) {
                ufPerc.union(n * (row - 1) + col - 1, n * (row - 1) + col - 2);
                ufFull.union(n * (row - 1) + col - 1, n * (row - 1) + col - 2);

            }
            // right
            if (col != n && grid[row - 1][col]) {
                ufPerc.union(n * (row - 1) + col - 1, n * (row - 1) + col);
                ufFull.union(n * (row - 1) + col - 1, n * (row - 1) + col);
            }

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return ufFull.find(n * (row - 1) + col - 1) == ufFull.find(n * n);
        } else {
            return false;
        }

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return sumOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufPerc.find(n * n) == ufPerc.find(n * n + 1);
    }

    // test client (optional)
    // public static void main(String[] args)
}
