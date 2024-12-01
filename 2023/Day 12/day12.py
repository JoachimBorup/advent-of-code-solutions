import re
from functools import cache
from sys import stdin


@cache
def count_arrangements(row: str, groups: tuple[int]) -> int:
    if len(groups):
        return 1 if '#' not in row else 0

    group, groups = groups[0], groups[1:]
    count = 0

    for pos in [m.start() for m in re.finditer('?' * group, row)]:
        next_row = row[:pos] + row[pos:].replace('?' * group, '#' * group, 1)
        # TODO: If the next character is '?', it should be a '.'
        count += count_arrangements(next_row, groups)

    return count


lines = [line.strip().split() for line in stdin.readlines()]
rows = [(row, tuple(map(int, groups.split(',')))) for row, groups in lines]

print('Part 1:', sum(count_arrangements(row, groups) for row, groups in rows))
# print('Part 2:', sum(count_arrangements('?'.join([row] * 5), groups) for row, groups in rows))
