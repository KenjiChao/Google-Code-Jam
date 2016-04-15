import java.io.*;
import java.util.*;

public class HikingDeer {
    private static InputReader in;
    private static PrintWriter out;
    public static boolean SUBMIT = false;
    public static final String NAME = "C-large-practice";
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
            // out.println("groups=" + groups);
            for (int i = 0; i < groups; i++) {
                long start = in.nextLong();
                int people = in.nextInt();
                long pace = in.nextLong();
                // if (i == 0) out.println(start + " " + people + " " + pace);
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
        int len = human.size();
        long[] firstTimeOnOrigin = new long[len];
        long[] pace = new long[len];
        PriorityQueue<Long> queue = new PriorityQueue<>();
        int ret = len - 1;

        for (int i = 0; i < len; i++) {
            Human h = human.get(i);
            firstTimeOnOrigin[i] = (360 - h.start) * h.pace;
            pace[i] = firstTimeOnOrigin[i];
        }

        ArrayIndexComparator cmp = new ArrayIndexComparator(firstTimeOnOrigin);
        Integer[] indices = cmp.createIndexArray();
        Arrays.sort(indices, cmp);


        for (int i = 1; i < len; i++) {
            for (int j = 0; j < len; j++) {
                Human h = human.get(j);
                pace[j] += 360 * h.pace;
                if (pace[j] <= firstTimeOnOrigin[indices[len - 1]])
                    queue.add(pace[j]);
            }
            if (queue.size() == 0) {
                ret = Math.min(ret, i - 1);
                break;
            }

            long val = queue.poll();
            int largeElements = binarySearchLargeElements(indices, firstTimeOnOrigin, val);
            ret = Math.min(ret, largeElements + (i - 1));
            // if (val > firstTimeOnOrigin[indices[len - 1]]) break;
        }

        return ret;
    }

    private static int binarySearchLargeElements(Integer[] indices, long[] value, long target) {
        int len = indices.length;
        int low = 0;
        int high = len;
        while (low + 1 < high) {
            int mid = (low + high) / 2;
            if (value[indices[mid]] < target) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return len - high;
    }

    private static class ArrayIndexComparator implements Comparator<Integer> {
        long[] nums;

        public ArrayIndexComparator(long[] nums) {
            this.nums = nums;
        }

        public Integer[] createIndexArray() {
            Integer[] indices = new Integer[nums.length];
            for (int i = 0; i < indices.length; i++) {
                indices[i] = i;
            }
            return indices;
        }

        @Override
        public int compare(Integer i1, Integer i2) {
            if (nums[i1] < nums[i2]) {
                return -1;
            } else if (nums[i1] > nums[i2]) {
                return 1;  
            } else {
                return i1.compareTo(i2);
            }
        } 
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
