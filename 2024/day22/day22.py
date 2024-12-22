from collections import defaultdict
import sys

def evolve(n):
    n ^= n * 64
    n %= 16777216
    n ^= n // 32
    n %= 16777216
    n ^= n * 2048
    n %= 16777216
    return n

secret_sum = 0
num_bananas = defaultdict(int)

for n in map(int, sys.stdin):
    sequence = [n]
    for _ in range(2000):
        n = evolve(n)
        sequence.append(n)
    secret_sum += sequence[-1]

    differences = [b % 10 - a % 10 for a, b in zip(sequence, sequence[1:])]
    visited = set()

    for i in range(len(sequence) - 4):
        pattern = tuple(differences[i:i + 4])
        if pattern not in visited:
            visited.add(pattern)
            num_bananas[pattern] += sequence[i + 4] % 10

print(f'Part 1: {secret_sum}')
print(f'Part 2: {max(num_bananas.values())}')
