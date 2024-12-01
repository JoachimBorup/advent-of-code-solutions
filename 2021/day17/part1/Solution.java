import java.util.Scanner;

public class Solution {
    private static final int MAGIC_NUMBER = 10_000;
    private static int highestReached;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] temp = sc.nextLine().split("(: x=)|(, y=)|(\\.\\.)");
        int minY = Integer.parseInt(temp[3]);
        int maxY = Integer.parseInt(temp[4]);
        
        for (int i = MAGIC_NUMBER; i >= minY; i--) {
            if (isPossible(i, minY, maxY)) {
                break;
            }
        }

        System.out.println(highestReached);
        sc.close();
    }

    private static boolean isPossible(int yVel, int min, int max) {
        highestReached = 0;
        int y = 0;

        while (y >= min) {
            y += yVel;
            yVel--;

            highestReached = Math.max(highestReached, y);

            if (min <= y && y <= max) {
                return true;
            }
        }

        return false;
    }
}
