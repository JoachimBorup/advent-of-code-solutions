import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Packet packet = parsePacket(parseHexadecimal(sc.nextLine()));
        System.out.println(evaluate(packet));
        sc.close();
    }

    private static String parseHexadecimal(String input) {
        StringBuilder sb = new StringBuilder();

        for (char ch : input.toCharArray()) {
            sb.append(hexadecimalToBinary(ch));
        }

        return sb.toString();
    }

    private static String hexadecimalToBinary(char ch) {
        return switch (ch) {
            case '0' -> "0000";
            case '1' -> "0001";
            case '2' -> "0010";
            case '3' -> "0011";
            case '4' -> "0100";
            case '5' -> "0101";
            case '6' -> "0110";
            case '7' -> "0111";
            case '8' -> "1000";
            case '9' -> "1001";
            case 'A' -> "1010";
            case 'B' -> "1011";
            case 'C' -> "1100";
            case 'D' -> "1101";
            case 'E' -> "1110";
            case 'F' -> "1111";
            default -> throw new IllegalArgumentException();
        };
    }

    private static Packet parsePacket(String packet) {
        packet = packet.substring(3); // Version
        int typeID = parseBits(packet,  3);
        packet = packet.substring(3);

        List<Packet> subPackets = new ArrayList<>();
        StringBuilder value = new StringBuilder();

        if (typeID == 4) {
            while (packet.charAt(0) == '1') {
                value.append(packet, 1, 5);
                packet = packet.substring(5);
            }

            value.append(packet, 1, 5);
            packet = packet.substring(5);
        } else {
            int lengthTypeID = packet.charAt(0) == '0' ? 0 : 1;
            packet = packet.substring(1);

            if (lengthTypeID == 0) {
                int totalLength = parseBits(packet, 15);
                packet = packet.substring(15);

                int originalLength = packet.length();

                while (originalLength - packet.length() < totalLength) {
                    Packet subPacket = parsePacket(packet);
                    subPackets.add(subPacket);
                    packet = subPacket.remaining;
                }
            } else {
                int numberOfSubPackets = parseBits(packet, 11);
                packet = packet.substring(11);

                for (int i = 0; i < numberOfSubPackets; i++) {
                    Packet subPacket = parsePacket(packet);
                    subPackets.add(subPacket);
                    packet = subPacket.remaining;
                }
            }
        }

        return new Packet(packet, subPackets, typeID, value.toString());
    }

    private static int parseBits(String packet, int length) {
        return Integer.parseInt(packet.substring(0, length), 2);
    }

    private static long evaluate(Packet packet) {
        return switch (packet.typeID) {
            case 0 -> packet.subPackets.stream().map(Solution::evaluate).reduce(Long::sum).get();
            case 1 -> packet.subPackets.stream().map(Solution::evaluate).reduce((a, b) -> a * b).get();
            case 2 -> packet.subPackets.stream().map(Solution::evaluate).min(Long::compareTo).get();
            case 3 -> packet.subPackets.stream().map(Solution::evaluate).max(Long::compareTo).get();
            case 4 -> packet.value;
            case 5 -> evaluate(packet.subPackets.get(0)) > evaluate(packet.subPackets.get(1)) ? 1 : 0;
            case 6 -> evaluate(packet.subPackets.get(0)) < evaluate(packet.subPackets.get(1)) ? 1 : 0;
            case 7 -> evaluate(packet.subPackets.get(0)) == evaluate(packet.subPackets.get(1)) ? 1 : 0;
            default -> throw new IllegalArgumentException();
        };
    }

    private static class Packet {
        public final String remaining;
        public final List<Packet> subPackets;
        public final int typeID;
        public final long value;

        public Packet(String remaining, List<Packet> subPackets, int typeID, String value) {
            this.remaining = remaining;
            this.subPackets = subPackets;
            this.typeID = typeID;
            this.value = value.isEmpty() ? 0 : Long.parseLong(value, 2);
        }
    }
}
