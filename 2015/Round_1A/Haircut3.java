import java.io.*;
import java.util.*;

public class Haircut3 {
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


        PriorityQueue<Node> queue1 = new PriorityQueue<Node>();
        PriorityQueue<Node> queue2 = new PriorityQueue<Node>();
        for (int i = 0; i < n; i++) {
            queue1.add(new Node(i, 0));
        }

        int index = 0;

        for (int i = 0; i < k; i++) {
            if (queue1.isEmpty()) {
                Node node = queue2.poll();
                index = node.index;
                node.val = node.val + nums[index];
                queue1.add(node);
            } else if (queue2.isEmpty()) {
                Node node = queue1.poll();
                index = node.index;
                node.val = node.val + nums[index];
                queue2.add(node);
            } else {
                Node node1 = queue1.peek();
                Node node2 = queue2.peek();
                if (node1.compareTo(node2) < 0) {
                    node1 = queue1.poll();
                    index = node1.index;
                    node1.val = node1.val + nums[index];
                    queue2.add(node1);
                } else {
                    node2 = queue2.poll();
                    index = node2.index;
                    node2.val = node2.val + nums[index];
                    queue2.add(node2);
                }
            }
        }

        return index + 1;

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
