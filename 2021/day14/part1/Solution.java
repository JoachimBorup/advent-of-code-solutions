import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StringBuilder polymer = new StringBuilder(sc.nextLine());
        Map<String, String> pairs = new HashMap<>();
        sc.nextLine();

        while (sc.hasNextLine()) {
            String[] temp = sc.nextLine().split(" -> ");
            pairs.put(temp[0], temp[1]);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = polymer.length() - 1; j > 0; j--) {
                polymer.insert(j, pairs.get(polymer.substring(j - 1, j + 1)));
            }
        }

        Map<Character, Integer> occurences = new HashMap<>();

        for (int i = 0; i < polymer.length(); i++) {
            char ch = polymer.charAt(i);

            if (!occurences.containsKey(ch)) {
                occurences.put(ch, 0);
            }

            occurences.put(ch, occurences.get(ch) + 1);
        }

        int maxOccurences = Integer.MIN_VALUE;
        int minOccurences = Integer.MAX_VALUE;

        for (int value : occurences.values()) {
            maxOccurences = Math.max(maxOccurences, value);
            minOccurences = Math.min(minOccurences, value);
        }

        System.out.println(maxOccurences - minOccurences);
        sc.close();
    }
}
