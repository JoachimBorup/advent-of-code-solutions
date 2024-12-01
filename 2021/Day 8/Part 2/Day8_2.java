import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 *    ####    ....    ####    ####    ....    ####    ####    ####    ####    ####
 *   #    #  .    #  .    #  .    #  #    #  #    .  #    .  .    #  #    #  #    #
 *   #    #  .    #  .    #  .    #  #    #  #    .  #    .  .    #  #    #  #    #
 *    ....    ....    ####    ####    ####    ####    ####    ....    ####    ####
 *   #    #  .    #  #    .  .    #  .    #  .    #  #    #  .    #  #    #  .    #
 *   #    #  .    #  #    .  .    #  .    #  .    #  #    #  .    #  #    #  .    #
 *    ####    ....    ####    ####    ....    ####    ####    ....    ####    ####
 *
 *   Numbers with 5 segments: (2, 3, 5)
 *   - 2 has 2 intersections with 4
 *   - 3 is a superset of 1 (and has 4 intersections with 2, and is a superset of 7)
 *   - 5 remains
 *
 *   Numbers with 6 segments: (0, 6, 9)
 *   - 0 has 4 intersections with 5
 *   - 6 has 1 intersections with 1 (and has 2 intersections with 7)
 *   - 9 remains
 */

public class Day8_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int sum = 0;

        while (sc.hasNextLine()) {
            sum += getOutputValue(sc.nextLine());
        }

        System.out.println(sum);
        sc.close();
    }

    private static int getOutputValue(String input) {
        String[] temp = input.split(" \\| ");
        String[] signalPatterns = temp[0].split(" ");
        String[] outputValues = temp[1].split(" ");
        
        Arrays.sort(signalPatterns, (a, b) -> Integer.compare(a.length(), b.length()));
        
        Map<Integer, Set<Character>> segments = new HashMap<>();
        segments.put(1, getSegments(signalPatterns[0]));
        segments.put(4, getSegments(signalPatterns[2]));
        segments.put(7, getSegments(signalPatterns[1]));
        segments.put(8, getSegments(signalPatterns[9]));

        solveFiveSegmentNumbers(segments, signalPatterns);
        solveSixSegmentNumbers(segments, signalPatterns);

        StringBuilder sb = new StringBuilder();

        for (String outputValue : outputValues) {
            sb.append(getIndex(segments, outputValue));
        }

        return Integer.parseInt(sb.toString());
    }

    private static Set<Character> getSegments(String pattern) {
        Set<Character> chars = new HashSet<>();

        for (char ch : pattern.toCharArray()) {
            chars.add(ch);
        }

        return chars;
    }

    private static void solveFiveSegmentNumbers(Map<Integer, Set<Character>> segments, String[] patterns) {
        int two = findNumber(segments.get(4), patterns, 2, 3);
        segments.put(2, getSegments(patterns[two]));

        int three = findNumber(segments.get(1), patterns, 2, 3);
        segments.put(3, getSegments(patterns[three]));

        int five = getRemaining(two, three, 3, 4, 5);
        segments.put(5, getSegments(patterns[five]));
    }

    private static void solveSixSegmentNumbers(Map<Integer, Set<Character>> segments, String[] patterns) {
        int zero = findNumber(segments.get(5), patterns, 4, 6);
        segments.put(0, getSegments(patterns[zero]));

        int six = findNumber(segments.get(1), patterns, 1, 6);
        segments.put(6, getSegments(patterns[six]));

        int nine = getRemaining(zero, six, 6, 7, 8);
        segments.put(9, getSegments(patterns[nine]));
    }

    private static int findNumber(Set<Character> set, String[] patterns, int intersections, int index) {
        if (intersections(set, getSegments(patterns[index])) == intersections) return index;
        else if (intersections(set, getSegments(patterns[index + 1])) == intersections) return index + 1;
        else if (intersections(set, getSegments(patterns[index + 2])) == intersections) return index + 2;
        else throw new IllegalArgumentException(String.format("No intersections of %d!", intersections));
    }

    private static int getRemaining(int x, int y, int i, int j, int k) {
        if (x != i && y != i) return i;
        else if (x != j && y != j) return j;
        else if (x != k && y != k) return k;
        else throw new IllegalArgumentException();
    }

    private static int intersections(Set<Character> setX, Set<Character> setY) {
        Set<Character> set = new HashSet<>(setX);
        set.retainAll(setY);

        return set.size();
    }

    private static int getIndex(Map<Integer, Set<Character>> segments, String outputValue) {
        Set<Character> setX = getSegments(outputValue);

        for (int i = 0; i < segments.size(); i++) {
            Set<Character> setY = segments.get(i);

            if (setX.size() == setY.size() && intersections(setX, setY) == setX.size()) {
                return i;
            }
        }

        return -1;
    }
}
