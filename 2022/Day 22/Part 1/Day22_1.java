import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day22_1 {
    public static void main(String[] args) {
        Board board = readInput();
        board.walkPath();
        System.out.println((1000 * (board.y + 1)) + (4 * (board.x + 1)) + board.dir.toInt());
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
            int prevX = x;
            int prevY = y;

            while (board[nextY()][nextX()] == ' ') {
                x = nextX();
                y = nextY();
            }

            if (board[nextY()][nextX()] == '.') {
                x = nextX();
                y = nextY();
            } else {
                x = prevX;
                y = prevY;
            }
        }

        private int nextX() {
            return (board[y].length + switch (dir) {
                case RIGHT -> x + 1;
                case LEFT -> x - 1;
                default -> x;
            }) % board[y].length;
        }

        private int nextY() {
            return (board.length + switch (dir) {
                case DOWN -> y + 1;
                case UP -> y - 1;
                default -> y;
            }) % board.length;
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
    }
}
