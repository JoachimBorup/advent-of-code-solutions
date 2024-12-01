import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day22_2 {
    public static void main(String[] args) {
        Board board = readInput();
        board.walkPath();
        board.print();
        System.out.println((1000 * (board.y + 1)) + (4 * (board.x + 1)) + board.dir.toInt());
        System.out.println(board.board.length);
        System.out.println(board.board[0].length);
    }

    private static Board readInput() {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                break;
            }

            lines.add(line);
        }

        String path = sc.nextLine();

        sc.close();
        return new Board(lines, path);
    }

    private enum Direction {
        RIGHT, DOWN, LEFT, UP;

        private int toInt() {
            return switch (this) {
                case RIGHT -> 0;
                case DOWN -> 1;
                case LEFT -> 2;
                case UP -> 3;
            };
        }
    }

    private record Move(int x, int y, int turnRights) { }

    private static class Board {
        private final char[][] board;
        private final String[] path;
        private Direction dir;
        private int x, y;

        public Board(List<String> board, String path) {
            int width = board.stream().mapToInt(String::length).max().orElseThrow();
            this.board = new char[board.size()][width];

            for (int i = 0; i < board.size(); i++) {
                String line = board.get(i);

                for (int j = 0; j < width; j++) {
                    this.board[i][j] = j < line.length() ? line.charAt(j) : ' ';
                }
            }

            this.path = Pattern.compile("\\d+|\\w")
                    .matcher(path)
                    .results()
                    .map(MatchResult::group)
                    .toArray(String[]::new);

            this.dir = Direction.RIGHT;
            this.y = 0;

            for (int i = 0; i < this.board[y].length; i++) {
                if (this.board[y][i] == '.') {
                    this.x = i;
                    break;
                }
            }
        }

        public void walkPath() {
            for (String step : path) {
                switch (step) {
                    case "L" -> turnLeft();
                    case "R" -> turnRight();
                    default -> {
                        int steps = Integer.parseInt(step);

                        for (int i = 0; i < steps; i++) {
                            moveOnce();
                        }
                    }
                }
            }
        }

        private void moveOnce() {
            Move next = nextMove();

            if (board[next.y][next.x] == '.') {
                x = next.x;
                y = next.y;

                for (int i = 0; i < next.turnRights; i++) {
                    turnRight();
                }
            }
        }

        //          000..049 050..099 100..149
        // 000..049 ........ 11111111 22222222
        // 050..099 ........ 33333333 ........
        // 100..149 44444444 55555555 ........
        // 150..199 66666666 ........ ........

        private Move nextMove() {
            switch (dir) {
                case RIGHT -> {
                    if (x == 149) { // 2 to 5
                        return new Move(99, 149 - y, 2);
                    }
                    if (x == 99 && y > 49) {
                        if (y < 100) { // 3 to 2
                            return new Move(50 + y, 49, 3);
                        } else { // 5 to 2
                            return new Move(149, 149 - y, 2);
                        }
                    }
                    if (x == 49 && y > 149) { // 6 to 5
                        return new Move(y - 50, 149, 3);
                    }

                    return new Move(x + 1, y ,0);
                }
                case DOWN -> {
                    return new Move(x, y + 1, 0);
                }
                case LEFT -> {
                    return new Move(x - 1, y, 0);
                }
                case UP -> {
                    return new Move(x, y - 1, 0);
                }
                default -> throw new IllegalStateException("Unexpected value: " + dir);
            }
        }

        private void turnRight() {
            this.dir = switch (dir) {
                case RIGHT -> Direction.DOWN;
                case DOWN -> Direction.LEFT;
                case LEFT -> Direction.UP;
                case UP -> Direction.RIGHT;
            };
        }

        private void turnLeft() {
            this.dir = switch (dir) {
                case RIGHT -> Direction.UP;
                case DOWN -> Direction.RIGHT;
                case LEFT -> Direction.DOWN;
                case UP -> Direction.LEFT;
            };
        }

        private void turnAround() {
            this.dir = switch (dir) {
                case RIGHT -> Direction.LEFT;
                case DOWN -> Direction.UP;
                case LEFT -> Direction.RIGHT;
                case UP -> Direction.DOWN;
            };
        }

        public void print() {
            for (char[] row : board) {
                for (char c : row) {
                    System.out.print(c);
                }
                System.out.println();
            }
            System.out.println();
            System.out.println(Arrays.toString(path));
        }
    }
}
