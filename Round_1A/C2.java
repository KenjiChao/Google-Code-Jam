import java.io.*;
import java.util.*;

public class C2 {
    private static InputReader in;
    private static PrintWriter out;
    public static boolean SUBMIT = false;
    public static final String NAME = "C-small-practice";
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
        int cycleVal = 0;
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < N; i++) {
            if (parent[parent[i]] == i && !set.contains(i)) {
                set.add(i);
                set.add(parent[i]);
                cycleVal += dfs(i, set) + dfs(parent[i], set);
                ret = Math.max(ret, cycleVal);
            }
        }

        for (int i = 0; i < N; i++) {
            if (!set.contains(i)) {
                HashSet<Integer> possible = new HashSet<Integer>();
                int j = i;
                possible.add(j);
                while (!possible.contains(parent[j]) && !set.contains(parent[j])) {
                    possible.add(parent[j]);
                    j = parent[j];
                }

                if (parent[j] == i) {
                    set.addAll(possible);
                    ret = Math.max(ret, possible.size());
                }
            }
        }

        return ret;
    }


    private static int dfs(int target, HashSet<Integer> set) {
        int size = child[target].size();
        if (size == 0) return 1;
        
        int max = 0;
        HashSet<Integer> copy = new HashSet<Integer>(set);
        copy.add(target);
        for (int i = 0; i < size; i++) {
            int val = (int) child[target].get(i);
            if (!copy.contains(val)) {
                max = Math.max(max, dfs(val, copy));
            }
        }

        return max + 1;
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
