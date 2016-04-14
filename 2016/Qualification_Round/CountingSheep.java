import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class CountingSheep {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static BigInteger n;
    
    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(br.readLine());
        for (int i = 1; i <= T; i++) {
            readInput();
            long ret = solve();
            if (ret == - 1) {
                System.out.println("Case #" + i + ": INSOMNIA");
            } else {
                System.out.println("Case #" + i + ": " + ret);
            }
        }
    }
    
    public static long solve() {
        if (n.intValue() == 0) {
            return -1L;
        }
        BigInteger ret = BigInteger.ZERO;
        HashSet<Character> set = new HashSet<Character>();
        while (set.size() < 10) {
            ret = ret.add(n);
            char[] arr = ret.toString().toCharArray();
            for (int i = 0; i < arr.length; i++) {
                char ch = arr[i];
                if (!set.contains(ch)) {
                    set.add(ch);
                }
            }
            
        }
        return ret.longValue();
    }
            
    private static void readInput() throws Exception {
        n = new BigInteger(br.readLine());
    }
}
