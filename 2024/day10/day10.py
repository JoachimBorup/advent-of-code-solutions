import sys

grid = [line.strip() for line in sys.stdin]
trailheads = set()

for y, row in enumerate(grid):
    for x, cell in enumerate(row):
        if cell == '0':
            trailheads.add((y, x))

def out_of_bounds(y, x):
    return y < 0 or y >= len(grid) or x < 0 or x >= len(grid[0])

directions = [(0, 1), (0, -1), (1, 0), (-1, 0)]
part_1 = part_2 = 0

for y, x in trailheads:
    stack = [(y, x)]
    visited = set()
    reachable_nines = set()
    trailhead_rating = 0

    while stack:
        y, x = stack.pop()
        visited.add((y, x))

        if grid[y][x] == '9':
            reachable_nines.add((y, x))
            trailhead_rating += 1
            continue

        for dy, dx in directions:
            ny, nx = y + dy, x + dx
            if out_of_bounds(ny, nx):
                continue
            if ord(grid[ny][nx]) == ord(grid[y][x]) + 1:
                stack.append((ny, nx))

    part_1 += len(reachable_nines)
    part_2 += trailhead_rating

print(f'Part 1: {part_1}')
print(f'Part 2: {part_2}')
