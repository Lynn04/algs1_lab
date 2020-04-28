import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int steps;
    private boolean solvable;
    private Stack<Board> solution = new Stack<Board>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // Corner case: Throw an IllegalArgumentException in the constructor if the argument is null.
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<Node> pq = new MinPQ<Node>();
        pq.insert(new Node(initial, 0, null));
        pq.insert(new Node(initial.twin(), 0, null));

        Node current = pq.delMin(); // 如果twin先实现，说明board无解
        while (!current.board.isGoal()) {
            for (Board neighbor : current.board.neighbors()) {
                // critical optimization: 如果这个neighbor和母节点的board一样，就不要重复添加了
                if (current.prev == null || !neighbor.equals(current.prev.board)) {
                    pq.insert(new Node(neighbor, current.moves + 1, current));
                }
            }
            current = pq.delMin();
        }
        steps = current.moves;
        while (current != null) {
            solution.push(current.board);
            current = current.prev;
        }
        if (solution.pop().equals(initial)) {
            solvable = true;
            solution.push(initial);
        } else {
            solvable = false;
        }

    }

    private class Node implements Comparable<Node> {
        Node prev;
        Board board;
        int moves;
        int priority;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(Node that) {
            return this.priority - that.priority;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable()) {
            return steps;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (isSolvable()) {
            return solution;
        } else {
            return null;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}

