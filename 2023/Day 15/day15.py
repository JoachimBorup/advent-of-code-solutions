import re
from functools import reduce
from sys import stdin


def hash(string: str) -> int:
    return reduce(lambda a, b: (a + b) * 17, map(ord, string), 0) % 256


def hashmap(sequence: list[str]) -> int:
    boxes = [{} for _ in range(256)]
    focal_power = 0

    for step in sequence:
        print(step)
        temp = re.findall(r'(\w+|[=-]|\d*)', step)
        print(temp)
        label, operation, focal_length = re.findall(r'(\w+|[=-]|\d*)', step)
        box = boxes[hash(label)]

        if operation == '=':
            box[label] = int(focal_length)
        elif operation == '-':
            if label in box:
                del box[label]

    for box_i, box in enumerate(boxes, start=1):
        for slot, (label, focal_length) in enumerate(box.items(), start=1):
            focal_power += box_i * slot * focal_length

    return focal_power


sequence = stdin.read().strip().split(',')
print('Part 1:', sum(map(hash, sequence)))
print('Part 2:', hashmap(sequence))
