import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day21_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Monkey> monkeys = new HashMap<>();

        while (sc.hasNext()) {
            String[] input = sc.nextLine().split(": ");
            monkeys.put(input[0], new Monkey(input[1]));
        }

        System.out.println(monkeys.get("root").getValue(monkeys));
        sc.close();
    }

    private static class Monkey {
        private String left, right;
        private char operator;
        private long value;

        public Monkey(String input) {
            if (input.matches("\\d+")) {
                value = Integer.parseInt(input);
            } else {
                String[] split = input.split(" ");
                left = split[0];
                right = split[2];
                operator = split[1].charAt(0);
                value = -1;
            }
        }

        public long getValue(Map<String, Monkey> monkeys) {
            if (value == -1) {
                long leftValue = monkeys.get(left).getValue(monkeys);
                long rightValue = monkeys.get(right).getValue(monkeys);

                this.value = switch (operator) {
                    case '+' -> leftValue + rightValue;
                    case '-' -> leftValue - rightValue;
                    case '*' -> leftValue * rightValue;
                    case '/' -> leftValue / rightValue;
                    default -> throw new IllegalStateException("Unexpected operator: " + operator);
                };
            }

            return value;
        }
    }
}
