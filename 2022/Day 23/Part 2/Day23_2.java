import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day23_2 {
    public static void main(String[] args) {
        Elves elves = readInput();

        while (elves.canMove()) {
            elves.move();
        }

        System.out.println(elves.rounds());
    }

    private static Elves readInput() {
        Scanner sc = new Scanner(System.in);
        Set<Elf> elves = new HashSet<>();

        for (int y = 0; sc.hasNext(); y++) {
            char[] line = sc.nextLine().toCharArray();
            for (int x = 0; x < line.length; x++) {
                if (line[x] == '#') {
                    elves.add(new Elf(x, y));
                }
            }
        }

        sc.close();
        return new Elves(elves);
    }

    private static class Elves {
        private final Direction[] directions;
        private Set<Elf> elves;
        private int round;
        private boolean isDone;

        public Elves(Set<Elf> elves) {
            this.directions = Direction.values();
            this.elves = elves;
            this.round = 0;
            this.isDone = false;
        }

        public void move() {
            Set<Elf> newElves = new HashSet<>();
            Map<Elf, List<Elf>> proposals = new HashMap<>();
            boolean elvesMoved = false;

            for (Elf elf : elves) {
                Elf proposal = move(elf);
                proposals.putIfAbsent(proposal, new ArrayList<>());
                proposals.get(proposal).add(elf);

                if (!elf.equals(proposal)) {
                    elvesMoved = true;
                }
            }

            for (Map.Entry<Elf, List<Elf>> entry : proposals.entrySet()) {
                Elf proposal = entry.getKey();
                List<Elf> elves = entry.getValue();

                if (elves.size() > 1) {
                    newElves.addAll(elves);
                } else {
                    newElves.add(proposal);
                }
            }

            isDone = !elvesMoved;
            elves = newElves;
            round++;
        }

        private Elf move(Elf elf) {
            if (isAlone(elf)) {
                return elf;
            }

            for (int i = 0; i < directions.length; i++) {
                Direction direction = directions[(i + round) % directions.length];
                if (isBlocked(elf, direction)) {
                    return elf.next(direction);
                }
            }

            return elf;
        }

        private boolean isBlocked(Elf elf, Direction direction) {
            return switch (direction) {
                case NORTH -> isBlocked(elf, 0, -1);
                case SOUTH -> isBlocked(elf, 0, 1);
                case WEST -> isBlocked(elf, -1, 0);
                case EAST -> isBlocked(elf, 1, 0);
            };
        }

        private boolean isBlocked(Elf elf, int x, int y) {
            int i = Math.abs(y), j = Math.abs(x);
            return !elves.contains(new Elf(elf.x + x, elf.y + y)) &&
                   !elves.contains(new Elf(elf.x + x - i, elf.y + y - j)) &&
                   !elves.contains(new Elf(elf.x + x + i, elf.y + y + j));
        }

        private boolean isAlone(Elf elf) {
            int[] temp = { -1, 0, 1 };

            for (int i : temp) {
                for (int j : temp) {
                    if (!(i == 0 && j == 0) && elves.contains(new Elf(elf.x() + i, elf.y() + j))) {
                        return false;
                    }
                }
            }

            return true;
        }

        public boolean canMove() {
            return !isDone;
        }

        public int rounds() {
            return round;
        }
    }

    private record Elf(int x, int y) {
        public Elf next(Direction direction) {
            return switch (direction) {
                case NORTH -> new Elf(x, y - 1);
                case SOUTH -> new Elf(x, y + 1);
                case WEST -> new Elf(x - 1, y);
                case EAST -> new Elf(x + 1, y);
            };
        }
    }

    private enum Direction {
        NORTH, SOUTH, WEST, EAST
    }
}
