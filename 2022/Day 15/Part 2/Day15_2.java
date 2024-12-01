import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day15_2 {
    private static final int RANGE = 4_000_000;

    public static void main(String[] args) {
        List<Sensor> sensors = readInput();
        Set<Coordinate> potentialBeacons = new HashSet<>();

        for (Sensor s : sensors) {
            potentialBeacons.addAll(s.borderingCoordinates());
        }

        potentialBeacons.stream()
            .filter(c -> 0 <= c.x && c.x <= RANGE && 0 <= c.y && c.y <= RANGE)
            .filter(c -> sensors.stream().allMatch(s -> s.isBeaconAt(c) == Answer.MAYBE))
            .map(c -> c.x * 4_000_000L + c.y)
            .forEach(System.out::println);
    }

    private static List<Sensor> readInput() {
        Scanner sc = new Scanner(System.in);
        List<Sensor> sensors = new ArrayList<>();

        while (sc.hasNext()) {
            String[] line = sc.nextLine().split("[^\\d-]+");
            int sX = Integer.parseInt(line[1]);
            int sY = Integer.parseInt(line[2]);
            int bX = Integer.parseInt(line[3]);
            int bY = Integer.parseInt(line[4]);

            sensors.add(Sensor.from(sX, sY, bX, bY));
        }

        sc.close();
        return sensors;
    }

    private enum Answer {
        YES, MAYBE, NO
    }

    private record Coordinate(int x, int y) { }

    private record Sensor(Coordinate sensor, Coordinate beacon) {
        public static Sensor from(int sX, int sY, int bX, int bY) {
            return new Sensor(new Coordinate(sX, sY), new Coordinate(bX, bY));
        }

        public Answer isBeaconAt(Coordinate c) {
            if (beacon.equals(c)) {
                return Answer.YES;
            }
            if (distanceToBeacon() < distanceTo(c)) {
                return Answer.MAYBE;
            }
            return Answer.NO;
        }

        public Set<Coordinate> borderingCoordinates() {
            Set<Coordinate> set = new HashSet<>();
            int dist = distanceToBeacon() + 1;

            for (int i = 0; i < dist; i++) {
                set.add(new Coordinate(sensor.x + i, sensor.y - dist + i));
                set.add(new Coordinate(sensor.x + dist - i, sensor.y + i));
                set.add(new Coordinate(sensor.x - i, sensor.y + dist - i));
                set.add(new Coordinate(sensor.x - dist + i, sensor.y - i));
            }

            return set;
        }

        public int distanceTo(Coordinate c) {
            return Math.abs(sensor.x - c.x) + Math.abs(sensor.y - c.y);
        }

        public int distanceToBeacon() {
            return distanceTo(beacon);
        }
    }
}
