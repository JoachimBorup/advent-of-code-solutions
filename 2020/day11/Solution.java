import java.util.ArrayList;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int ans1 = 0, ans2 = 0;

        // Read input
        ArrayList<String> input = new ArrayList<>();
        while (in.hasNextLine()) input.add(in.nextLine());

        // Grid length and width
        int x = input.size(), y = input.get(0).length();

        // Create grid
        char[][] grid = new char[x][y];
        for (int i = 0; i < x; i++) grid[i] = input.get(i).toCharArray();

        // Run until the chaos stabilizes
        boolean chaos = true;
        while (chaos) {
            // Determine whether each seat should be empty or occupied next iteration
            boolean[][] occupied = new boolean[x][y];
            boolean[][] empty = new boolean[x][y];
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    char pos = grid[i][j];
                    if (pos != '.') {
                        int counter = 0;
                        if (i > 0 && grid[i - 1][j] == '#') counter++;
                        if (j > 0 && grid[i][j - 1] == '#') counter++;
                        if (i < x - 1 && grid[i + 1][j] == '#') counter++;
                        if (j < y - 1 && grid[i][j + 1] == '#') counter++;
                        if (i > 0 && j > 0 && grid[i - 1][j - 1] == '#') counter++;
                        if (i < x - 1 && j > 0 && grid[i + 1][j - 1] == '#') counter++;
                        if (i > 0 && j < y - 1 && grid[i - 1][j + 1] == '#') counter++;
                        if (i < x - 1 && j < y - 1 && grid[i + 1][j + 1] == '#') counter++;

                        if (pos == 'L' && counter == 0) occupied[i][j] = true;
                        else if (pos == '#' && counter >= 4) empty[i][j] = true;
                    }
                }
            }

            // Check if chaos has stabilized yet
            chaos = isChaos(x, y, grid, occupied, empty);
        }

        // Set answer for part 1, and reset grid
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (grid[i][j] == '#') {
                    grid[i][j] = 'L';
                    ans1++;
                }
            }
        }

        // Run until the chaos stabilizes
        chaos = true;
        while (chaos) {
            // Determine whether each seat should be empty or occupied next iteration
            boolean[][] occupied = new boolean[x][y];
            boolean[][] empty = new boolean[x][y];
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    char pos = grid[i][j];
                    if (pos != '.') {
                        int counter = 0;
                        int xx, yy;

                        xx = i - 1;
                        while (xx >= 0) {
                            char next = grid[xx][j];
                            if (next == 'L') break;
                            else if (next == '#') {
                                counter++;
                                break;
                            }
                            xx--;
                        }

                        yy = j - 1;
                        while (yy >= 0) {
                            char next = grid[i][yy];
                            if (next == 'L') break;
                            else if (next == '#') {
                                counter++;
                                break;
                            }
                            yy--;
                        }

                        xx = i + 1;
                        while (xx <= x - 1) {
                            char next = grid[xx][j];
                            if (next == 'L') break;
                            else if (next == '#') {
                                counter++;
                                break;
                            }
                            xx++;
                        }

                        yy = j + 1;
                        while (yy <= y - 1) {
                            char next = grid[i][yy];
                            if (next == 'L') break;
                            else if (next == '#') {
                                counter++;
                                break;
                            }
                            yy++;
                        }

                        xx = i - 1;
                        yy = j - 1;
                        while (xx >= 0 && yy >= 0) {
                            char next = grid[xx][yy];
                            if (next == 'L') break;
                            else if (next == '#') {
                                counter++;
                                break;
                            }
                            xx--;
                            yy--;
                        }

                        xx = i + 1;
                        yy = j - 1;
                        while (xx <= x - 1 && yy >= 0) {
                            char next = grid[xx][yy];
                            if (next == 'L') break;
                            else if (next == '#') {
                                counter++;
                                break;
                            }
                            xx++;
                            yy--;
                        }

                        xx = i - 1;
                        yy = j + 1;
                        while (xx >= 0 && yy <= y - 1) {
                            char next = grid[xx][yy];
                            if (next == 'L') break;
                            else if (next == '#') {
                                counter++;
                                break;
                            }
                            xx--;
                            yy++;
                        }

                        xx = i + 1;
                        yy = j + 1;
                        while (xx <= x - 1 && yy <= y - 1) {
                            char next = grid[xx][yy];
                            if (next == 'L') break;
                            else if (next == '#') {
                                counter++;
                                break;
                            }
                            xx++;
                            yy++;
                        }

                        if (pos == 'L' && counter == 0) occupied[i][j] = true;
                        else if (pos == '#' && counter >= 5) empty[i][j] = true;
                    }
                }
            }

            // Check if chaos has stabilized yet
            chaos = isChaos(x, y, grid, occupied, empty);
        }

        // Set answer for part 2
        for (int i = 0; i < x; i++) for (int j = 0; j < y; j++) if (grid[i][j] == '#') ans2++;

        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }

    private static boolean isChaos(int x, int y, char[][] grid, boolean[][] occupied, boolean[][] empty) {
        boolean chaos = false;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (occupied[i][j]) {
                    grid[i][j] = '#';
                    chaos = true;
                }
                if (empty[i][j]) {
                    grid[i][j] = 'L';
                    chaos = true;
                }
            }
        }
        return chaos;
    }
}
