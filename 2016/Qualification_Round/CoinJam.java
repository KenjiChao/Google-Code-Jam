import java.io.*;
import java.util.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class CoinJam {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int N;
    private static int J;
    private static BigInteger[] base;
    private static BigInteger largestPrimeToCheck = new BigInteger("11");
    
    public static void main(String[] args) throws Exception {
        base = new BigInteger[11];
        for (int i = 0; i < base.length; i++) {
            base[i] = new BigInteger(String.valueOf(i));
        }

        int T = Integer.parseInt(br.readLine());
        for (int i = 1; i <= T; i++) {
            readInput();
            System.out.println("Case #" + i + ": ");
            printCoins(N, J);
        }
        
    }

    private static BigInteger initBigInteger(int N) {
        BigInteger bi = BigInteger.ONE;
        for (int i = 0; i < N - 1; i++) {
            bi = bi.multiply(base[2]);
        }
        bi = bi.add(BigInteger.ONE);

        return bi;
    }

    private static void printCoins(int N, int J) {
        BigInteger bi = initBigInteger(N);

        while (J > 0) {
            String[] ret = solve(printBits(bi).toCharArray());
            if (ret != null) {
                printAll(bi, ret);
                J--;
            }
            bi = bi.add(base[2]);
        }

    }

    private static String printBits(BigInteger bi) {
        StringBuilder sb = new StringBuilder();
        for (int i = bi.bitLength() - 1; i >= 0; i--) {
            sb.append(bi.testBit(i) ? '1' : '0');
        }
        return sb.toString();
    }

    private static void printAll(BigInteger bi, String[] ret) {
        StringBuilder sb = new StringBuilder();
        sb.append(printBits(bi));
        for (int i = 2; i <= 10; i++) {
            sb.append(" " + ret[i]);
        }
        System.out.println(sb.toString());
    }
    
    public static String[] solve(char[] arr) {
        String[] divisor = new String[11];
        for (int i = 2; i <= 10; i++) {
            BigInteger bi = BigInteger.ZERO;
            for (int j = 0; j < arr.length; j++) {
                bi = bi.multiply(base[i]).add(arr[j] == '1' ? BigInteger.ONE : BigInteger.ZERO);
            }

            divisor[i] = getDivisor(bi);
            if (divisor[i] == null) {
                return null;
            }
        }
        return divisor;
    }


    private static String getDivisor(BigInteger bi) {
        for (BigInteger divisor = new BigInteger("2");
            divisor.subtract(largestPrimeToCheck).signum() < 1;
            divisor = divisor.add(BigInteger.ONE)) {
            if (bi.remainder(divisor).equals(BigInteger.ZERO)) {
                return divisor.toString();
            }
        }
        return null;
    }

    private static void readInput() throws Exception {
        StringTokenizer token = new StringTokenizer(br.readLine());
        N = Integer.parseInt(token.nextToken());
        J = Integer.parseInt(token.nextToken());
    }
}
