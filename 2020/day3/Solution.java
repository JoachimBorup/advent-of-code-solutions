import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long ans0 = 0, ans1 = 0, ans2 = 0, ans3 = 0, ans4 = 0;
        for (int i = 0; in.hasNext(); i++) {
            String line = in.nextLine();
            int l = line.length();
            if (line.charAt(i % l) == '#') ans0++;
            if (line.charAt((i * 3) % l) == '#') ans1++;
            if (line.charAt((i * 5) % l) == '#') ans2++;
            if (line.charAt((i * 7) % l) == '#') ans3++;
            if (i % 2 == 0 && line.charAt((i / 2) % l) == '#') ans4++;
        }
        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans0 * ans1 * ans2 * ans3 * ans4);
        in.close();
    }
}
