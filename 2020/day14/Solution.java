import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long ans1 = 0, ans2 = 0;

        // Maps where each value is mapped to an address
        Map<Integer, Long> values1 = new HashMap<>();
        Map<Long, Integer> values2 = new HashMap<>();
        char[] mask = {};
        int bits = 36;

        while (in.hasNextLine()) {
            String[] nextLine = in.nextLine().split(" = ");
            String action = nextLine[0];
            if (action.equals("mask")) {
                mask = nextLine[1].toCharArray();
            } else {
                // Left- and right-hand side integers
                int leftInt = Integer.parseInt(action.substring(4, action.length() - 1));
                int rightInt = Integer.parseInt(nextLine[1]);

                // Convert integers to temporary binary strings (missing leading zeroes)
                char[] temp1 = Integer.toBinaryString(rightInt).toCharArray();
                char[] temp2 = Integer.toBinaryString(leftInt).toCharArray();

                // Instantiate values, and fill with zeroes
                char[] value1 = new char[bits];
                char[] value2 = new char[bits];
                Arrays.fill(value1, '0');
                Arrays.fill(value2, '0');

                // Copy temporary values onto actual values (with leading zeroes)
                System.arraycopy(temp1, 0, value1, bits - temp1.length, temp1.length);
                System.arraycopy(temp2, 0, value2, bits - temp2.length, temp2.length);

                // Values overwritten by mask
                for (int i = 0; i < bits; i++) {
                    if (mask[i] != 'X') value1[i] = mask[i];
                    if (mask[i] != '0') value2[i] = mask[i];
                }

                // Map value to address (part 1)
                values1.put(leftInt, Long.parseLong(new String(value1), 2));

                // Map value to addresses (part 2)
                solveFloating(value2, values2, rightInt);
            }
        }

        // Add up the values to get solutions
        for (Map.Entry<Integer, Long> entry : values1.entrySet()) {
            ans1 += entry.getValue();
        }
        for (Map.Entry<Long, Integer> entry : values2.entrySet()) {
            ans2 += entry.getValue();
        }

        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }

    private static void solveFloating(char[] address, Map<Long, Integer> values, int value) {
        for (int i = 0; i < address.length; i++) {
            if (address[i] == 'X') {
                char[] address1 = address.clone();
                char[] address2 = address.clone();
                address1[i] = '0';
                address2[i] = '1';
                solveFloating(address1, values, value);
                solveFloating(address2, values, value);
                return;
            }
        }
        values.put(Long.parseLong(new String(address), 2), value);
    }
}
