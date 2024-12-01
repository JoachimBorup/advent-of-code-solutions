import java.util.Scanner;

public class Day8_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int easyDigits = 0;

        while (sc.hasNextLine()) {
            for (String outputValue : sc.nextLine().split(" \\| ")[1].split(" ")) {
                int segments = outputValue.length();

                if (2 <= segments && segments <= 4 || segments == 7) {
                    easyDigits++;
                }
            }
        }

        System.out.println(easyDigits);
        sc.close();
    }
}
