import sys

farm = [line.strip() for line in sys.stdin]

directions = [(0, 1), (0, -1), (1, 0), (-1, 0)]
corner_pairs = [
    ((-1, 0), (0, -1)),  # NW
    ((-1, 0), (0, 1)),   # NE
    ((1, 0), (0, -1)),   # SW
    ((1, 0), (0, 1)),    # SE
]

def add(*vectors):
    return tuple(sum(coords) for coords in zip(*vectors))

def find_regions(farm):
    def bfs_find_region(y, x):
        plant_type = farm[y][x]
        queue = [(y, x)]
        region = set()

        while queue:
            c = queue.pop()
            if c in region:
                continue
            region.add(c)
            for d in directions:
                ny, nx = add(c, d)
                if 0 <= ny < len(farm) and 0 <= nx < len(farm[0]) and farm[ny][nx] == plant_type:
                    queue.append((ny, nx))

        return frozenset(region)

    visited = set()
    regions = set()

    for y in range(len(farm)):
        for x in range(len(farm[0])):
            if (y, x) not in visited:
                region = bfs_find_region(y, x)
                regions.add(region)
                visited.update(region)
    
    return regions

def perimeter(region):
    return sum(add(c, d) not in region for c in region for d in directions)

def corners(region):
    corners = 0
    for c in region:
        for d1, d2 in corner_pairs:
            diagonal = add(c, d1, d2)

            n1_in = add(c, d1) in region
            n2_in = add(c, d2) in region
            diag_in = (diagonal in region)

            # Exterior corner: both neighbors are out
            if not n1_in and not n2_in:
                corners += 1
            # Interior corner: both neighbors are in, but diagonal is out
            elif n1_in and n2_in and not diag_in:
                corners += 1

    return corners

def price(regions, function):
    return sum(len(region) * function(region) for region in regions)

regions = find_regions(farm)
print(f'Part 1: {price(regions, perimeter)}')
print(f'Part 2: {price(regions, corners)}')
