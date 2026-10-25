
import java.util.*;

public class Maze {

    public static int[] breakWalls(Input.Walls w) {
        DisjointSet dsu = new DisjointSet(w.cells());
        int[] result = new int[w.cells() - 1];
        int count = 0;

        for (int i = 0; i < w.count(); i++) {
            if (dsu.union(w.a()[i], w.b()[i])) {
                result[count] = i;
                count++;
            }
        }

        return Arrays.copyOf(result, count);
    }

    public static int[] cheapestMaze(Input.Walls w) {
        Integer[] order = new Integer[w.count()];

        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }

        Arrays.sort(order,
                Comparator.comparingInt(i -> w.cost()[i]));

        DisjointSet dsu = new DisjointSet(w.cells());
        int[] result = new int[w.cells() - 1];
        int count = 0;

        for (int i : order) {
            if (dsu.union(w.a()[i], w.b()[i])) {
                result[count] = i;
                count++;

                if (count == w.cells() - 1) {
                    break;
                }
            }
        }

        return Arrays.copyOf(result, count);
    }

    private record Edge(int to, int cost) {}

    public static long primCost(Input.Walls w) {
        int n = w.cells();

        List<Edge>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < w.count(); i++) {
            int a = w.a()[i];
            int b = w.b()[i];
            int cost = w.cost()[i];

            graph[a].add(new Edge(b, cost));
            graph[b].add(new Edge(a, cost));
        }

        boolean[] used = new boolean[n];

        PriorityQueue<Edge> queue = new PriorityQueue<>(
                Comparator.comparingInt(Edge::cost));

        used[0] = true;

        for (Edge edge : graph[0]) {
            queue.add(edge);
        }

        long total = 0;
        int count = 1;

        while (!queue.isEmpty() && count < n) {
            Edge edge = queue.poll();

            if (used[edge.to()]) {
                continue;
            }

            used[edge.to()] = true;
            count++;
            total += edge.cost();

            for (Edge next : graph[edge.to()]) {
                if (!used[next.to()]) {
                    queue.add(next);
                }
            }
        }

        return total;
    }
}
