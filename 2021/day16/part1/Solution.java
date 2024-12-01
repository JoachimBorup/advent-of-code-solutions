import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Packet packet = parsePacket(parseHexadecimal(sc.nextLine()));
        System.out.println(sumOfVersions(packet));
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
        int version = parseBits(packet, 3);
        packet = packet.substring(3);

        int typeID = parseBits(packet,  3);
        packet = packet.substring(3);

        List<Packet> subPackets = new ArrayList<>();

        if (typeID == 4) {
            while (packet.charAt(0) == '1') {
                packet = packet.substring(5);
            }

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

        return new Packet(packet, subPackets, version);
    }

    private static int parseBits(String packet, int length) {
        return Integer.parseInt(packet.substring(0, length), 2);
    }

    private static int sumOfVersions(Packet packet) {
        Stack<Packet> packets = new Stack<>();
        packets.push(packet);
        int sum = 0;

        while (!packets.isEmpty()) {
            Packet p = packets.pop();
            sum += p.version;

            for (Packet subPacket : p.subPackets) {
                packets.push(subPacket);
            }
        }

        return sum;
    }

    private static class Packet {
        public final List<Packet> subPackets;
        public final String remaining;
        public final int version;

        public Packet(String remaining, List<Packet> subPackets, int version) {
            this.remaining = remaining;
            this.subPackets = subPackets;
            this.version = version;
        }
    }
}
