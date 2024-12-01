import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day18_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Set<Coordinate> cubes = new HashSet<>();

        while (sc.hasNextLine()) {
            String[] input = sc.nextLine().split(",");
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);
            int z = Integer.parseInt(input[2]);
            cubes.add(new Coordinate(x, y, z));
        }

        int surfaceArea = 0;

        for (Coordinate cube : cubes) {
            for (Coordinate adjacentCoordinate : cube.adjacentCoordinates()) {
                if (!cubes.contains(adjacentCoordinate)) {
                    surfaceArea++;
                }
            }
        }

        System.out.println(surfaceArea);
        sc.close();
    }

    private record Coordinate(int x, int y, int z) {
        public Coordinate[] adjacentCoordinates() {
            return new Coordinate[] {
                new Coordinate(x + 1, y, z),
                new Coordinate(x - 1, y, z),
                new Coordinate(x, y + 1, z),
                new Coordinate(x, y - 1, z),
                new Coordinate(x, y, z + 1),
                new Coordinate(x, y, z - 1)
            };
        }
    }
}
