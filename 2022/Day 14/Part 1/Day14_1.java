import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day14_1 {
    public static void main(String[] args) {
        System.out.println(fillWithSand(readInput()));
    }

    private static int fillWithSand(Set<Coordinate> filled) {
        int deepestRock = filled.stream().mapToInt(c -> c.y).max().orElse(0);
        int originalSize = filled.size();

        while (true) {
            int x = 500;
            int y = 0;

            while (true) {
                if (y > deepestRock) {
                    return filled.size() - originalSize;
                }

                if (filled.contains(new Coordinate(x, y + 1))) {
                    if (filled.contains(new Coordinate(x - 1, y + 1))) {
                        if (filled.contains(new Coordinate(x + 1, y + 1))) {
                            break;
                        } else {
                            x++;
                        }
                    } else {
                        x--;
                    }
                }

                y++;
            }

            filled.add(new Coordinate(x, y));
        }
    }

    private static Set<Coordinate> readInput() {
        Scanner sc = new Scanner(System.in);
        Set<Coordinate> filled = new HashSet<>();

        while (sc.hasNext()) {
            String[] line = sc.nextLine().split("\\D+");
            Coordinate prev = null;
            
            for (int i = 0; i < line.length; i += 2) {
                int x = Integer.parseInt(line[i]);
                int y = Integer.parseInt(line[i + 1]);

                if (prev == null) {
                    prev = new Coordinate(x, y);
                    filled.add(prev);
                    continue;
                }

                int fromX = Math.min(prev.x, x);
                int toX = Math.max(prev.x, x);
                int fromY = Math.min(prev.y, y);
                int toY = Math.max(prev.y, y);

                if (fromX == toX) {
                    for (int j = fromY; j <= toY; j++) {
                        filled.add(new Coordinate(x, j));
                    }
                } else {
                    for (int j = fromX; j <= toX; j++) {
                        filled.add(new Coordinate(j, y));
                    }
                }

                prev = new Coordinate(x, y);
            }
        }

        sc.close();
        return filled;
    }

    private record Coordinate(int x, int y) {
        @Override
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
    }
}
