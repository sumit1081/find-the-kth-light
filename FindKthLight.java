import java.io.*;
import java.util.*;

class FindKthLight {
    static ArrayList<Integer> onSwitches; // List of primes that represent On switches
    static long k; // Position that I have to find.

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            String stateOfBulbs = br.readLine();
            k = Long.parseLong(br.readLine());
            onSwitches = new ArrayList<>();
            for (int i = 0; i < 40; i++) {
                if (stateOfBulbs.charAt(i) == '1')
                    onSwitches.add(i + 1);
            }
            long low = k;
            long high = k * onSwitches.get(0);
            long ans = -1;
            while (low <= high) {
                long mid = low + (high - low) / 2;
                if (findRank(mid) >= k) {
                    ans = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            out.write("" + ans + "\n");
        }
        out.close();
    }

    // This function computes how many numbers less than val are multiples of any
    // one prime from onSwitches
    public static long findRank(long val) {
        int n = onSwitches.size();
        long rank = 0;

        // Uses standard Inclusion Exclusion Principle
        // I have n numbers, I will loop upto 2^n. 1<<n represents 2^n
        // Lets say we have 3 primes, 3 5 11. Now n is 3, so this loop goes from 1 to
        // 2^3-1.
        for (int i = 1; i < (1 << n); i++) {
            int count = 0;
            long product = 1;

            // I have total of n bits in any i. I am checking if jth bit is 1, then I am
            // mupliplying my product by jth prime number in the list. When i is 3, binary
            // is 011, so if my primes are 3, 5, 11, then my product will become 3 * 5
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    count++;
                    int prime = onSwitches.get(j);
                    product = product * prime;
                }
            }
            // This is inclusion exclusion principle. If count is odd, increase the result,
            // else decrease. When count is odd, then I have only odd number of primes.
            if (count % 2 == 1)
                rank += val / product;
            else
                rank += -1 * val / product;
        }
        return rank;
    }
}
