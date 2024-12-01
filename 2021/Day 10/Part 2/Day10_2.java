import java.util.*;

public class Day10_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<Character, Integer> autocompleteScores = new HashMap<>();
        autocompleteScores.put('(', 1);
        autocompleteScores.put('[', 2);
        autocompleteScores.put('{', 3);
        autocompleteScores.put('<', 4);

        List<Long> scores = new ArrayList<>();

        while (sc.hasNextLine()) {
            char[] line = sc.nextLine().toCharArray();
            long score = getAutocompleteScore(line, autocompleteScores);
            if (score != 0) scores.add(score);
        }

        Collections.sort(scores);

        System.out.println(scores.get(scores.size() / 2));
        sc.close();
    }

    private static long getAutocompleteScore(char[] line, Map<Character, Integer> autocompleteScores) {
        Stack<Character> stack = new Stack<>();

        for (char character : line) {
            switch (character) {
                case '(', '[', '{', '<' -> stack.push(character);
                case ')' -> { if (stack.pop() != '(') return 0; }
                case ']' -> { if (stack.pop() != '[') return 0; }
                case '}' -> { if (stack.pop() != '{') return 0; }
                case '>' -> { if (stack.pop() != '<') return 0; }
            }
        }

        long score = 0;

        while (!stack.isEmpty()) {
            score = score * 5 + autocompleteScores.get(stack.pop());
        }

        return score;
    }
}
