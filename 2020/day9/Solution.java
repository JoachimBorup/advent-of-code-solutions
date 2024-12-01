import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long ans1 = 0, ans2 = 0;

        List<Long> numbers = new ArrayList<>();
        while (in.hasNextLong()) numbers.add(in.nextLong());
        int length = numbers.size();

        for (int i = 25; i < length; i++) {
            long z = numbers.get(i);
            boolean valid = false;
            outer:
            for (int j = i - 25; j < i; j++) {
                long x = numbers.get(j);
                for (int k = j + 1; k < i; k++) {
                    long y = numbers.get(k);
                    if (x + y == z) {
                        valid = true;
                        break outer;
                    }
                }
            }
            if (!valid) {
                ans1 = z;
                break;
            }
        }

        outer:
        for (int i = 0; i < length; i++) {
            long x = numbers.get(i), sum = x, min = x, max = x;
            for (int j = i + 1; j < length; j++) {
                long y = numbers.get(j);
                sum += y;
                if (y < min) min = y;
                if (y > max) max = y;
                if (sum > ans1) break;
                else if (sum == ans1) {
                    ans2 = min + max;
                    break outer;
                }
            }
        }

        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }
}
