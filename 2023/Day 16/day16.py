from collections import deque
from sys import stdin

UP, DOWN, LEFT, RIGHT = 0, 1, 2, 3


def count_energized_tiles(grid, start):
    queue = deque([start])
    visited = set()

    while len(queue) > 0:
        x, y, direction = queue.popleft()
        if not (0 <= y < len(grid) and 0 <= x < len(grid[y])):
            continue
        if (x, y, direction) in visited:
            continue
        visited.add((x, y, direction))

        def move(direction):
            if direction == UP:
                return x, y - 1, direction
            elif direction == DOWN:
                return x, y + 1, direction
            elif direction == LEFT:
                return x - 1, y, direction
            elif direction == RIGHT:
                return x + 1, y, direction
            else:
                raise ValueError('Invalid direction')

        if grid[y][x] == '|':
            if direction == RIGHT or direction == LEFT:
                queue.append(move(DOWN))
                queue.append(move(UP))
            else:
                queue.append(move(direction))
        elif grid[y][x] == '-':
            if direction == UP or direction == DOWN:
                queue.append(move(RIGHT))
                queue.append(move(LEFT))
            else:
                queue.append(move(direction))
        elif grid[y][x] == '/':
            if direction == UP:
                queue.append(move(RIGHT))
            elif direction == DOWN:
                queue.append(move(LEFT))
            elif direction == LEFT:
                queue.append(move(DOWN))
            elif direction == RIGHT:
                queue.append(move(UP))
        elif grid[y][x] == '\\':
            if direction == UP:
                queue.append(move(LEFT))
            elif direction == DOWN:
                queue.append(move(RIGHT))
            elif direction == LEFT:
                queue.append(move(UP))
            elif direction == RIGHT:
                queue.append(move(DOWN))
        else:
            queue.append(move(direction))

    return len(set(map(lambda x: (x[0], x[1]), visited)))


def beam_entrances(grid):
    starts = []
    for i in range(len(grid)):
        starts.append((0, i, RIGHT))
        starts.append((len(grid[i]) - 1, i, LEFT))
    for i in range(len(grid[0])):
        starts.append((i, 0, DOWN))
        starts.append((i, len(grid) - 1, UP))
    return starts


grid = [line.strip() for line in stdin.readlines()]
print('Part 1:', count_energized_tiles(grid, (0, 0, RIGHT)))
print('Part 2:', max(count_energized_tiles(grid, start) for start in beam_entrances(grid)))
