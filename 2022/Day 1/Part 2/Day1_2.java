import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day1_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        List<Integer> elves = new ArrayList<>();
        int calories = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                elves.add(calories);
                calories = 0;
            } else {
                calories += Integer.parseInt(line);
            }
        }

        elves.add(calories);
        calories = 0;

        Collections.sort(elves, Collections.reverseOrder());
        for (int i = 0; i < 3; i++) {
            calories += elves.get(i);
        }

        System.out.println(calories);
        sc.close();
    }
}
