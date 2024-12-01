import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day20_1 {
    public static void main(String[] args) {
        List<Node> nodes = readInput();
        mixNodes(nodes);
        System.out.println(sumOfCoordinates(nodes));
    }

    private static void mixNodes(List<Node> nodes) {
        for (Node node : nodes) {
            if (node.value == 0) {
                continue;
            }

            // Remove node from list
            node.next.prev = node.prev;
            node.prev.next = node.next;
            Node temp = node.prev;

            // Traverse list to find new position
            long l = Math.abs(node.value % (nodes.size() - 1));
            for (long i = 0; i < l; i++) {
                temp = node.value < 0 ? temp.prev : temp.next;
            }

            // Insert node at new position
            node.prev = temp;
            node.next = temp.next;
            node.prev.next = node;
            node.next.prev = node;
        }
    }

    private static int sumOfCoordinates(List<Node> nodes) {
        Node node = nodes.stream().filter(n -> n.value == 0).findFirst().orElseThrow();
        int sum = 0;

        for (int i = 1; i <= 3000; i++) {
            node = node.next;

            if (i % 1000 == 0) {
                sum += node.value;
            }
        }

        return sum;
    }

    private static List<Node> readInput() {new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        List<Node> nodes = new ArrayList<>();

        while (sc.hasNext()) {
            nodes.add(new Node(Integer.parseInt(sc.nextLine())));
        }

        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).prev = nodes.get((nodes.size() + i - 1) % nodes.size());
            nodes.get(i).next = nodes.get((i + 1) % nodes.size());
        }

        sc.close();
        return nodes;
    }

    private static class Node {
        private final int value;
        private Node prev;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }
}
