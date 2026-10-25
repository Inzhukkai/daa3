
import java.util.Arrays;

public class Zones {

    public static int[] kosaraju(Input.Slides s) {
        int n = s.n();

        int[][] from = Graphs.slidesFrom(s);
        int[][] into = Graphs.slidesInto(s);

        boolean[] seen = new boolean[n];
        int[] order = new int[n];
        int orderCount = 0;

        int[] stack = new int[n];
        int[] nextIndex = new int[n];

        // First pass
        for (int start = 0; start < n; start++) {
            if (seen[start]) {
                continue;
            }

            int top = 0;
            stack[0] = start;
            nextIndex[0] = 0;
            seen[start] = true;

            while (top >= 0) {
                int cell = stack[top];

                if (nextIndex[top] < from[cell].length) {
                    int edge = from[cell][nextIndex[top]];
                    nextIndex[top]++;

                    int next = s.to()[edge];

                    if (!seen[next]) {
                        seen[next] = true;
                        top++;
                        stack[top] = next;
                        nextIndex[top] = 0;
                    }
                } else {
                    order[orderCount] = cell;
                    orderCount++;
                    top--;
                }
            }
        }

        int[] zoneOf = new int[n];
        Arrays.fill(zoneOf, -1);

        int zone = 0;

        // Second pass on the reversed graph
        for (int i = orderCount - 1; i >= 0; i--) {
            int start = order[i];

            if (zoneOf[start] != -1) {
                continue;
            }

            int top = 0;
            stack[0] = start;
            zoneOf[start] = zone;

            while (top >= 0) {
                int cell = stack[top];
                top--;

                for (int edge : into[cell]) {
                    int next = s.from()[edge];

                    if (zoneOf[next] == -1) {
                        zoneOf[next] = zone;
                        top++;
                        stack[top] = next;
                    }
                }
            }

            zone++;
        }

        return zoneOf;
    }

    public static long[] routes(
            Input.Slides s, int[] zoneOf, int zoneCount) {

        int n = s.n();

        long[] stages = new long[zoneCount];
        long[] distance = new long[zoneCount];

        Arrays.fill(stages, 1);
        Arrays.fill(distance, Long.MAX_VALUE / 4);

        int startZone = zoneOf[0];
        int exitZone = zoneOf[n - 1];

        distance[startZone] = 0;

        // Find the number of stages
        for (int zone = 0; zone < zoneCount; zone++) {
            for (int i = 0; i < s.count(); i++) {
                int a = zoneOf[s.from()[i]];
                int b = zoneOf[s.to()[i]];

                if (a == zone && a != b) {
                    stages[b] = Math.max(
                            stages[b], stages[a] + 1);
                }
            }
        }

        long longest = 0;

        for (long stage : stages) {
            longest = Math.max(longest, stage);
        }

        // Find the cheapest route
        for (int zone = 0; zone < zoneCount; zone++) {
            if (distance[zone] >= Long.MAX_VALUE / 8) {
                continue;
            }

            for (int i = 0; i < s.count(); i++) {
                if (zoneOf[s.from()[i]] != zone) {
                    continue;
                }

                int next = zoneOf[s.to()[i]];

                if (next == zone) {
                    continue;
                }

                long cost = s.time()[i];

                distance[next] = Math.min(
                        distance[next], distance[zone] + cost);
            }
        }

        return new long[]{longest, distance[exitZone]};
    }
}
