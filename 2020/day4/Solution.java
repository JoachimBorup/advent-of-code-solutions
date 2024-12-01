import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean byr1 = false, iyr1 = false, eyr1 = false, hgt1 = false, hcl1 = false, ecl1 = false, pid1 = false;
        boolean byr2 = false, iyr2 = false, eyr2 = false, hgt2 = false, hcl2 = false, ecl2 = false, pid2 = false;
        int ans1 = 0, ans2 = 0;
        while (in.hasNext()) {
            String line = in.nextLine();
            if (line.equals("")) {
                if (byr1 && iyr1 && eyr1 && hgt1 && hcl1 && ecl1 && pid1) ans1++;
                if (byr2 && iyr2 && eyr2 && hgt2 && hcl2 && ecl2 && pid2) ans2++;
                byr1 = false; byr2 = false;
                iyr1 = false; iyr2 = false;
                eyr1 = false; eyr2 = false;
                hgt1 = false; hgt2 = false;
                hcl1 = false; hcl2 = false;
                ecl1 = false; ecl2 = false;
                pid1 = false; pid2 = false;
            } else {
                String[] input = line.split(" ");
                for (String fields : input) {
                    String[] field = fields.split(":");
                    String key = field[0], value = field[1];
                    switch (key) {
                        case "byr" -> {
                            int year = Integer.parseInt(value);
                            if (year >= 1920 && year <= 2002) byr2 = true;
                            byr1 = true;
                        }
                        case "iyr" -> {
                            int year = Integer.parseInt(value);
                            if (year >= 2010 && year <= 2020) iyr2 = true;
                            iyr1 = true;
                        }
                        case "eyr" -> {
                            int year = Integer.parseInt(value);
                            if (year >= 2020 && year <= 2030) eyr2 = true;
                            eyr1 = true;
                        }
                        case "hgt" -> {
                            try {
                                int index = value.length() - 2;
                                String unit = value.substring(index);
                                int height = Integer.parseInt(value.substring(0, index));
                                if (unit.equals("cm") && height >= 150 && height <= 193) hgt2 = true;
                                else if (unit.equals("in") && height >= 59 && height <= 76) hgt2 = true;
                            } catch (NumberFormatException ignored) {}
                            hgt1 = true;
                        }
                        case "hcl" -> {
                            if (value.length() == 7 && value.charAt(0) == '#'
                                    && value.substring(1).matches("[#a-f0-9]+")) hcl2 = true;
                            hcl1 = true;
                        }
                        case "ecl" -> {
                            switch (value) {
                                case "amb", "blu", "brn", "gry", "grn", "hzl", "oth" -> ecl2 = true;
                            }
                            ecl1 = true;
                        }
                        case "pid" -> {
                            if (value.length() == 9 && value.matches("[0-9]+")) pid2 = true;
                            pid1 = true;
                        }
                    }
                }
            }
        }
        System.out.println("Part 1: " + ans1);
        System.out.println("Part 2: " + ans2);
        in.close();
    }
}
