import re
from collections import defaultdict
from sys import stdin

id_sum = 0

for line in stdin:
    line = re.split(r'\W+', line.strip())
    cubes = defaultdict(int)

    for n, color in zip(line[2::2], line[3::2]):
        cubes[color] = max(cubes[color], int(n))

    if cubes['red'] <= 12 and cubes['green'] <= 13 and cubes['blue'] <= 14:
        id_sum += int(line[1])

print(id_sum)
