/* *****************************************************************************
 *  Name:              Nir Albo
 *  Coursera User ID:  123456
 *  Last modified:     June 11, 2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int n;
    private int openSites;
    private WeightedQuickUnionUF qu;

    // creates n-by-n grid, with all sites initially blocked
    // upper-left [1, 1], bottom-right [n, n]
    // allowed time complexity: O(n^2)
    public Percolation(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("size must be bigger than 0!");

        n = size;
        openSites = 0;
        grid = new boolean[n][n];

        qu = new WeightedQuickUnionUF(n * n + 2);
        // first site (i=0) is the start, last site (i=n*n) + 1 is the end
        // they are both virtual (not part of the grid)
        // the n*n sites between them represent the grid itself
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            openSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return !grid[row - 1][col - 1];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return qu.find(0) == qu.find(n * n + 1);
    }

    // validates input indices
    private void validate(int row, int col) {
        if (row - 1 < 0 || row - 1 > n)
            throw new ArrayIndexOutOfBoundsException(
                    "Row must be between 1 and " + Integer.toString(n) + "!");

        if (col - 1 < 0 || col - 1 > n)
            throw new ArrayIndexOutOfBoundsException(
                    "Col must be between 1 and " + Integer.toString(n) + "!");
    }

    // converts from 2D indices to 1D index
    private int rowColTo1D(int row, int col) {
        validate(row, col);
        return 1 + (row - 1) * n + (col - 1);
    }

    private static void printBool(boolean b, boolean spaced) {
        StdOut.print(Boolean.toString(b) + (spaced ? " " : ""));
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        for (int i = 0; i < p.grid.length; i++) {
            for (int j = 0; j < p.grid[i].length; j++)
                printBool(p.grid[i][j], true);

            StdOut.println();
        }
        printBool(p.isOpen(1, 1), true);
        printBool(p.isFull(2, 2), false);
    }
}
