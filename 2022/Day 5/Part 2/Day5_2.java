import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Day5_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Stack<Character>> stacks = new ArrayList<Stack<Character>>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            System.err.println(line);
            if (line.charAt(1) == '1') {
                break;
            }

            for (int i = 1, j = 0; i < line.length(); i += 4, j++) {
                if (stacks.size() <= j) {
                    stacks.add(new Stack<Character>());
                    System.err.println("Created stack " + j);
                }

                if (line.charAt(i) != ' ') {
                    stacks.get(j).push(line.charAt(i));
                    System.err.println("Pushed " + line.charAt(i) + " to stack " + j);
                }
            }
        }
        sc.nextLine();

        for (Stack<Character> stack : stacks) {
            Collections.reverse(stack);
            System.err.println(stack);
        }

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            System.err.println(line);

            String[] instructions = line.split("[a-z\\s]+");
            int amount = Integer.parseInt(instructions[1]);
            int from = Integer.parseInt(instructions[2]);
            int to = Integer.parseInt(instructions[3]);
            System.err.println("Moving " + amount + " crates from " + from + " to " + to);

            Stack<Character> movedCrates = new Stack<Character>();

            for (int i = 0; i < amount; i++) {
                System.err.println("Moving " + stacks.get(from - 1).peek() + " from " + from + " to " + to);
                movedCrates.push(stacks.get(from - 1).pop());

                for (Stack<Character> stack : stacks) {
                    System.err.println(stack);
                }
            }

            for (int i = 0; i < amount; i++) {
                stacks.get(to - 1).push(movedCrates.pop());
            }
        }

        for (Stack<Character> stack : stacks) {
            System.out.print(stack.peek());
        }

        System.out.println();
        sc.close();
    }
}
