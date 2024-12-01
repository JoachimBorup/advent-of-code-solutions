import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day12_1 {
    private static final Map<String, List<String>> graph = new HashMap<>();
    private static final String start = "start", end = "end";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String[] input = sc.nextLine().split("-");
            addEdge(input[0], input[1]);
        }

        System.out.println(dfs(start, new HashSet<>()));
        sc.close();
    }

    private static void addEdge(String v, String w) {
        if (!graph.containsKey(v)) graph.put(v, new ArrayList<>());
        if (!graph.containsKey(w)) graph.put(w, new ArrayList<>());

        graph.get(v).add(w);
        graph.get(w).add(v);
    }

    private static int dfs(String v, Set<String> marked) {
        if (marked.contains(v)) return 0;
        if (v.equals(end)) return 1;

        if (isSmallCave(v)) marked.add(v);
        int paths = 0;

        for (String cave : graph.get(v)) {
            paths += dfs(cave, marked);
        }

        marked.remove(v);
        return paths;
    }

    private static boolean isSmallCave(String v) {
        return v.equals(v.toLowerCase());
    }
}
