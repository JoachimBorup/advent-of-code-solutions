from ast import literal_eval
from sys import stdin

def compare(left, right):
    if isinstance(left, int) and isinstance(right, int):
        return 0 if left == right else (-1 if left < right else 1)
    elif isinstance(left, int):
        return compare([left], right)
    elif isinstance(right, int):
        return compare(left, [right])
    else:
        for i in range(min(len(left), len(right))):
            cmp = compare(left[i], right[i])
            if cmp != 0:
                return cmp
        return compare(len(left), len(right))

sum = 0
for i, pair in enumerate(stdin.read().split("\n\n")):
    left, right = map(literal_eval, pair.split('\n'))
    if compare(left, right) < 1:
        sum += i + 1

print(sum)
