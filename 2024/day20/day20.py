from collections import defaultdict
import itertools
import sys
import time

grid = [line.strip() for line in sys.stdin]

start, racetrack = None, set()
for i, row in enumerate(grid):
    for j, cell in enumerate(row):
        if cell == 'S':
            start = (i, j)
        if cell == '.' or cell == 'E':
            racetrack.add((i, j))

def adj(y, x):
    for dy, dx in ((0, 1), (1, 0), (0, -1), (-1, 0)):
        ny, nx = y + dy, x + dx
        if (ny, nx) in racetrack:
            yield ny, nx

queue = [start]
path = {}
distance = 0

while queue:
    y, x = queue.pop(0)
    path[(y, x)] = distance
    distance += 1

    for ny, nx in adj(y, x):
        if (ny, nx) not in path:
            queue.append((ny, nx))

def adj_cheat(y, x, s):
    y_range = range(max(0, y - s), min(len(grid), y + s + 1))
    x_range = range(max(0, x - s), min(len(grid[0]), x + s + 1))
    for i, j in itertools.product(y_range, x_range):
        if abs(i - y) + abs(j - x) > s:
            continue
        if not (i, j) in racetrack:
            continue
        if path[(i, j)] > path[(y, x)]:
            yield i, j, abs(i - y) + abs(j - x)

def cheats(s):
    time_saved = defaultdict(int)
    for (y, x), d in path.items():
        for ny, nx, nd in adj_cheat(y, x, s):
            time_saved[path[(ny, nx)] - d - nd] += 1
    return time_saved

start_time = time.time()
print(f'Part 1: {sum(c for t, c in cheats(2).items() if t >= 100)}')
print(f'Part 2: {sum(c for t, c in cheats(20).items() if t >= 100)}')
print(f'Elapsed time: {time.time() - start_time:.3f}s')
