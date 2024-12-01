from itertools import combinations
from sys import stdin


def sum_lengths(mult: int) -> int:
    sum = 0
    for (x1, y1), (x2, y2) in combinations(galaxies, 2):
        empty_rows_between = len({y for y in empty_rows if min(y1, y2) < y < max(y1, y2)})
        empty_cols_between = len({x for x in empty_cols if min(x1, x2) < x < max(x1, x2)})
        sum += abs(y1 - y2) + abs(x1 - x2) + (mult - 1) * (empty_rows_between + empty_cols_between)
    return sum


grid = [line.strip() for line in stdin.readlines()]
galaxies = {(x, y) for y, row in enumerate(grid) for x, c in enumerate(row) if c == '#'}
empty_rows = {y for y, row in enumerate(grid) if all(c == '.' for c in row)}
empty_cols = {x for x, col in enumerate(zip(*grid)) if all(c == '.' for c in col)}

print('Part 1:', sum_lengths(2))
print('Part 2:', sum_lengths(1_000_000))
