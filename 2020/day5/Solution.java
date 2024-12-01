import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean[][] seatOccupied = new boolean[128][8];
        int ans1 = 0, ans2 = 0;

        for (int i = 0; i < 908; i++) {
            int minRow = 0, maxRow = 127, minColumn = 0, maxColumn = 7;
            char[] boardingPass = in.next().toCharArray();

            for (int j = 0; j < boardingPass.length; j++) {
                char ch = boardingPass[j];
                switch (ch) {
                    case 'F' -> maxRow = (minRow + maxRow) / 2;
                    case 'B' -> minRow = (minRow + maxRow) / 2 + 1;
                    case 'L' -> maxColumn = (minColumn + maxColumn) / 2;
                    case 'R' -> minColumn = (minColumn + maxColumn) / 2 + 1;
                }
            }

            int seatID = minRow * 8 + minColumn;
            if (seatID > ans1) ans1 = seatID;

            seatOccupied[minRow][minColumn] = true;
        }

        for (int i = 5; i < 118; i++) {
            for (int j = 0; j < 8; j++) {
                if (!seatOccupied[i][j]) {
                    ans2 = i * 8 + j;
                    break;
                }
            }
        }

        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }
}
