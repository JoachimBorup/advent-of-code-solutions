import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // double startTime = System.currentTimeMillis();
        int ans1, ans2;

        String[] input = in.nextLine().split(",");
        int[] numbers = new int[30000000];

        // Starting numbers
        for (int i = 1; i < input.length; i++) {
            numbers[Integer.parseInt(input[i - 1])] = i;
        }
        int number = Integer.parseInt(input[input.length - 1]);

        // getNumber() for solutions
        ans1 = getNumber(numbers, number, input.length, 2020);
        ans2 = getNumber(numbers, ans1, 2020, 30000000);

        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        // System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) / 1000 + " s");
        in.close();
    }

    private static int getNumber(int[] numbers, int number, int length, int last) {
        for (int i = length; i < last; i++) {
            if (numbers[number] == 0) {
                numbers[number] = i;
                number = 0;
            } else {
                int temp = number;
                number = i - numbers[number];
                numbers[temp] = i;
            }
        }
        return number;
    }
}
