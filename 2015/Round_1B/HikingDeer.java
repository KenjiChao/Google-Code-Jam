import java.io.*;
import java.util.*;

public class HikingDeer {
    private static InputReader in;
    private static PrintWriter out;
    public static boolean SUBMIT = false;
    public static final String NAME = "C-small-practice-1";
    private static ArrayList<Human> human;

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
            // read input
            human = new ArrayList<>();
            int groups = in.nextInt();
            for (int i = 0; i < groups; i++) {
                long start = in.nextLong();
                int people = in.nextInt();
                long pace = in.nextLong();
                for (int j = 0; j < people; j++, pace++) {
                    human.add(new Human(start, pace));
                }
            }

            out.println("Case #" + test + ": " + solve());
        }

        out.close();
        System.exit(0);
    }

    private static int solve() {
        if (human.size() == 1 || isTheSamePace()) return 0;

        Human human1 = human.get(0);
        Human human2 = human.get(1);

        // double firstTimeOnOrigin = Math.max((double) human1.start / (double) human1.pace, (double) human2.start / (double) human2.pace);
        // double secondTimeOnOrigin = Math.min((double) (360 + human1.start) / (double) human1.pace, (double) (360 + human2.start) / (double) human2.pace);
        long firstTimeOnOrigin = Math.max((360 - human1.start) * human1.pace, (360 - human2.start) * human2.pace);
        long secondTimeOnOrigin = Math.min((720 - human1.start) *  human1.pace, (720 - human2.start) * human2.pace);

        if (firstTimeOnOrigin < secondTimeOnOrigin) {
            return 0;
        }

        return 1;
    }

    private static boolean isTheSamePace() {
        long pace = human.get(0).pace;
        for (int i = 1; i < human.size(); i++) {
            if (pace != human.get(i).pace) {
                return false;
            }
        }
        return true;
    }

    private static class Human {
        long start;
        long pace;

        public Human(long start, long pace) {
            this.start = start;
            this.pace = pace;
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

        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}
