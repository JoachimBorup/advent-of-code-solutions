import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean[][] paper = parsePaper(sc);
        List<Fold> folds = parseFolds(sc);

        int maxY = paper.length;
        int maxX = paper[0].length;

        for (Fold fold : folds) {
            if (fold.isHorizontal) {
                for (int i = fold.line + 1; i < maxY; i++) {
                    int offset = (i - fold.line) * 2;
                    
                    for (int j = 0; j < maxX; j++) {
                        paper[i - offset][j] = paper[i - offset][j] || paper[i][j];                    
                    }
                }

                maxY = fold.line;
            } else {
                for (int i = 0; i < maxY; i++) {
                    for (int j = fold.line + 1; j < maxX; j++) {
                        int offset = (j - fold.line) * 2;

                        paper[i][j - offset] = paper[i][j - offset] || paper[i][j];                    
                    }
                }

                maxX = fold.line;
            }
        }

        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(paper[i][j] ? '#' : '.');
            }
            System.out.println();
        }

        sc.close();
    }

    private static boolean[][] parsePaper(Scanner sc) {
        List<Coordinate> coordinates = new ArrayList<>();
        int maxX = 0, maxY = 0;

        while (sc.hasNextLine()) {
            String[] temp = sc.nextLine().split(",");
            if (temp.length < 2) break;

            int x = Integer.parseInt(temp[0]);
            int y = Integer.parseInt(temp[1]);

            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            
            coordinates.add(new Coordinate(x, y));
        }

        boolean[][] paper = new boolean[maxY + 1][maxX + 1];

        for (Coordinate c : coordinates) {
            paper[c.y][c.x] = true;
        }

        return paper;
    }

    private static List<Fold> parseFolds(Scanner sc) {
        List<Fold> folds = new ArrayList<>();

        while (sc.hasNextLine()) {
            String[] temp = sc.nextLine().split("[ =]");
            folds.add(new Fold(temp[2].equals("y"), Integer.parseInt(temp[3])));
        }

        return folds;
    }

    private static class Coordinate {
        public final int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Fold {
        private final boolean isHorizontal;
        private final int line;

        public Fold(boolean isHorizontal, int line) {
            this.isHorizontal = isHorizontal;
            this.line = line;
        }
    }
}
