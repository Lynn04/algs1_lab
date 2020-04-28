import java.util.ArrayList;

public class Board {
    private int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String line = n + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                line = line + " " + tiles[i][j];
            }
            line = line + "\n";
        }
        return line;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    if (tiles[i][j] != n * i + j + 1) {
                        hamming++;
                    }
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    int row = (tiles[i][j] - 1) / n;
                    int col = (tiles[i][j] - 1) % n;
                    manhattan = manhattan + Math.abs(i - row) + Math.abs(j - col);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    if (tiles[i][j] != n * i + j + 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int row = 0;
        int col = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        if (row > 0) {
            Board neighbor = new Board(exch(row, col, row - 1, col));
            neighbors.add(neighbor);
        }
        if (row < n - 1) {
            Board neighbor = new Board(exch(row, col, row + 1, col));
            neighbors.add(neighbor);
        }
        if (col > 0) {
            Board neighbor = new Board(exch(row, col, row, col - 1));
            neighbors.add(neighbor);
        }
        if (col < n - 1) {
            Board neighbor = new Board(exch(row, col, row, col + 1));
            neighbors.add(neighbor);
        }
        return neighbors;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (tiles[0][0] != 0 && tiles[0][1] != 0) {
            return new Board(exch(0, 0, 0, 1));
        } else {
            return new Board(exch(1, 0, 1, 1));
        }
    }

    private int[][] exch(int row1, int col1, int row2, int col2) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        int i = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = i;
        return copy;
    }

    // unit testing (not graded)
//    public static void main(String[] args) {
//        int[][] test = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
//        Board b = new Board(test);
//        System.out.println(b);
//        System.out.println(b.hamming());
//        System.out.println(b.manhattan());
//    }
}
