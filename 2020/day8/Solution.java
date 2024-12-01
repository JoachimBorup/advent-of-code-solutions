import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private static final List<String> operations = new ArrayList<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) operations.add(in.nextLine());
        int ans1, ans2;

        ans1 = solve();

        loop:
        for (int i = 0; i < operations.size(); i++) {
            String operation = operations.get(i).split(" ")[0];
            switch (operation) {
                case "nop" -> {
                    if (check(i)) {
                        operations.set(i, operations.get(i).replace("nop", "jmp"));
                        break loop;
                    }
                }
                case "jmp" -> {
                    if (check(i)) {
                        operations.set(i, operations.get(i).replace("jmp", "nop"));
                        break loop;
                    }
                }
            }
        }

        ans2 = solve();

        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }

    public static int solve() {
        boolean[] visited = new boolean[operations.size()];
        int accumulator = 0;

        for (int i = 0; i < operations.size(); i++) {
            if (visited[i]) break;
            else {
                String[] next = operations.get(i).split(" ");
                String operation = next[0];
                int value = Integer.parseInt(next[1].substring(1));
                if (next[1].charAt(0) == '-') value = -value;
                switch (operation) {
                    case "acc" -> accumulator += value;
                    case "jmp" -> i += value - 1;
                }
                visited[i] = true;
            }
        }
        return accumulator;
    }

    public static boolean check(int index) {
        boolean[] visited = new boolean[operations.size()];
        for (int i = 0; i < operations.size(); i++) {
            if (visited[i]) {
                return false;
            } else {
                String[] next = operations.get(i).split(" ");
                String operation = next[0];
                int value = Integer.parseInt(next[1].substring(1));
                if (next[1].charAt(0) == '-') value = -value;
                switch (operation) {
                    case "jmp" -> {
                        if (i != index) i += value - 1;
                    }
                    case "nop" -> {
                        if (i == index) i += value - 1;
                    }
                }
                visited[i] = true;
            }
        }
        return true;
    }
}
