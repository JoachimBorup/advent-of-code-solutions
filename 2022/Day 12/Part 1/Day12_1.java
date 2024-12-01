import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Day12_1 {
    public static void main(String[] args) {
        char[][] grid = readInput();
        Coordinate start = null;
        Coordinate end = null;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'S') {
                    start = new Coordinate(i, j);
                    grid[i][j] = 'a';
                } else if (grid[i][j] == 'E') {
                    end = new Coordinate(i, j);
                    grid[i][j] = 'z';
                }
            }
        }

        System.out.println(shortestPath(grid, start, end));
    }

    private static int shortestPath(char[][] grid, Coordinate start, Coordinate end) {
        Queue<Coordinate> queue = new ArrayDeque<>();
        int[][] cost = new int[grid.length][];

        for (int i = 0; i < grid.length; i++) {
            cost[i] = new int[grid[i].length];

            for (int j = 0; j < grid[i].length; j++) {
                cost[i][j] = Integer.MAX_VALUE;
            }
        }

        cost[start.x][start.y] = 0;
        queue.add(start);

        while(queue.size() > 0) {
            Coordinate cur = queue.remove();

            if (cur.x == end.x && cur.y == end.y) {
                return cost[cur.x][cur.y];
            }

            for (Coordinate c : cur.directNeighbors()) {
                if (c.x < 0 || c.x >= grid.length || c.y < 0 || c.y >= grid[c.x].length) continue;
                if (grid[cur.x][cur.y] < grid[c.x][c.y] - 1) continue;

                if (cost[cur.x][cur.y] + 1 < cost[c.x][c.y]) {
                    cost[c.x][c.y] = cost[cur.x][cur.y] + 1;
                    queue.add(c);
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    private static char[][] readInput() {
        Scanner sc = new Scanner(System.in);

        List<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        char[][] grid = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        return grid;
    }

    private record Coordinate(int x, int y) {
        public Coordinate[] directNeighbors() {
            return new Coordinate[] {
                new Coordinate(x + 1, y),
                new Coordinate(x - 1, y),
                new Coordinate(x, y + 1),
                new Coordinate(x, y - 1)
            };
        }
    }
}
