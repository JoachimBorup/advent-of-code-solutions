import re
from collections import defaultdict
from functools import reduce
from operator import mul
from sys import stdin

part_1 = 0
part_2 = 0

for line in stdin:
    line = re.split(r'\W+', line.strip())
    cubes = defaultdict(int)

    for n, color in zip(line[2::2], line[3::2]):
        cubes[color] = max(cubes[color], int(n))

    if cubes['red'] <= 12 and cubes['green'] <= 13 and cubes['blue'] <= 14:
        part_1 += int(line[1])
    part_2 += reduce(mul, cubes.values(), 1)

print(f'Part 1: {part_1}')
print(f'Part 2: {part_2}')
