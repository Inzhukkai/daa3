
public class DisjointSet {
    private final int[] parent;
    private final int[] size;
    private int sets;

    public DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];
        sets = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    public boolean union(int a, int b) {
        int ra = find(a);
        int rb = find(b);

        if (ra == rb) {
            return false;
        }

        if (size[ra] < size[rb]) {
            int temp = ra;
            ra = rb;
            rb = temp;
        }

        parent[rb] = ra;
        size[ra] += size[rb];
        sets--;

        return true;
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public int count() {
        return sets;
    }
}
