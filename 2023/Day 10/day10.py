import math
from collections import deque
from sys import stdin

grid = [line.strip() for line in stdin.readlines()]
start = 0, 0

for y, line in enumerate(grid):
    for x, char in enumerate(line):
        if char == 'S':
            start = x, y
            break


for line in grid:
    print(line)


NORTH, EAST, SOUTH, WEST = 0, 1, 2, 3

facing = NORTH
prev_x, prev_y = start
x, y = prev_x, prev_y - 1
steps = 0

wall = [[False for _ in range(len(grid[i]))] for i in range(len(grid))]

while (x, y) != start:
    wall[x][y] = True
    steps += 1

    if grid[y][x] == '|':
        if not (facing == NORTH or facing == SOUTH):
            raise Exception(f'Cannot move {facing} on |')
    elif grid[y][x] == '-':
        if not (facing == WEST or facing == EAST):
            raise Exception(f'Cannot move {facing} on -')
    elif grid[y][x] == 'L':
        if facing == WEST:
            facing = NORTH
        elif facing == SOUTH:
            facing = EAST
        else:
            raise Exception(f'Cannot move {facing} on L')
    elif grid[y][x] == 'J':
        if facing == EAST:
            facing = NORTH
        elif facing == SOUTH:
            facing = WEST
        else:
            raise Exception(f'Cannot move {facing} on J')
    elif grid[y][x] == '7':
        if facing == NORTH:
            facing = WEST
        elif facing == EAST:
            facing = SOUTH
        else:
            raise Exception(f'Cannot move {facing} on 7')
    elif grid[y][x] == 'F':
        if facing == NORTH:
            facing = EAST
        elif facing == WEST:
            facing = SOUTH
        else:
            raise Exception(f'Cannot move {facing} on F')
    else:
        raise Exception(f'Unknown character {grid[y][x]} at {x}, {y}')

    if facing == NORTH:
        y -= 1
    elif facing == EAST:
        x += 1
    elif facing == SOUTH:
        y += 1
    elif facing == WEST:
        x -= 1
    else:
        raise Exception(f'Unknown direction {facing}')


# for y, line in enumerate(grid):
#     for x, char in enumerate(line):
#         if (x, y) in visited:
#             print(f'#', end='')
#         else:
#             print(f'{char}', end='')
#     print()

print(math.ceil(steps / 2))


# Part 2

# BFS

upscaled_grid = [[' ' for _ in range(len(grid[0]) * 3)] for _ in range(len(grid) * 3)]
for y, line in enumerate(grid):
    for x, char in enumerate(line):

        upscaled_grid[y * 3 + 1][x * 3 + 1] = char
        if char == '|':
            upscaled_grid[y * 3][x * 3 + 1] = '|'
            upscaled_grid[y * 3 + 2][x * 3 + 1] = '|'
        elif char == '-':
            upscaled_grid[y * 3 + 1][x * 3] = '-'
            upscaled_grid[y * 3 + 1][x * 3 + 2] = '-'
        elif char == 'L':
            upscaled_grid[y * 3][x * 3 + 1] = '|'
            upscaled_grid[y * 3 + 1][x * 3 + 2] = '-'
        elif char == 'J':
            upscaled_grid[y * 3][x * 3 + 1] = '|'
            upscaled_grid[y * 3 + 1][x * 3] = '-'
        elif char == '7':
            upscaled_grid[y * 3 + 2][x * 3 + 1] = '|'
            upscaled_grid[y * 3 + 1][x * 3] = '-'
        elif char == 'F':
            upscaled_grid[y * 3 + 2][x * 3 + 1] = '|'
            upscaled_grid[y * 3 + 1][x * 3 + 2] = '-'
        elif char == 'S':
            upscaled_grid[y * 3][x * 3 + 1] = '|'
            upscaled_grid[y * 3 + 2][x * 3 + 1] = '|'

for line in upscaled_grid:
    print(''.join(line))

x, y = start
queue = deque()
queue.append((x * 3, y * 3 + 1))  # Start to the left of the start
tiles_inside_wall = set()
visited = [[False for _ in range(len(upscaled_grid[i]))] for i in range(len(upscaled_grid))]
iterations = 0

while queue:
    up_x, up_y = queue.popleft()
    x, y = up_x // 3, up_y // 3
    if up_y < 0 or up_x < 0:
        continue
    # iterations += 1
    # if iterations % 10000 == 0:
    #     print(f'Iteration {iterations}: {len(queue)} tiles left')
    try:
        if visited[up_x][up_y] or upscaled_grid[up_y][up_x] != ' ':
            continue
    except IndexError:
        continue
    if not wall[x][y]:
        tiles_inside_wall.add((x, y))
    visited[up_x][up_y] = True

    queue.append((up_x + 1, up_y))
    queue.append((up_x - 1, up_y))
    queue.append((up_x, up_y + 1))
    queue.append((up_x, up_y - 1))

print(iterations)

# for y, line in enumerate(grid):
#     for x, char in enumerate(line):
#         if wall[x][y]:
#             tiles_inside_wall.remove((x, y))

print(len(tiles_inside_wall))
# print(tiles_inside_wall)
