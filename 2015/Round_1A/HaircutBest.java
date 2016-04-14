import java.io.*;
import java.util.*;

public class HaircutBest {
    private static InputReader in;
    private static PrintWriter out;
    private static boolean SUBMIT = true;
    private static boolean isSmall = false;
    private static final String NAME = "B-" + (isSmall ? "small" : "large") + "-practice";
    private static int b;
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
            b = in.nextInt();
            n = in.nextInt();
            nums = new int[b];
            for (int i = 0; i < b; i++) {
                nums[i] = in.nextInt();
            }

            out.println("Case #" + test + ": " + solve());
        }

        out.close();
        System.exit(0);
    }


    private static long countServedCustomers(long time) {
        if (time < 0) return 0;
        long servedCustomers = 0;
        for (int i = 0; i < b; i++) {
            servedCustomers += (time / nums[i]) + 1;
        }
        return servedCustomers;
    }

    private static int solve() {
        long lo = 0;
        long hi = n * (isSmall ? 25 : 100000L);
        while (lo < hi) {
            long mid = (lo + hi) / 2;
            if (countServedCustomers(mid) < n) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        int toBeServed = n - (int) countServedCustomers(hi - 1);
        for (int i = 0; i < b; i++) {
            if (hi % nums[i] == 0) {
                toBeServed--;
                if (toBeServed == 0) {
                    return i + 1;
                }
            }
        }

        return -1;
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
