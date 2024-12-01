import java.util.Scanner;

public class Day10_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] row = new char[40];
        int cycle = 0;
        int x = 1;

        while (sc.hasNext()) {
            String instruction = sc.next();
            int cyclesLeft = instruction.equals("noop") ? 1 : 2;

            while (cyclesLeft > 0) {
                row[cycle] = x - 1 <= cycle && cycle <= x + 1 ? '#' : '.';
                cyclesLeft--;
                cycle++;

                if (cycle % 40 == 0) {
                    System.out.println(row);
                    cycle = 0;
                }
            }

            if (instruction.equals("addx")) {
                x += sc.nextInt();
            }
        }

        sc.close();
    }
}
