import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] numbersDrawn = parseNumbersDrawn(sc);
        List<Board> boards = parseBoards(sc);
        int winningScore = getWinningScore(boards, numbersDrawn);

        System.out.println(winningScore);
        sc.close();
    }

    private static int[] parseNumbersDrawn(Scanner sc) {
        String[] temp = sc.nextLine().split(",");
        int[] numbersDrawn = new int[temp.length];

        for (int i = 0; i < temp.length; i++) {
            numbersDrawn[i] = Integer.parseInt(temp[i]);
        }

        return numbersDrawn;
    }

    private static List<Board> parseBoards(Scanner sc) {
        List<Board> boards = new ArrayList<>();

        while (sc.hasNext()) {
            int[][] board = new int[5][5];

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    board[i][j] = sc.nextInt();
                }
            }

            boards.add(new Board(board));
        }

        return boards;
    }

    private static int getWinningScore(List<Board> boards, int[] numbersDrawn) {
        for (int number : numbersDrawn) {
            for (int i = boards.size() - 1; i >= 0; i--) {
                Board board = boards.get(i);
                board.mark(number);

                if (board.isWinning()) {
                    boards.remove(i);

                    if (boards.isEmpty()) {
                        return board.getSumOfUnmarkedNumbers() * number;
                    }
                }
            }
        }

        return -1;
    }

    private static class Board {
        private int[][] board;
        private boolean[][] marked;

        public Board(int[][] board) {
            this.board = board;
            this.marked = new boolean[board.length][board.length];
        }

        public void mark(int number) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] == number) {
                        marked[i][j] = true;
                        return;
                    }
                }
            }
        }

        public boolean isWinning() {
            for (int i = 0; i < board.length; i++) {
                if (rowIsMarked(board, i)) {
                    return true;
                }

                if (columnIsMarked(board, i)) {
                    return true;
                }
            }

            return false;
        }

        private boolean rowIsMarked(int[][] board, int row) {
            for (int i = 0; i < board.length; i++) {
                if (!marked[row][i]) {
                    return false;
                }
            }

            return true;
        }

        private boolean columnIsMarked(int[][] board, int column) {
            for (int i = 0; i < board.length; i++) {
                if (!marked[i][column]) {
                    return false;
                }
            }

            return true;
        }

        public int getSumOfUnmarkedNumbers() {
            int sum = 0;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (!marked[i][j]) {
                        sum += board[i][j];
                    }
                }
            }

            return sum;
        }
    }
}
