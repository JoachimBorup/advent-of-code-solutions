import java.util.Scanner;

public class Day6_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        final int DAYS = 80;
        int[] fish = new int[9];

        for (String input : sc.nextLine().split(",")) {
            fish[Integer.parseInt(input)]++;
        }

        for (int i = 0; i < DAYS; i++) {
            int[] updatedFish = new int[fish.length];
            
            for (int j = 0; j < updatedFish.length; j++) {
                if (j == 6) updatedFish[j] = fish[0] + fish[7];
                else if (j == 8) updatedFish[j] = fish[0];
                else updatedFish[j] = fish[j + 1];
            }

            fish = updatedFish;
        }

        int totalFish = 0;

        for (int i = 0; i < fish.length; i++) {
            totalFish += fish[i];
        }

        System.out.println(totalFish);
        sc.close();
    }
}
