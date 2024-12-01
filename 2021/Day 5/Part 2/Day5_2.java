import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day5_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Line> lines = new ArrayList<>();
        int maxX = 0, maxY = 0;

        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            Line line = Line.parse(nextLine);

            if (line.isAligned()) {
                lines.add(line);

                maxX = max(maxX, line.leftX, line.rightX);
                maxY = max(maxY, line.leftY, line.rightY);
            }
        }

        int[][] grid = new int[maxX + 2][maxY + 2];
        markLines(grid, lines);

        System.out.println(countOverlappingLines(grid));
        sc.close();
    }

    private static int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    private static void markLines(int[][] grid, List<Line> lines) {
        for (Line line : lines) {
            if (line.isHorizontallyAligned()) {
                if (line.leftY < line.rightY) {
                    for (int i = line.leftY; i <= line.rightY; i++) {
                        grid[i][line.leftX]++;
                    }
                } else {
                    for (int i = line.rightY; i <= line.leftY; i++) {
                        grid[i][line.leftX]++;
                    }
                }
            } else if (line.isVerticallyAligned()) {
                for (int i = line.leftX; i <= line.rightX; i++) {
                    grid[line.leftY][i]++;
                }
            } else if (line.isDiagonallyAligned()) {
                if (line.leftY < line.rightY) {
                    for (int i = 0; line.leftX + i <= line.rightX && line.leftY + i <= line.rightY; i++) {
                        grid[line.leftY + i][line.leftX + i]++;
                    }
                } else {
                    for (int i = 0; line.leftX + i <= line.rightX && line.leftY - i >= line.rightY; i++) {
                        grid[line.leftY - i][line.leftX + i]++;
                    }
                }
            }
        }
    }

    private static int countOverlappingLines(int[][] grid) {
        int overlappingLines = 0;

        for (int[] row : grid) {
            for (int position : row) {
                if (position > 1) {
                    overlappingLines++;
                }
            }
        }

        return overlappingLines;
    }

    private static class Line {
        public final int leftX, rightX;
        public final int leftY, rightY;

        public Line(int x1, int y1, int x2, int y2) {
            this.leftX = x1 < x2 ? x1 : x2;
            this.rightX = x1 < x2 ? x2 : x1;
            this.leftY = x1 < x2 ? y1 : y2;
            this.rightY = x1 < x2 ? y2 : y1;
        }

        public boolean isAligned() {
            return isHorizontallyAligned() || isVerticallyAligned() || isDiagonallyAligned();
        }

        public boolean isHorizontallyAligned() {
            return leftX == rightX;
        }

        public boolean isVerticallyAligned() {
            return leftY == rightY;
        }

        public boolean isDiagonallyAligned() {
            return rightX - leftX == Math.abs(leftY - rightY);
        }

        public static Line parse(String input) {
            String[] temp = input.split("[ ,]");
    
            int x1 = Integer.parseInt(temp[0]);
            int y1 = Integer.parseInt(temp[1]);
            int x2 = Integer.parseInt(temp[3]);
            int y2 = Integer.parseInt(temp[4]);
    
            return new Line(x1, y1, x2, y2);
        }
    }
}
