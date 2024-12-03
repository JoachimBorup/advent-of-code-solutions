import re
import sys

lines = sys.stdin.readlines()
part_1 = part_2 = 0
enabled = True

for line in lines:
    for match in re.findall(r"(mul)\((\d+),(\d+)\)|(do(?:n't)?)\(\)", line):
        if match[0] == "mul":
            x, y = int(match[1]), int(match[2])
            part_1 += x * y
            if enabled:
                part_2 += x * y
        elif match[3] == "do":
            enabled = True
        elif match[3] == "don't":
            enabled = False

print(f'Part 1: {part_1}')
print(f'Part 2: {part_2}')
