import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<String, String> rules = new HashMap<>();
        Map<String, Long> pairs = new HashMap<>();
        String polymer = sc.nextLine();
        sc.nextLine();

        // Find initial polymer pairs
        for (int i = 1; i < polymer.length(); i++) {
            String pair = polymer.substring(i - 1, i + 1);
            add(pairs, pair);
        }

        // Get rules
        while (sc.hasNextLine()) {
            String[] temp = sc.nextLine().split(" -> ");
            rules.put(temp[0], temp[1]);
        }

        // Compute occurences of new pairs based on previous pairs
        for (int i = 0; i < 40; i++) {
            Map<String, Long> newPairs = new HashMap<>();

            pairs.forEach((pair, occurences) -> {
                String rule = rules.get(pair);
                String left = pair.charAt(0) + rule;
                String right = rule + pair.charAt(1);

                add(newPairs, left, occurences);
                add(newPairs, right, occurences);
            });

            pairs = newPairs;
        }

        Map<String, Long> totalOccurences = new HashMap<>();

        // Count elements
        pairs.forEach((pair, occurences) -> {
            add(totalOccurences, pair.substring(0, 1), occurences / 2);
            add(totalOccurences, pair.substring(1, 2), occurences / 2);
        });

        long maxOccurences = Long.MIN_VALUE;
        long minOccurences = Long.MAX_VALUE;

        for (long value : totalOccurences.values()) {
            maxOccurences = Math.max(maxOccurences, value);
            minOccurences = Math.min(minOccurences, value);
        }

        System.out.println(maxOccurences - minOccurences);
        sc.close();
    }

    private static void add(Map<String, Long> pairs, String pair) {
        add(pairs, pair, 1);
    }

    private static void add(Map<String, Long> pairs, String pair, long occurences) {
        if (!pairs.containsKey(pair)) pairs.put(pair, 0L);
        pairs.put(pair, pairs.get(pair) + occurences);
    }
}
