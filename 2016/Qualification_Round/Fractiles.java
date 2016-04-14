import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class Fractiles {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static long K;
    private static long C;
    private static long S;
    
    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(br.readLine());
        for (int i = 1; i <= T; i++) {
            readInput();
            ArrayList<Long> ret = solve();
            if (ret == null) {
                System.out.println("Case #" + i + ": IMPOSSIBLE");
            } else {
                System.out.println("Case #" + i + ":" + printAll(ret));
            }
        }
    }

    private static String printAll(ArrayList<Long> ret) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ret.size(); i++) {
            sb.append(" " + ret.get(i));
        }
        return sb.toString();
    }
    
    public static ArrayList<Long> solve() {
        if (C * S < K) {
            return null;
        }

        ArrayList<Long> list = new ArrayList<Long>();
        if (C == 1) {
            for (int i = 1; i <= K; i++) {
                list.add((long) i);
            }
            return list;
        }

        for (int i = 1; i <= K; i += C) {
            long p = 1;
            for (int j = 0; j < C; j++) {
                p = (p - 1) * K + Math.min((long) (i + j), K);
            }

            list.add(p);
        }

        return list;
    }
            
    private static void readInput() throws Exception {
        StringTokenizer tokens = new StringTokenizer(br.readLine());
        K = Long.parseLong(tokens.nextToken());
        C = Long.parseLong(tokens.nextToken());
        S = Long.parseLong(tokens.nextToken());
    }
}
