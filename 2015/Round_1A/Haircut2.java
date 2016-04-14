import java.io.*;
import java.util.*;

public class Haircut2 {
    private static InputReader in;
    private static PrintWriter out;
    public static boolean SUBMIT = false;
    public static final String NAME = "B-small-practice";
    private static int n;
    private static int k;
    private static int[] nums;

    public static void main(String[] args) throws IOException {
        if (SUBMIT) {
            in = new InputReader(new FileInputStream(new File(NAME + ".in")));
            out = new PrintWriter(new BufferedWriter(new FileWriter(NAME + ".out")));
        } else {
            in = new InputReader(System.in);
            out = new PrintWriter(System.out, true);
        }

        int numCases = in.nextInt();
        for (int test = 1; test <= numCases; test++) {
            n = in.nextInt();
            k = in.nextInt();
            nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = in.nextInt();
            }

            out.println("Case #" + test + ": " + solve());
        }

        out.close();
        System.exit(0);
    }

    private static int solve() {
        if (isSame(nums)) {
            return ((k - 1) % n) + 1;
        }

        int index = 0;
        int[] times = new int[n];
        boolean containsCycle = false;
        int cycle = 0;
        ArrayList<Integer> list = new ArrayList<Integer>();
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < k; i++) {
            if (i < n) {
                index = i;
                times[i] += nums[i];
                max = Math.max(max, times[i]);
                min = Math.min(min, times[i]);
            } else {
                int localmin = Integer.MAX_VALUE;
                for (int j = 0; j < n; j++) {
                    if (times[j] == min) {
                        index = j;
                        times[j] += nums[j];
                        max = Math.max(max, times[j]);
                        break;
                    }
                    localmin = Math.min(localmin, times[j]);
                }

                for (int j = index; j < n; j++) {
                    localmin = Math.min(localmin, times[j]);
                }

                min = localmin;

                if (max == min) {
                    cycle = i + 1;
                    containsCycle = true;
                    list.add(index);
                    out.println("cycle=" + cycle + "max=" + max + " min=" + min);
                    break;
                }
            }

            list.add(index);
        }

        if (containsCycle) {
            return list.get((k - 1) % cycle) + 1;
        } else {
            return index + 1;
        }

    }

    private static boolean isSame(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (nums[i] != nums[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private static class Node implements Comparable<Node>{
        int index;
        int val;

        public Node(int index, int val) {
            this.index = index;
            this.val = val;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Node) {
                Node node = (Node) obj;
                return node.index == this.index && node.val == this.val;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 41 * (41 + index) + val;
        }

        @Override
        public int compareTo(Node node) {
            if (this.val != node.val) {
                return this.val - node.val;
            } else {
                return this.index - node.index;
            }
        }
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
    }
}
