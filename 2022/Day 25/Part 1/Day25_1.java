import java.util.Scanner;

public class Day25_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long sum = 0;

        while (sc.hasNext()) {
            sum += fromSnafu(sc.next());
        }

        System.out.println(toSnafu(sum));
        sc.close();
    }

    private static String toSnafu(long number) {
        StringBuilder sb = new StringBuilder("2");
        while (fromSnafu(sb.toString()) < number) {
            sb.append("2");
        }

        char[] temp = new char[] { '1', '0', '-', '=' };

        for (int i = 0; i < sb.length(); i++) {
            for (char ch : temp) {
                char prev = sb.charAt(i);
                sb.setCharAt(i, ch);

                if (fromSnafu(sb.toString()) < number) {
                    sb.setCharAt(i, prev);
                    break;
                }
            }
        }

        return sb.toString();
    }

    private static long fromSnafu(String snafu) {
        long sum = 0;
        for (int i = 0; i < snafu.length(); i++) {
            sum += toDigit(snafu.charAt(i)) * Math.pow(5, snafu.length() - i - 1);
        }
        return sum;
    }

    private static long toDigit(char c) {
        return switch (c) {
            case '2' -> 2;
            case '1' -> 1;
            case '0' -> 0;
            case '-' -> -1;
            case '=' -> -2;
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
}
