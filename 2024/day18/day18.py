import sys

bytes = [tuple(map(int, line.strip().split(','))) for line in sys.stdin]
w, h = (max(x) + 1 for x in zip(*bytes))

def bfs(bytes_to_add):
    corrupted_space = {byte for byte in bytes[:bytes_to_add]}
    directions = [(1, 0), (0, 1), (-1, 0), (0, -1)]
    visited = {(0, 0)}
    queue = [(0, 0)]
    distance = 0

    while queue:
        next_queue = []
        for x, y in queue:
            if (x, y) == (w - 1, h - 1):
                return distance

            for dx, dy in directions:
                nx, ny = x + dx, y + dy
                if nx < 0 or nx >= w or ny < 0 or ny >= h:
                    continue
                if (nx, ny) in visited or (nx, ny) in corrupted_space:
                    continue
                visited.add((nx, ny))
                next_queue.append((nx, ny))
        queue = next_queue
        distance += 1
    
    return -1

print(f'Part 1: {bfs(1024)}')

low, high = 1, len(bytes)
while low < high:
    mid = (low + high) // 2
    if bfs(mid) == -1:
        high = mid
    else:
        low = mid + 1

byte = bytes[low - 1]
print(f'Part 2: {byte[0]},{byte[1]}')
