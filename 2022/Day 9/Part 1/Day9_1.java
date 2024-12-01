import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day9_1 {
    private static final int KNOTS = 2;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Set<Coordinate> visited = new HashSet<>();
        int[] xs = new int[KNOTS];
        int[] ys = new int[KNOTS];

        while (sc.hasNext()) {
            String direction = sc.next();
            int distance = sc.nextInt();

            for (int i = 0; i < distance; i++) {
                // Move head knot
                switch (direction) {
                    case "U" -> ys[0]++;
                    case "D" -> ys[0]--;
                    case "L" -> xs[0]--;
                    case "R" -> xs[0]++;
                }

                for (int j = 1; j < KNOTS; j++) {
                    // Don't move touching knots
                    if (Math.abs(xs[j - 1] - xs[j]) < 2 && Math.abs(ys[j - 1] - ys[j]) < 2) {
                        continue;
                    }

                    // Move knot closer to previous knot
                    if (xs[j - 1] < xs[j]) xs[j]--;
                    if (xs[j - 1] > xs[j]) xs[j]++;
                    if (ys[j - 1] < ys[j]) ys[j]--;
                    if (ys[j - 1] > ys[j]) ys[j]++;
                }

                // Add tail knot to set of visited knots
                visited.add(new Coordinate(xs[KNOTS - 1], ys[KNOTS - 1]));
            }
        }

        System.out.println(visited.size());
        sc.close();
    }

    private record Coordinate(int x, int y) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }
    }
}
