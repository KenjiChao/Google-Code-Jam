import java.io.*;
import java.util.*;

public class CounterCulture {
    private static InputReader in;
    private static PrintWriter out;
    public static boolean SUBMIT = true;
    public static final String NAME = "A-large-practice";
    private static long n;
    private static int[] nums;
    private static int len = 0;
    private static long[] table = new long[15];

    public static void main(String[] args) throws IOException {
        if (SUBMIT) {
            in = new InputReader(new FileInputStream(new File(NAME + ".in")));
            out = new PrintWriter(new BufferedWriter(new FileWriter(NAME + ".out")));
        } else {
            in = new InputReader(System.in);
            out = new PrintWriter(System.out, true);
        }


        table[1] = 10;
        long base = 9;
        for (int i = 2; i < table.length; i++) {
            table[i] = table[i - 1] + base;
            if (i % 2 == 1) {
                base = base * 10 + 9;
            }
            table[i] += base + 1;
        }

        int numCases = in.nextInt();
        for (int test = 1; test <= numCases; test++) {
            n = in.nextLong();
            toIntArray(n);
            out.println("Case #" + test + ": " + solve());
        }

        out.close();
        System.exit(0);
    }

    private static void toIntArray(long n) {
        long localN = n;
        len = 0;
        while (localN > 0) {
            len++;
            localN /= 10;
        }

        localN = n;
        nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = (int) (localN % 10);
            localN /= 10;
        }
    }

    private static boolean isPowerOfTen() {
        for (int i = 0; i < len - 1; i++) {
            if (nums[i] != 0) return false;
        }

        return nums[len - 1] == 1;
    }

    private static long solve() {
        if (n <= 19) return n;
        if (isPowerOfTen()) return table[len - 1];

        long count = table[len - 1];

        int left = len / 2;
        int right = len - left;
        boolean isRightHalfAllZero = isRightHalfAllZero(right);
        long leftCount = 0;
        long rightCount = 0;
        boolean shouldRotate = true;


        if (isRightHalfAllZero) {
            n--;
            toIntArray(n);
        }

        for (int i = right - 1; i >= 0; i--) {
            rightCount = rightCount * 10 + nums[i];
        }

        for (int i = 0; i < left; i++) {
            leftCount = leftCount * 10 + nums[right + i];
        }

        if (leftCount == 1) shouldRotate = false;

        if (shouldRotate) {
            count += leftCount + rightCount;
        } else {
            count += rightCount;
        }

        return isRightHalfAllZero ? count + 1 : count;

    }

    private static boolean isRightHalfAllZero(int digitsToCheck) {
        for (int i = 0; i < digitsToCheck; i++) {
            if (nums[i] != 0) return false;
        }

        return true;
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
