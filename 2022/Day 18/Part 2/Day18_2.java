import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Day18_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Set<Cube> lava = new HashSet<>();

        while (sc.hasNextLine()) {
            String[] input = sc.nextLine().split(",");
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);
            int z = Integer.parseInt(input[2]);
            lava.add(new Cube(x, y, z));
        }

        Set<Cube> water = getSurroundingWaterCubes(lava);
        int surfaceArea = 0;

        for (Cube cube : water) {
            for (Cube adjacent : cube.adjacentCubes()) {
                if (lava.contains(adjacent)) {
                    surfaceArea++;
                }
            }
        }

        System.out.println(surfaceArea);
        sc.close();
    }

    private static Set<Cube> getSurroundingWaterCubes(Set<Cube> cubes) {
        Queue<Cube> queue = new ArrayDeque<>();
        Set<Cube> visited = new HashSet<>();

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxZ = Integer.MIN_VALUE;

        for (Cube cube : cubes) {
            minX = Math.min(minX, cube.x - 1);
            maxX = Math.max(maxX, cube.x + 1);
            minY = Math.min(minY, cube.y - 1);
            maxY = Math.max(maxY, cube.y + 1);
            minZ = Math.min(minZ, cube.z - 1);
            maxZ = Math.max(maxZ, cube.z + 1);
        }

        queue.add(new Cube(minX, minY, minZ));

        while (!queue.isEmpty()) {
            Cube current = queue.remove();
            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);
            for (Cube adjacent : current.adjacentCubes()) {
                if (
                    adjacent.x < minX || adjacent.x > maxX ||
                    adjacent.y < minY || adjacent.y > maxY ||
                    adjacent.z < minZ || adjacent.z > maxZ ||
                    cubes.contains(adjacent)
                ) {
                    continue;
                }

                queue.add(adjacent);
            }
        }

        return visited;
    }

    private record Cube(int x, int y, int z) {
        public Cube[] adjacentCubes() {
            return new Cube[] {
                new Cube(x + 1, y, z),
                new Cube(x - 1, y, z),
                new Cube(x, y + 1, z),
                new Cube(x, y - 1, z),
                new Cube(x, y, z + 1),
                new Cube(x, y, z - 1)
            };
        }
    }
}
