import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day5_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Line> lines = new ArrayList<>();
        int maxX = 0, maxY = 0;

        while (sc.hasNext()) {
            Line line = Line.parse(sc.nextLine());

            if (line.isAligned()) {
                lines.add(line);

                maxX = max(maxX, line.leftX, line.rightX);
                maxY = max(maxY, line.topY, line.bottomY);
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
                for (int i = line.bottomY; i <= line.topY; i++) {
                    grid[i][line.leftX]++;
                }
            } else if (line.isVerticallyAligned()) {
                for (int i = line.leftX; i <= line.rightX; i++) {
                    grid[line.bottomY][i]++;
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
        public final int topY, bottomY;

        public Line(int x1, int y1, int x2, int y2) {
            this.leftX = Math.min(x1, x2);
            this.topY = Math.max(y1, y2);
            this.rightX = Math.max(x1, x2);
            this.bottomY = Math.min(y1, y2);
        }

        public boolean isAligned() {
            return isHorizontallyAligned() || isVerticallyAligned();
        }

        public boolean isHorizontallyAligned() {
            return leftX == rightX;
        }

        public boolean isVerticallyAligned() {
            return topY == bottomY;
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
