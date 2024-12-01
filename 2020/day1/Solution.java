import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        int ans1 = 0, ans2 = 0;
        Scanner in = new Scanner(System.in);
        List<Integer> input = new ArrayList<>();
        while (in.hasNext()) input.add(in.nextInt());
        for (int i = 0; i < input.size(); i++) {
            int x = input.get(i);
            for (int j = i + 1; j < input.size(); j++) {
                int y = input.get(j);
                if (x + y == 2020) ans1 = x * y;
                for (int k = j + 1; k < input.size(); k++) {
                    int z = input.get(k);
                    if (x + y + z == 2020) ans2 = x * y * z;
                }
            }
        }
        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }
}
