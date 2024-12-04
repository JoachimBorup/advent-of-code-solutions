import itertools
import sys

grid = [line.strip() for line in sys.stdin]
part1 = part2 = 0

directions = list(itertools.product([-1, 0, 1], repeat=2))
diagonals = [(-1, 1), (1, 1)]
XMAS = "XMAS"

def out_of_bounds(i, j):
    return i < 0 or j < 0 or i >= len(grid) or j >= len(grid[i])

def search_xmas(i, j):
    matches = 0

    for x, y in directions:
        for k in range(1, len(XMAS)):
            nx, ny = i + k * x, j + k * y
            if out_of_bounds(nx, ny) or grid[nx][ny] != XMAS[k]:
                break
        else:
            matches += 1

    return matches

def search_mas(i, j):
    for x, y in [(-1, 1), (1, 1)]:
        nx, ny = i + x, j + y
        px, py = i - x, j - y
        if out_of_bounds(nx, ny) or out_of_bounds(px, py):
            return 0
        if grid[nx][ny] == XMAS[1] and grid[px][py] == XMAS[3]:
            continue
        if grid[nx][ny] == XMAS[3] and grid[px][py] == XMAS[1]:
            continue
        return 0

    return 1

for i in range(len(grid)):
    for j in range(len(grid[i])):
        if grid[i][j] == 'X':
            part1 += search_xmas(i, j)
        if grid[i][j] == 'A':
            part2 += search_mas(i, j)

print(f'Part 1: {part1}')
print(f'Part 2: {part2}')
