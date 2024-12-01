import java.util.Scanner;

public class Solution {
    private static final int MAGIC_NUMBER = 10_000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] temp = sc.nextLine().split("(: x=)|(, y=)|(\\.\\.)");
        int minX = Integer.parseInt(temp[1]);
        int maxX = Integer.parseInt(temp[2]);
        int minY = Integer.parseInt(temp[3]);
        int maxY = Integer.parseInt(temp[4]);

        int possibleValues = 0;
        
        for (int i = MAGIC_NUMBER; i >= minY; i--) {
            for (int j = 0; j <= maxX; j++) {
                if (isPossible(i, j, minX, maxX, minY, maxY)) {
                    possibleValues++;
                }
            }
        }

        System.out.println(possibleValues);
        sc.close();
    }

    private static boolean isPossible(int yVel, int xVel, int minX, int maxX, int minY, int maxY) {
        int x = 0, y = 0;

        while (y >= minY && x <= maxX) {
            x += xVel;
            y += yVel;

            if (xVel > 0) xVel--;
            yVel--;

            if (minY <= y && y <= maxY && minX <= x && x <= maxX) {
                return true;
            }
        }

        return false;
    }
}
