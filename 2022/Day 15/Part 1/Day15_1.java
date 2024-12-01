import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Day15_1 {
    private static final int ROW = 2_000_000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Sensor> sensors = new ArrayList<>();

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;

        while (sc.hasNext()) {
            String[] line = sc.nextLine().split("[^\\d-]+");
            int sX = Integer.parseInt(line[1]);
            int sY = Integer.parseInt(line[2]);
            int bX = Integer.parseInt(line[3]);
            int bY = Integer.parseInt(line[4]);
            Sensor s = Sensor.from(sX, sY, bX, bY);

            sensors.add(s);
            minX = Math.min(minX, sX - s.distanceToBeacon());
            maxX = Math.max(maxX, sX + s.distanceToBeacon());
        }

        long beaconsNotPresent = IntStream
                .range(minX, maxX + 1)
                .mapToObj(x -> new Coordinate(x, ROW))
                .filter(c -> sensors.stream().anyMatch(s -> s.isBeaconAt(c) == Answer.NO))
                .count();

        System.out.println(beaconsNotPresent);
        sc.close();
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

        public int distanceTo(Coordinate c) {
            return Math.abs(sensor.x - c.x) + Math.abs(sensor.y - c.y);
        }

        public int distanceToBeacon() {
            return distanceTo(beacon);
        }
    }
}
