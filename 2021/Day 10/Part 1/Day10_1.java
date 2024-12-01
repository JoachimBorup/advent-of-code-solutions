import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Day10_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<Character, Integer> syntaxCheckerScores = new HashMap<>();
        syntaxCheckerScores.put(')', 3);
        syntaxCheckerScores.put(']', 57);
        syntaxCheckerScores.put('}', 1197);
        syntaxCheckerScores.put('>', 25137);

        int sum = 0;

        while (sc.hasNextLine()) {
            char[] line = sc.nextLine().toCharArray();
            sum += getErrorScore(line, syntaxCheckerScores);
        }

        System.out.println(sum);
        sc.close();
    }

    private static int getErrorScore(char[] line, Map<Character, Integer> syntaxCheckerScores) {
        Stack<Character> stack = new Stack<>();

        for (char character : line) {
            switch (character) {
                case '(', '[', '{', '<' -> stack.push(character);
                case ')' -> { if (stack.pop() != '(') return syntaxCheckerScores.get(character); }
                case ']' -> { if (stack.pop() != '[') return syntaxCheckerScores.get(character); }
                case '}' -> { if (stack.pop() != '{') return syntaxCheckerScores.get(character); }
                case '>' -> { if (stack.pop() != '<') return syntaxCheckerScores.get(character); }
            }
        }

        return 0;
    }
}
