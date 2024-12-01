import heapq
from dataclasses import dataclass
from sys import stdin


@dataclass
class Node:
    x: int
    y: int
    weight: int
    direction: int
    steps: int

    def __lt__(self, other):
        return self.weight < other.weight


UP, RIGHT, DOWN, LEFT = 0, 1, 2, 3
grid = [[int(tile) for tile in input().strip()] for line in range(13)]
for line in grid:
    print(line)


def shortest_path(grid):
    priority_queue = [Node(0, 0, 0, RIGHT, 0), Node(0, 0, 0, DOWN, 0)]
    heapq.heapify(priority_queue)
    parent = {}
    visited = set()

    while len(priority_queue) > 0:
        node = priority_queue.pop(0)
        if (node.x, node.y, node.direction) in visited:
            continue
        visited.add((node.x, node.y, node.direction))
        print("Visiting", node.x, node.y, node.weight, node.direction, node.steps)

        def push(x, y, direction):
            if not (0 <= y < len(grid) and 0 <= x < len(grid[y])):
                return
            steps = node.steps + 1 if node.direction == direction else 1
            if steps <= 3:
                heapq.heappush(priority_queue, Node(x, y, node.weight + grid[y][x], direction, steps))
                parent[(x, y, direction)] = (node.x, node.y, node.direction)
        def move(x, y, direction):
            if direction == UP:
                push(x, y - 1, UP)
            elif direction == RIGHT:
                push(x + 1, y, RIGHT)
            elif direction == DOWN:
                push(x, y + 1, DOWN)
            elif direction == LEFT:
                push(x - 1, y, LEFT)
            else:
                raise Exception("Invalid direction")

        if node.y == len(grid) - 1 and node.x == len(grid[node.y]) - 1:
            return node.weight, visited
        move(node.x, node.y, (node.direction + 3) % 4)  # Left
        move(node.x, node.y, (node.direction + 1) % 4)  # Right
        if node.steps < 3:
            move(node.x, node.y, node.direction)        # Forward

    return None


# Replace with parent
res, visited = shortest_path(grid)
visited = set(map(lambda x: (x[0], x[1]), visited))
for y, line in enumerate(grid):
    for x, tile in enumerate(line):
        if (x, y) in visited:
            print("X", end="")
        else:
            print(' ', end="")
    print()
print(res)
