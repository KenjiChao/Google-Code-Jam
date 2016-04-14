import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class Pancakes {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static char[] arr;
    
    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(br.readLine());
        for (int i = 1; i <= T; i++) {
            readInput();
            System.out.println("Case #" + i + ": " + solve());
        }
    }
    
    public static int solve() {
        int change = 0;
        int len = arr.length;

        for (int i = 1; i < len; i++) {
            if (arr[i] != arr[i - 1]) {
                change++;
            }
        }

        if (arr[len - 1] == '-') {
            change++;
        }

        return change;
    }
            
    private static void readInput() throws Exception {
        arr = br.readLine().toCharArray();
    }
}
