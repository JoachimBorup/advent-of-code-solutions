import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean[][] paper = parsePaper(sc);
        List<Fold> folds = parseFolds(sc);

        Fold fold = folds.get(0);

        if (fold.isHorizontal) {
            for (int i = fold.line + 1; i < paper.length; i++) {
                int offset = (i - fold.line) * 2;
                
                for (int j = 0; j < paper[i].length; j++) {
                    paper[i - offset][j] = paper[i - offset][j] || paper[i][j];                    
                }
            }
        } else {
            for (int i = 0; i < paper.length; i++) {
                for (int j = fold.line + 1; j < paper[i].length; j++) {
                    int offset = (j - fold.line) * 2;

                    paper[i][j - offset] = paper[i][j - offset] || paper[i][j];                    
                }
            }
        }
        
        int count = 0;
        
        for (int i = 0; i < (fold.isHorizontal ? fold.line : paper.length); i++) {
            for (int j = 0; j < (fold.isHorizontal ? paper[i].length : fold.line); j++) {
                if (paper[i][j]) {
                    count++;
                }
            }
        }

        System.out.println(count);
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
