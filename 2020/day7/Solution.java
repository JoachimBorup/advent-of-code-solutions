import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    private static final Map<String, List<String>> bags = new HashMap<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int ans1 = 0, ans2;

        while (in.hasNext()) {
            String[] input = in.nextLine().split(" bags contain ");
            String[] smallBags = input[1].split(" bags?(, |.)");
            bags.put(input[0], Arrays.asList(smallBags));
        }

        for (Map.Entry<String, List<String>> entry : bags.entrySet()) if (containsBag(entry.getKey())) ans1++;
        ans2 = bagContains("shiny gold") - 1;

        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }

    private static boolean containsBag(String key) {
        List<String> newBags = bags.get(key);
        for (String value : newBags) if (value.contains("shiny gold")) return true;
        for (String value : newBags) {
            value = value.substring(2);
            if (bags.containsKey(value) && containsBag(value)) return true;
        }
        return false;
    }

    private static int bagContains(String key) {
        int sum = 1;
        List<String> newBags = bags.get(key);
        if (newBags.get(0).equals("no other")) return 1;
        else for (String value : newBags) {
            int amount = Integer.parseInt(value.substring(0, 1));
            value = value.substring(2);
            sum += amount * bagContains(value);
        }
        return sum;
    }
}
