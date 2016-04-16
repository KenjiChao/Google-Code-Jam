import java.io.*;
import java.util.*;

public class C {
    private static InputReader in;
    private static PrintWriter out;
    public static boolean SUBMIT = false;
    public static final String NAME = "C-large-practice";
    private static int[] parent;
    private static ArrayList[] child;
    private static int N;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        if (args != null && args.length > 0) {
            SUBMIT = args[0].equals("true") ? true : false;
        }

        if (SUBMIT) {
            in = new InputReader(new FileInputStream(new File(NAME + ".in")));
            out = new PrintWriter(new BufferedWriter(new FileWriter(NAME + ".out")));
        } else {
            in = new InputReader(System.in);
            out = new PrintWriter(System.out, true);
        }

        int numCases = in.nextInt();
        for (int test = 1; test <= numCases; test++) {
            N = in.nextInt();
            parent = new int[N];
            child = new ArrayList[N];
            for (int i = 0; i < N; i++) {
                child[i] = new ArrayList<Integer>();
            }

            for (int i = 0; i < N; i++) {
                parent[i] = in.nextInt() - 1;
                child[parent[i]].add(i);
            }
            out.println("Case #" + test + ": " + solve());
        }

        out.close();
        System.exit(0);
    }

    private static int solve() {
        int ret = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(10, Collections.reverseOrder());
        HashSet<Integer> closed = new HashSet<Integer>();
        for (int i = 0; i < N; i++) {
            if (!closed.contains(i)) {
                ret = Math.max(ret, dfs(i, queue, closed));
            }
        }

        if (queue.size() < 2) {
            return ret;
        } else {
            int q = 0;
            while (!queue.isEmpty()) {
                q += (int) queue.poll();
            }
            return Math.max(ret, q);
        }
    }

    private static int dfs(int root, PriorityQueue<Integer> queue, HashSet<Integer> closed) {
        int max = 0;
        int ret = 0;
        int rootParent = parent[root];
        boolean findRootParent = child[root].contains(rootParent);
        LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();
        set.add(root);

        if (findRootParent) {
            ret = dfsHelper(rootParent, set, findRootParent);
        }

        for (int i = 0; i < child[root].size(); i++) {
            if ((int) child[root].get(i) != rootParent) {
                max = Math.max(max, dfsHelper((int) child[root].get(i), set, findRootParent));
            }
        }

        if (findRootParent) {
            queue.add(ret + max + 1);
            closed.add(rootParent);
        }


        return ret + max + 1;
    }

    private static int dfsHelper(int node, LinkedHashSet<Integer> set, boolean findRootParent) {
        Iterator iter = set.iterator();
        int root = (int) iter.next();
        int size = child[node].size();

        if (size == 0) {
            return findRootParent ? 1 : (parent[root] == node ? 1 : 0);
        }

        int ret = 0;
        LinkedHashSet<Integer> copy = new LinkedHashSet<Integer>(set);
        copy.add(node);

        for (int i = 0; i < child[node].size(); i++) {
            if (!copy.contains(child[node].get(i))) {
                ret = Math.max(ret, dfsHelper((int) child[node].get(i), copy, findRootParent));
            }
        }


        return ret == 0 ? (parent[root] == node ? 1 : 0) : (ret + 1);
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}
