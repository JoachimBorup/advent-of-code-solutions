import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day19_1 {
    private static final int MAX_MINUTES = 24;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int qualityLevel = 0;

        for (int i = 1; sc.hasNext(); i++) {
            RobotFactory factory = new RobotFactory(sc.nextLine().split("\\D+"));
            qualityLevel += i * factory.maxGeodes();
        }

        System.out.println(qualityLevel);
        sc.close();
    }

    private static class RobotFactory {
        private final Robot oreRobot, clayRobot, obsidianRobot, geodeRobot;
        private final int maxOreCost, maxClayCost, maxObsidianCost;
        private final Set<FactoryState> visitedStates;
        private int maxGeodes;

        public RobotFactory(String[] input) {
            this.oreRobot = new Robot(Integer.parseInt(input[2]), 0, 0);
            this.clayRobot = new Robot(Integer.parseInt(input[3]), 0, 0);
            this.obsidianRobot = new Robot(Integer.parseInt(input[4]), Integer.parseInt(input[5]), 0);
            this.geodeRobot = new Robot(Integer.parseInt(input[6]), 0, Integer.parseInt(input[7]));

            this.maxOreCost = Math.max(Math.max(Math.max(oreRobot.oreCost(), clayRobot.oreCost()), obsidianRobot.oreCost()), geodeRobot.oreCost());
            this.maxClayCost = obsidianRobot.clayCost();
            this.maxObsidianCost = geodeRobot.obsidianCost();

            this.visitedStates = new HashSet<>();
            this.maxGeodes = 0;
        }

        public int maxGeodes() {
            maxGeodes(new FactoryState(1, 0, 0, 0, 0, 0, 0, 0, 0));
            return maxGeodes;
        }

        public void maxGeodes(FactoryState state) {
            if (state.minutes() >= MAX_MINUTES) {
                maxGeodes = Math.max(maxGeodes, state.geode());
                return;
            }
            if (visitedStates.contains(state)) {
                return;
            }

            visitedStates.add(state);
            int minutesLeft = MAX_MINUTES - state.minutes();
            int maxGeodesPossible = state.geode();

            for(int i = 0; i < minutesLeft; i++) {
                maxGeodesPossible += state.geodeRobots() + i;
            }
            if (maxGeodesPossible < this.maxGeodes) {
                return;
            }

            if (state.oreRobots() < this.maxOreCost && this.oreRobot.canBeBuiltWith(state)) {
                maxGeodes(state.buildOreRobot(this.oreRobot));
            }
            if (state.clayRobots() < this.maxClayCost && this.clayRobot.canBeBuiltWith(state)) {
                maxGeodes(state.buildClayRobot(this.clayRobot));
            }
            if (state.obsidianRobots() < this.maxObsidianCost && this.obsidianRobot.canBeBuiltWith(state)) {
                maxGeodes(state.buildObsidianRobot(this.obsidianRobot));
            }
            if (this.geodeRobot.canBeBuiltWith(state)) {
                maxGeodes(state.buildGeodeRobot(this.geodeRobot));
            }

            maxGeodes(state.buildNothing());
        }
    }

    private record Robot(int oreCost, int clayCost, int obsidianCost) {
        public boolean canBeBuiltWith(FactoryState state) {
            return state.ore() >= this.oreCost() && state.clay() >= this.clayCost() && state.obsidian() >= this.obsidianCost();
        }
    }

    private record FactoryState(
        int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots,
        int ore, int clay, int obsidian, int geode,
        int minutes
    ) {
        public FactoryState buildOreRobot(Robot robot) {
            return buildRobot(1, 0, 0, 0, robot);
        }

        public FactoryState buildClayRobot(Robot robot) {
            return buildRobot(0, 1, 0, 0, robot);
        }

        public FactoryState buildObsidianRobot(Robot robot) {
            return buildRobot(0, 0, 1, 0, robot);
        }

        public FactoryState buildGeodeRobot(Robot robot) {
            return buildRobot(0, 0, 0, 1, robot);
        }

        private FactoryState buildRobot(int oreRobot, int clayRobot, int obsidianRobot, int geodeRobot, Robot robot) {
            return nextState(oreRobot, clayRobot, obsidianRobot, geodeRobot, robot.oreCost(), robot.clayCost(), robot.obsidianCost());
        }

        public FactoryState buildNothing() {
            return nextState(0, 0, 0, 0, 0, 0, 0);
        }

        private FactoryState nextState(int oreRobot, int clayRobot, int obsidianRobot, int geodeRobot, int ore, int clay, int obsidian) {
            return new FactoryState(
                this.oreRobots() + oreRobot, this.clayRobots() + clayRobot, this.obsidianRobots() + obsidianRobot, this.geodeRobots() + geodeRobot,
                this.ore() + this.oreRobots() - ore, this.clay() + this.clayRobots() - clay, this.obsidian() + this.obsidianRobots() - obsidian, this.geode() + this.geodeRobots(),
                this.minutes() + 1
            );
        }
    }
}
