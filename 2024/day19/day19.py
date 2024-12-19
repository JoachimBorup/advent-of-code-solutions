from functools import cache
import sys

patterns, designs = sys.stdin.read().strip().split('\n\n')
patterns = patterns.split(', ')
designs = designs.split()

@cache
def ways_to_make_design(design):
    if not design:
        return 1

    total_ways = 0
    for pattern in patterns:
        if design.startswith(pattern):
            total_ways += ways_to_make_design(design[len(pattern):])

    return total_ways

results = [ways_to_make_design(design) for design in designs]
print(f'Part 1: {sum(result > 0 for result in results)}')
print(f'Part 2: {sum(results)}')
