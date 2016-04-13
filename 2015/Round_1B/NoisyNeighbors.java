import java.io.*;
import java.util.*;

public class NoisyNeighbors {
    private static InputReader in;
    private static PrintWriter out;
    public static boolean SUBMIT = false;
    public static final String NAME = "B-small-practice";
    private static int r;
    private static int c;
    private static int n;

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
            r = in.nextInt();
            c = in.nextInt();
            n = in.nextInt();
            out.println("Case #" + test + ": " + solve());
        }

        out.close();
        System.exit(0);
    }

    private static long solve() {
        int high = r * c;
        int low = (high + 1) / 2;

        if (n <= low) return 0;

        if (Math.min(r, c) == 1) {
            int max = Math.max(r, c);
            if (max % 2 == 1) {
                return 2 * (n - low);
            } else {
                return 1 + 2 * (n - 1 - low);
            }
        }
        int unhappiness = 0;
        int corners = 4;
        int sides = 2 * (r + c - 4);
        int middles = (r - 2) * (c - 2);

        int threshold = (r % 2 == 1 && c % 2 == 1) ? ((middles + 1) / 2 + 2 * ((r - 2) / 2 + (c - 2) / 2)) : ((high - corners) / 2);

        return (high - n <= threshold) ? topDown(high, low, corners, middles, sides) : buttomUp(high, low, corners, middles, sides);
    }

    private static int topDown(int high, int low, int corners, int middles, int sides) {
        int unhappiness = 2 * high - r - c;
        corners = ((r % 2 == 1) && (c % 2 == 1)) ? 0 : 2;
        middles = (middles + 1) / 2;
        sides = high - low - corners - middles;
        for (int i = high; i > n; i--) {
            if (middles > 0) {
                middles--;
                unhappiness -= 4;
            } else if (sides > 0) {
                sides--;
                unhappiness -= 3;
            } else {
                corners--;
                unhappiness -= 2;
            }
        }
        return unhappiness;
    }

    private static int buttomUp(int high, int low, int corners, int middles, int sides) {
        int unhappiness = 0;
        corners = ((r % 2 == 1) && (c % 2 == 1)) ? 0 : 2;
        middles /= 2;
        sides = high - low - corners - middles;
        for (int i = low; i < n; i++) {
            if (corners > 0) {
                corners--;
                unhappiness += 2;
            } else if (sides > 0) {
                sides--;
                unhappiness += 3;
            } else {
                middles--;
                unhappiness += 4;
            }
        }
        return unhappiness;
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
