from collections import Counter
import sys

lefts, rights = zip(*[tuple(map(int, line.split())) for line in sys.stdin.readlines()])
distances = [abs(a - b) for a, b in zip(sorted(lefts), sorted(rights))]

print(f'Part 1: {sum(distances)}')

right_counts = Counter(rights)
similarities = [a * right_counts[a] for a in lefts]

print(f'Part 2: {sum(similarities)}')
