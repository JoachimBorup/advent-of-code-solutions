import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int ans1 = 0, ans2 = 0;

        Set<Character> questionsUnion = new HashSet<>();
        Set<Character> questionsIntersection = new HashSet<>();
        boolean firstPerson = true;

        for (int i = 0; i < 2063; i++) {
            char[] input = in.nextLine().toCharArray();
            if (input.length == 0) {
                ans1 += questionsUnion.size();
                ans2 += questionsIntersection.size();
                questionsUnion.clear();
                questionsIntersection.clear();
                firstPerson = true;
            } else {
                Set<Character> questionsAnswered = new HashSet<>();
                for (char ch : input) {
                    questionsUnion.add(ch);
                    questionsAnswered.add(ch);
                }
                if (firstPerson) {
                    questionsIntersection = questionsAnswered;
                    firstPerson = false;
                } else {
                    questionsIntersection.retainAll(questionsAnswered);
                }
                questionsIntersection.retainAll(questionsAnswered);
            }
        }

        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }
}
