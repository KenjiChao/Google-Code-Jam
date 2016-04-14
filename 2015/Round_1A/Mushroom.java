import java.io.*;
import java.util.*;

public class Mushroom {
    private static InputReader in;
    private static PrintWriter out;
    public static boolean SUBMIT = true;
    public static final String NAME = "A-large-practice";
    private static int n;
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
            nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = in.nextInt();
            }
            out.println("Case #" + test + ": " + firstSol() + " " + secondSol());
        }

        out.close();
        System.exit(0);
    }

    private static int firstSol() {
        int ret = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) {
                ret += nums[i - 1] - nums[i];
            }
        }
        return ret;

    }

    private static int secondSol() {
        int rate = 0;
        int ret = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) {
                rate = Math.max(rate, nums[i - 1] - nums[i]);
            }
        }

        for (int i = 0; i < n - 1; i++) {
            ret += Math.min(rate, nums[i]);
        }

        return ret;
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
