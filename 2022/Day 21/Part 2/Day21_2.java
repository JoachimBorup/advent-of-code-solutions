import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day21_2 {
    private static final String ROOT = "root";
    private static final String ME = "humn";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Monkey> monkeys = new HashMap<>();

        while (sc.hasNext()) {
            String[] input = sc.nextLine().split(": ");
            monkeys.put(input[0], new Monkey(input));
        }

        Monkey root = monkeys.get(ROOT);
        root.computeValues(monkeys);
        root.equalityCheck(monkeys);

        System.out.println(monkeys.get(ME).getValue());
        sc.close();
    }

    private static class Monkey {
        private final String name;
        private String left, right;
        private char operator;
        private long value;

        public Monkey(String[] input) {
            this.name = input[0];

            if (input[1].matches("\\d+")) {
                this.value = Integer.parseInt(input[1]);
            } else {
                String[] split = input[1].split(" ");
                this.left = split[0];
                this.right = split[2];
                this.operator = split[1].charAt(0);
                this.value = -1;
            }
        }

        public long getValue() {
            return value;
        }

        public long computeValues(Map<String, Monkey> monkeys) {
            if (this.name.equals(ME)) {
                this.value = -1;
                return -1;
            }

            if (this.value == -1) {
                long leftValue = monkeys.get(this.left).computeValues(monkeys);
                long rightValue = monkeys.get(this.right).computeValues(monkeys);

                if (leftValue == -1 || rightValue == -1) {
                    this.value = -1;
                    return -1;
                }

                this.value = switch (this.operator) {
                    case '+' -> leftValue + rightValue;
                    case '-' -> leftValue - rightValue;
                    case '*' -> leftValue * rightValue;
                    case '/' -> leftValue / rightValue;
                    default -> throw new IllegalStateException("Unexpected operator: " + this.operator);
                };
            }

            return this.value;
        }

        public void equalityCheck(Map<String, Monkey> monkeys) {
            Monkey left = monkeys.get(this.left);
            Monkey right = monkeys.get(this.right);

            if (left.value == -1) {
                left.equalityCheck(monkeys, right.value);
            } else {
                right.equalityCheck(monkeys, left.value);
            }
        }

        public void equalityCheck(Map<String, Monkey> monkeys, long x) {
            this.value = x;

            if (this.name.equals(ME)) {
                return;
            }

            Monkey left = monkeys.get(this.left);
            Monkey right = monkeys.get(this.right);

            if (left.value == -1) {
                switch (this.operator) {
                    case '+' -> left.equalityCheck(monkeys, x - right.value);
                    case '-' -> left.equalityCheck(monkeys, x + right.value);
                    case '*' -> left.equalityCheck(monkeys, x / right.value);
                    case '/' -> left.equalityCheck(monkeys, x * right.value);
                }
            } else {
                switch (this.operator) {
                    case '+' -> right.equalityCheck(monkeys, x - left.value);
                    case '-' -> right.equalityCheck(monkeys, left.value - x);
                    case '*' -> right.equalityCheck(monkeys, x / left.value);
                    case '/' -> right.equalityCheck(monkeys, left.value / x);
                }
            }
        }
    }
}
