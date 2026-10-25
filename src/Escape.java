
import java.util.Arrays;

public class Escape {

    public record Result(int[] path, int visited, int deadEnds) {}

    public static Result escape(Input.Walls w, int[] broken) {
        int cols = w.cols();
        int n = w.cells();
        int exit = n - 1;

        int[][] graph = Graphs.open(w, broken);

        int deadEnds = 0;

        for (int[] neighbours : graph) {
            if (neighbours.length == 1) {
                deadEnds++;
            }
        }

        boolean[] seen = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        int[] stackCell = new int[n];
        int[] stackNext = new int[n];

        int top = 0;
        int visited = 1;

        stackCell[0] = 0;
        seen[0] = true;

        boolean found = exit == 0;

        while (top >= 0 && !found) {
            int cell = stackCell[top];
            boolean moved = false;

            while (stackNext[top] < 4) {
                int direction = stackNext[top]++;
                int next;

                if (direction == 0) {
                    next = cell - cols;
                } else if (direction == 1) {
                    next = cell + 1;
                } else if (direction == 2) {
                    next = cell + cols;
                } else {
                    next = cell - 1;
                }

                if (next < 0 || next >= n) {
                    continue;
                }

                int rowDiff = Math.abs(cell / cols - next / cols);
                int colDiff = Math.abs(cell % cols - next % cols);

                if (rowDiff + colDiff != 1) {
                    continue;
                }

                if (seen[next] || !contains(graph[cell], next)) {
                    continue;
                }

                seen[next] = true;
                visited++;
                parent[next] = cell;

                if (next == exit) {
                    found = true;
                    break;
                }

                top++;
                stackCell[top] = next;
                stackNext[top] = 0;
                moved = true;
                break;
            }

            if (found) {
                break;
            }

            if (!moved && stackNext[top] >= 4) {
                top--;
            }
        }

        if (!found) {
            return new Result(new int[0], visited, deadEnds);
        }

        int length = 1;

        for (int cell = exit; cell != 0; cell = parent[cell]) {
            length++;
        }

        int[] path = new int[length];
        int cell = exit;

        for (int i = length - 1; i >= 0; i--) {
            path[i] = cell;

            if (cell != 0) {
                cell = parent[cell];
            }
        }

        return new Result(path, visited, deadEnds);
    }

    private static boolean contains(int[] array, int value) {
        for (int x : array) {
            if (x == value) {
                return true;
            }
        }

        return false;
    }
}
