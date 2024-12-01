import java.util.Scanner;

public class Day10_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int signalStrengths = 0;
        int cycle = 0;
        int x = 1;

        while (sc.hasNext()) {
            String instruction = sc.next();
            int cyclesLeft = instruction.equals("noop") ? 1 : 2;

            while (cyclesLeft > 0) {
                cyclesLeft--;
                cycle++;

                if (cycle % 40 == 20) {
                    signalStrengths += cycle * x;
                }
            }

            if (instruction.equals("addx")) {
                x += sc.nextInt();
            }
        }

        System.out.println(signalStrengths);
        sc.close();
    }
}
