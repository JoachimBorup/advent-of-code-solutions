import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int direction = 1, x1 = 0, y1 = 0;
        int xw = 10, yw = 1, x2 = 0, y2 = 0;

        while (in.hasNextLine()) {
            String next = in.nextLine();
            if (next.isEmpty()) break;
            char action = next.charAt(0);
            int value = Integer.parseInt(next.substring(1));
            switch (action) {
                case 'N' -> {
                    y1 += value;
                    yw += value;
                }
                case 'S' -> {
                    y1 -= value;
                    yw -= value;
                }
                case 'E' -> {
                    x1 += value;
                    xw += value;
                }
                case 'W' -> {
                    x1 -= value;
                    xw -= value;
                }
                case 'L' -> {
                    switch (value) {
                        case 90 -> {
                            direction = (direction + 3) % 4;
                            int temp = xw;
                            xw = -yw;
                            yw = temp;
                        }
                        case 180 -> {
                            direction = (direction + 2) % 4;
                            xw = -xw;
                            yw = -yw;
                        }
                        case 270 -> {
                            direction = (direction + 1) % 4;
                            int temp = xw;
                            xw = yw;
                            yw = -temp;
                        }
                    }
                }
                case 'R' -> {
                    switch (value) {
                        case 90 -> {
                            direction = (direction + 1) % 4;
                            int temp = xw;
                            xw = yw;
                            yw = -temp;
                        }
                        case 180 -> {
                            direction = (direction + 2) % 4;
                            xw = -xw;
                            yw = -yw;
                        }
                        case 270 -> {
                            direction = (direction + 3) % 4;
                            int temp = xw;
                            xw = -yw;
                            yw = temp;
                        }
                    }
                }
                case 'F' -> {
                    switch (direction) {
                        case 0 -> y1 += value;
                        case 1 -> x1 += value;
                        case 2 -> y1 -= value;
                        case 3 -> x1 -= value;
                    }
                    y2 += value * yw;
                    x2 += value * xw;
                }
            }
        }

        System.out.println("Part 1: " + (Math.abs(x1) + Math.abs(y1)));
        System.out.println("Part 2: " + (Math.abs(x2) + Math.abs(y2)));
        in.close();
    }
}
