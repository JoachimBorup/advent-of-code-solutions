import math
import sys

equations = []
for line in sys.stdin:
    test_value, numbers = line.strip().split(': ')
    equations.append((int(test_value), list(map(int, numbers.split(' ')))))

def can_be_made_true(test_value, numbers, operations):
    def solve(result, numbers):
        if len(numbers) == 0:
            return result == test_value
        if result > test_value:
            return False
        for op in operations:
            if solve(op(result, numbers[0]), numbers[1:]):
                return True
        return False

    return solve(numbers[0], numbers[1:])

add = lambda x, y: x + y
multiply = lambda x, y: x * y
concatenate = lambda x, y: x * (10 ** (math.floor(math.log10(y)) + 1)) + y
# concatenate = lambda x, y: int(f'{x}{y}')

print(f'Part 1: {sum(t for t, ns in equations if can_be_made_true(t, ns, [add, multiply]))}')
print(f'Part 2: {sum(t for t, ns in equations if can_be_made_true(t, ns, [add, multiply, concatenate]))}')
