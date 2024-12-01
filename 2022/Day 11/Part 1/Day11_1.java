import java.util.*;

public class Day11_1 {
    public static void main(String[] args) {
        List<Monkey> monkeys = readInput();
        final int ROUNDS = 20;

        for (int i = 0; i < ROUNDS; i++) {
            for (Monkey monkey : monkeys) {
                while (monkey.hasMoreItems()) {
                    int item = monkey.inspect();
                    monkeys.get(monkey.throwItemTo(item)).catchItem(item);
                }
            }
        }

        monkeys.stream()
                .map(Monkey::getItemsInspected)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .reduce((a, b) -> a * b)
                .ifPresent(System.out::println);
    }

    private static List<Monkey> readInput() {
        Scanner sc = new Scanner(System.in);
        List<Monkey> monkeys = new ArrayList<>();

        while (sc.hasNext()) {
            Queue<Integer> items = new ArrayDeque<>();

            // Monkey 0:
            sc.nextLine();

            // Starting items: 79, 98
            String[] line = sc.nextLine().split("\\D+");
            for (int i = 1; i < line.length; i++) {
                items.add(Integer.parseInt(line[i]));
            }

            // Operation: new = old * 19
            line = sc.nextLine().split(" ");
            char operation = line[7].equals("old") ? '^' : line[6].charAt(0);
            int worry = line[7].equals("old") ? -1 : Integer.parseInt(line[7]);

            // Test: divisible by 23
            int divisibleBy = Integer.parseInt(sc.nextLine().split("\\D+")[1]);
            // If true: throw to monkey 2
            int trueMonkey = Integer.parseInt(sc.nextLine().split("\\D+")[1]);
            // If false: throw to monkey 3
            int falseMonkey = Integer.parseInt(sc.nextLine().split("\\D+")[1]);

            // Ignore empty line
            if (sc.hasNextLine()) sc.nextLine();

            monkeys.add(new Monkey(items, operation, worry, divisibleBy, trueMonkey, falseMonkey));
        }

        sc.close();
        return monkeys;
    }

    private static class Monkey {
        private final Queue<Integer> items;
        private final char operation;
        private final int worry;
        private final int divisibleBy;
        private final int trueMonkey;
        private final int falseMonkey;
        private int itemsInspected;

        public Monkey(Queue<Integer> items, char operation, int worry, int divisibleBy, int trueMonkey, int falseMonkey) {
            this.items = items;
            this.operation = operation;
            this.worry = worry;
            this.divisibleBy = divisibleBy;
            this.trueMonkey = trueMonkey;
            this.falseMonkey = falseMonkey;
            this.itemsInspected = 0;
        }

        public int inspect() {
            int item = items.remove();
            itemsInspected++;

            switch (operation) {
                case '+' -> item += worry;
                case '*' -> item *= worry;
                case '^' -> item *= item;
            }

            return item / 3;
        }

        public int getItemsInspected() {
            return itemsInspected;
        }

        public boolean hasMoreItems() {
            return !items.isEmpty();
        }

        public int throwItemTo(int item) {
            return item % divisibleBy == 0 ? trueMonkey : falseMonkey;
        }

        public void catchItem(int item) {
            items.add(item);
        }
    }
}
