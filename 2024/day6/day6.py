import sys
import time

grid = [list(line.strip()) for line in sys.stdin]
y, x = next((i, row.index('^')) for i, row in enumerate(grid) if '^' in row)

directions = [(-1, 0), (0, 1), (1, 0), (0, -1)]
direction = 0

visited = {(y, x)}
visited_directed = {(y, x, direction)}
loop_positions = set()


def out_of_bounds(i: int, j: int) -> bool:
    return i < 0 or j < 0 or i >= len(grid) or j >= len(grid[0])


def has_loop(y: int, x: int, direction: int) -> bool:
    temp_visited = visited_directed.copy()

    while True:
        dy, dx = directions[direction]
        ny, nx = y + dy, x + dx
        if out_of_bounds(ny, nx):
            return False

        if grid[ny][nx] == '#':
            direction = (direction + 1) % 4
        elif (ny, nx, direction) in temp_visited:
            return True
        else:
            y, x = ny, nx
            temp_visited.add((y, x, direction))


while True:
    dy, dx = directions[direction]
    ny, nx = y + dy, x + dx
    if out_of_bounds(ny, nx):
        break

    if grid[ny][nx] == '#':
        direction = (direction + 1) % 4
    else:
        grid[ny][nx] = '#'
        if (ny, nx) not in visited and has_loop(y, x, direction):
            loop_positions.add((ny, nx))
        grid[ny][nx] = '.'

        y, x = ny, nx
        visited.add((y, x))
        visited_directed.add((y, x, direction))

print(f'Part 1: {len(visited)}')
print(f'Part 2: {len(loop_positions)}')
