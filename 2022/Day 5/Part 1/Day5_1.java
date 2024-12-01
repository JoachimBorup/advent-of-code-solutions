import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Day5_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Stack<Character>> stacks = parseStacks(sc);
        sc.nextLine();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            System.err.println(line);

            String[] instructions = line.split("[\\D]+");
            int amount = Integer.parseInt(instructions[1]);
            int from = Integer.parseInt(instructions[2]);
            int to = Integer.parseInt(instructions[3]);

            for (int i = 0; i < amount; i++) {
                char toMove = stacks.get(from - 1).pop();
                stacks.get(to - 1).push(toMove);
            }
        }

        for (Stack<Character> stack : stacks) {
            System.out.print(stack.peek());
        }

        System.out.println();
        sc.close();
    }

    private static List<Stack<Character>> parseStacks(Scanner sc) {
        List<Stack<Character>> stacks = new ArrayList<Stack<Character>>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.charAt(1) == '1') {
                break;
            }

            for (int i = 1, j = 0; i < line.length(); i += 4, j++) {
                if (stacks.size() <= j) {
                    stacks.add(new Stack<Character>());
                }

                if (line.charAt(i) != ' ') {
                    stacks.get(j).push(line.charAt(i));
                }
            }
        }

        for (Stack<Character> stack : stacks) {
            Collections.reverse(stack);
        }

        return stacks;
    }
}
