import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int ans1 = 0, ans2 = 0;
        while (in.hasNextLine()) {
            String[] input = in.nextLine().split("[ -]");
            char letter = input[2].charAt(0);
            char[] password = input[3].toCharArray();
            int l = Integer.parseInt(input[0]);
            int r = Integer.parseInt(input[1]);
            char first = password[l - 1];
            char second = password[r - 1];

            int count = 0;
            for (char ch : password) {
                if (ch == letter) count++;
            }
            if ((first != letter || second != letter) &&
                (first == letter || second == letter)) {
                ans2++;
            }

            if (l <= count && count <= r) ans1++;
        }
        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }
}
