import math
import re
from sys import stdin

instructions, _, *nodes = [line.strip() for line in stdin.readlines()]
graph = {}
for node in nodes:
    node, left, right = re.split(r'[ =(,)]+', node)[:3]
    graph[node] = (left, right)


def navigate(node: str = 'AAA', ends_with: str = 'ZZZ'):
    steps = 0

    while not node.endswith(ends_with):
        left, right = graph[node]
        node = left if instructions[steps % len(instructions)] == 'L' else right
        steps += 1

    return steps


def part_1():
    print(navigate())


def part_2():
    sources = [node for node in graph if node[-1] == 'A']
    steps = [navigate(node, ends_with='Z') for node in sources]
    print(math.lcm(*steps))


part_2()
