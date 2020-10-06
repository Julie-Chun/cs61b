package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /* the grid. */
    private int[][] grid;

    /* size of square grid. */
    private int size;

    /* number of open sites. */
    private int numOpen;

    /* keeps records of openess. */
    private boolean[][] openSpots;

    /* virtual sites.*/
    private WeightedQuickUnionUF tree;

    /* creates a N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        size = N;
        grid = new int[N][N];
        openSpots = new boolean[N][N];
        tree = new WeightedQuickUnionUF((size * size) + 2);

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                grid[r][c] = c + (r * size);
                openSpots[r][c] = false;
                if (r == 0) {
                    tree.union((size * size) + 1, grid[r][c]);
                }
            }
        }
    }

    /* opens the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        if (row < 0 || row > size - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        } else if (col < 0 || col > size - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            if (!openSpots[row][col]) {
                numOpen++;
                openSpots[row][col] = true;
            }

            if (row - 1  >= 0 && openSpots[row - 1][col]) {
                tree.union(grid[row][col], grid[row - 1][col]);
            }
            if (row + 1 < size && openSpots[row + 1][col]) {
                tree.union(grid[row + 1][col], grid[row][col]);
            }
            if (col - 1  >= 0 && openSpots[row][col - 1]) {
                tree.union(grid[row][col], grid[row][col - 1]);
            }
            if (col + 1 < size && openSpots[row][col +  1]) {
                tree.union(grid[row][col + 1], grid[row][col]);
            }
        }
    }

    /* checks if the site (row, col) is open. */
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > size - 1) {
            throw new java.lang.IndexOutOfBoundsException("row is out of bounds.");
        }
        if (col < 0 || col > size - 1) {
            throw new java.lang.IndexOutOfBoundsException("column is out of bounds.");
        }

        return openSpots[row][col];
    }

    /* checks if the site (row, col) is full. */
    public boolean isFull(int row, int col) {
        if (row < 0 || row > size - 1) {
            throw new java.lang.IndexOutOfBoundsException("row is out of bounds.");
        }
        if (col < 0 || col > size - 1) {
            throw new java.lang.IndexOutOfBoundsException("column is out of bounds.");
        }

        return tree.connected((size * size) + 1, grid[row][col]) && isOpen(row, col);
    }

    /* returns the number of open sites. */
    public int numberOfOpenSites() {
        return numOpen;
    }

    /* checks if the system percolates. */
    public boolean percolates() {
        return lastFilled((size * size) + 1, size - 1, 0);
    }

    /* checks if any column of the last row is filled and rooted. */
    private boolean lastFilled(int x, int r, int c) {
        if (c >= size) {
            return false;
        }

        if (tree.connected(x, c + (r * size))) {
            if (isFull(r, c)) {
                return true;
            }
        }

        return lastFilled(x, r, c++);
    }

    /* For unit testing (not required, but keep this here for the autograder). */
    public static void main(String[] args) {
        Percolation per = new Percolation(4);

        boolean one = per.isFull(0, 0);
        boolean two = per.isOpen(3, 3);
        boolean three = per.percolates();
        int num0 = per.numberOfOpenSites();
        boolean a = per.isFull(0, 0); // false
        per.open(0, 0);
        int num1 = per.numberOfOpenSites(); // 1
        per.open(1, 1);
        int num2 = per.numberOfOpenSites(); // 2
        boolean b = per.isOpen(1, 1); // true
        boolean c = per.isFull(1, 1); // false
        boolean d = per.isFull(0, 0); //true
        per.open(3, 0);
        int num3 = per.numberOfOpenSites(); // 3
        boolean e = per.percolates(); //false
        per.open(1, 0);
        boolean f = per.isFull(1, 1); // true
        per.open(2, 0);
        int num5 = per.numberOfOpenSites(); // 5
        boolean g = per.isFull(2, 0); // true
        boolean h = per.percolates(); // true
    }
}
