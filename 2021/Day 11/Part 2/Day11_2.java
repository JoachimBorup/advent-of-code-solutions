import java.util.Scanner;

public class Day11_2 {
    public static void main(String[] args) {
        int[][] grid = parseInput();
        int steps = 0;

        while (flash(grid) != 100) {
            increment(grid);
            steps++;
        }

        System.out.println(steps);
    }

    private static int[][] parseInput() {
        Scanner sc = new Scanner(System.in);
        int[][] grid = new int[10][10];

        for (int i = 0; i < grid.length; i++) {
            char[] row = sc.nextLine().toCharArray();

            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = row[j] - '0';
            }
        }

        sc.close();

        return grid;
    }

    private static void increment(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j]++;
            }
        }
    }

    private static int flash(int[][] grid) {
        boolean[][] flashed = new boolean[10][10];
        int flashes = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] > 9) {
                    flashes += flash(grid, flashed, i, j);
                }
            }
        }

        return flashes;
    }

    private static int flash(int[][] grid, boolean[][] flashed, int y, int x) {
        if (y < 0 || y >= grid.length || x < 0 || x >= grid[y].length || flashed[y][x]) {
            return 0;
        }

        int flashes = 0;
        grid[y][x]++;

        if (grid[y][x] > 9) {
            flashed[y][x] = true;
            grid[y][x] = 0;
            flashes++;

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    flashes += flash(grid, flashed, y + i, x + j);
                }
            }
        }

        return flashes;
    }
}
