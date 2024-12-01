import java.util.Scanner;

public class Day1_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int currentCalories = 0;
        int totalMax = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            if (line.isEmpty()) {
                currentCalories = 0;
            } else {
                currentCalories += Integer.parseInt(line);
            }

            totalMax = Math.max(totalMax, currentCalories);
        }

        System.out.println(totalMax);
        sc.close();
    }
}
