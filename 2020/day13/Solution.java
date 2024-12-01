import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int ans1;
        long ans2;

        // Two lists keeping track of bus IDs and their index number
        List<Integer> busIDs = new ArrayList<>(), busPos = new ArrayList<>();

        // Set timestamp, and fill lists with input
        int timestamp = Integer.parseInt(in.nextLine());
        String[] input = in.nextLine().split(",");
        for (int i = 0; i < input.length; i++) {
            String busID = input[i];
            if (busID.charAt(0) != 'x') {
                busIDs.add(Integer.parseInt(busID));
                busPos.add(i);
            }
        }

        // Part 1
        int finalTime = timestamp;
        part1:
        while (true) {
            for (int busID : busIDs) {
                if (finalTime % busID == 0) {
                    ans1 = (finalTime - timestamp) * busID;
                    break part1;
                }
            }
            finalTime++;
        }

        // Part 2
        int length = busIDs.size();
        long time = 0, increment = 1;
        for (int i = 0 ; i < length ; i++) {
            while ((time + busPos.get(i)) % busIDs.get(i) != 0) {
                time += increment;
            }
            increment *= busIDs.get(i);
        }
        ans2 = time;

        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }
}
