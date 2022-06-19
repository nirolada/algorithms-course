/* *****************************************************************************
 *  Name:              Nir Albo
 *  Coursera User ID:  123456
 *  Last modified:     June 20, 2022
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int n;
    private int openSites;
    private final WeightedQuickUnionUF qu;

    // creates n-by-n grid, with all sites initially blocked
    // upper-left [1, 1], bottom-right [n, n]
    // allowed time complexity: O(n^2)
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be bigger than 0!");

        this.n = n;
        openSites = 0;
        grid = new boolean[n][n];

        qu = new WeightedQuickUnionUF(n * n + 2);
        // first site (idx 0) is the start, last site (idx n*n + 1) is the end
        // they are both virtual (not part of the grid)
        // the n*n sites between them represent the grid itself
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        grid[row - 1][col - 1] = true;
        ++openSites;

        int point1D = rowColTo1D(row, col);
        if (row == 1)
            qu.union(point1D, 0); // connect to the virtual start
        if (row == n)
            qu.union(point1D, n * n + 1); // connect to the virtual end

        if (row - 2 >= 0 && isOpen(row - 1, col))
            qu.union(point1D, rowColTo1D(row - 1, col));

        if (col < n && isOpen(row, col + 1))
            qu.union(point1D, rowColTo1D(row, col + 1));

        if (row < n && isOpen(row + 1, col))
            qu.union(point1D, rowColTo1D(row + 1, col));

        if (col - 2 >= 0 && isOpen(row, col - 1))
            qu.union(point1D, rowColTo1D(row, col - 1));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return qu.find(rowColTo1D(row, col)) == qu.find(0);
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
        if (row - 1 < 0 || row - 1 > n - 1)
            throw new IllegalArgumentException(
                    "row index " + Integer.toString(row) + " out of bounds!");

        if (col - 1 < 0 || col - 1 > n - 1)
            throw new IllegalArgumentException(
                    "col index " + Integer.toString(col) + " out of bounds!");
    }

    // converts from 2D indices to 1D index
    private int rowColTo1D(int row, int col) {
        return 1 + (row - 1) * n + (col - 1);
    }
}
