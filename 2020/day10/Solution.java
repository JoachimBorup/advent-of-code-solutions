import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        List<Integer> adapters = new ArrayList<>();
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        while (in.hasNextInt()) {
            int next = in.nextInt();
            if (next < min) min = next;
            if (next > max) max = next;
            adapters.add(next);
        }
        adapters.add(min - 1);
        adapters.add(max + 3);
        Collections.sort(adapters);
        int length = adapters.size();

        int jolt1 = 0, jolt3 = 0, counter = 0;
        int[] seq = new int[5];
        for (int i = 1; i < length; i++) {
            int difference = adapters.get(i) - adapters.get(i - 1);
            if (difference == 1) {
                counter++;
                jolt1++;
            }
            else if (difference == 3) {
                seq[counter]++;
                counter = 0;
                jolt3++;
            }
        }
        
        int ans1 = jolt1 * jolt3;
        long ans2 = (long) (Math.pow(2, seq[2]) * Math.pow(4, seq[3]) * Math.pow(7, seq[4]));

        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }
}
