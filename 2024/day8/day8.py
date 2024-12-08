from collections import defaultdict
import itertools
import sys

grid = [line.strip() for line in sys.stdin]
antennas = defaultdict(set)
for i, row in enumerate(grid):
    for j, node in enumerate(row):
        if node != '.':
            antennas[node].add((i, j))

def add(a, b): return a[0] + b[0], a[1] + b[1]
def sub(a, b): return a[0] - b[0], a[1] - b[1]
def out_of_bounds(a): return not (0 <= a[0] < len(grid) and 0 <= a[1] < len(grid[a[0]]))

antinodes_part1 = set()
antinodes_part2 = set()
for antenna, nodes in antennas.items():
    for a, b in itertools.combinations(nodes, 2):
        diff = sub(a, b)

        for antinode in [add(a, diff), sub(b, diff)]:
            if not out_of_bounds(antinode):
                antinodes_part1.add(antinode)

        for extend in [add, sub]:
            x = a  # Antennas must also be antinodes
            while not out_of_bounds(x):
                antinodes_part2.add(x)
                x = extend(x, diff)

print(f'Part 1: {len(antinodes_part1)}')
print(f'Part 2: {len(antinodes_part2)}')
