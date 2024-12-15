import sys

grid, movements = map(str.split, sys.stdin.read().strip().split('\n\n'))

boxes, walls, robot = set(), set(), (0, 0)
for y, row in enumerate(grid):
    for x, cell in enumerate(row):
        if cell == '#':
            walls.add((y, x))
        elif cell == 'O':
            boxes.add((y, x))
        elif cell == '@':
            robot = (y, x)

directions = {'^': (-1, 0), 'v': (1, 0), '<': (0, -1), '>': (0, 1)}
instructions = [directions[arrow] for line in movements for arrow in line]

def adjacent_cell(pos, direction):
    return (pos[0] + direction[0], pos[1] + direction[1])

for instruction in instructions:
    next_robot = adjacent_cell(robot, instruction)
    box = next_robot
    while box in boxes:
        box = adjacent_cell(box, instruction)
    if box not in walls:
        if next_robot in boxes:
            boxes.remove(next_robot)
            boxes.add(box)
        robot = next_robot

print(f'Part 1: {sum(100 * y + x for y, x in boxes)}')

boxes, walls, robot = set(), set(), (0, 0)
for y, row in enumerate(grid):
    for x, cell in enumerate(row):
        if cell == '#':
            walls.add((y, x * 2))
            walls.add((y, x * 2 + 1))
        elif cell == 'O':
            boxes.add((y, x * 2))
        elif cell == '@':
            robot = (y, x * 2)

def find_box(pos, instruction):
    ny, nx = pos
    if instruction[1] == -1:
        if (ny, nx - 1) in boxes:
            return (ny, nx - 1)
    elif instruction[0] == 0:
        if (ny, nx) in boxes:
            return (ny, nx)
    elif (ny, nx) in boxes:
        return (ny, nx)
    elif (ny, nx - 1) in boxes:
        return (ny, nx - 1)

    return None

def blocks_adjacent_to_box(pos, instruction):
    ny, nx = adjacent_cell(pos, instruction)
    if instruction[0] == 0:
        box_spots = [(ny, nx + instruction[1])]
    else:
        box_spots = [(ny, nx - 1), (ny, nx), (ny, nx + 1)]
    return [box for box in box_spots if box in boxes]

def is_blocked(box, instruction):
    ny, nx = adjacent_cell(box, instruction)
    if instruction[0] == 0:
        if instruction[1] == 1:
            nx += 1
        return (ny, nx) in walls
    else:
        return (ny, nx) in walls or (ny, nx + 1) in walls

def check_boxes(box, instruction):
    boxes_to_move = set()

    def check_box(box):
        if is_blocked(box, instruction):
            return False
        if box not in boxes or box in boxes_to_move:
            return True
        boxes_to_move.add(box)

        for adj_box in blocks_adjacent_to_box(box, instruction):
            if adj_box in walls:
                return False
            if not check_box(adj_box):
                return False
        return True

    return boxes_to_move, check_box(box)


for instruction in instructions:
    next_robot = adjacent_cell(robot, instruction)
    if next_robot in walls:
        continue

    next_box = find_box(next_robot, instruction)
    if not next_box:
        robot = next_robot
        continue

    boxes_to_move, can_move = check_boxes(next_box, instruction)
    if not can_move:
        continue

    for box in boxes_to_move:
        boxes.remove(box)
    for box in boxes_to_move:
        boxes.add(adjacent_cell(box, instruction))

    robot = next_robot

print(f'Part 2: {sum(100 * y + x for y, x in boxes)}')
