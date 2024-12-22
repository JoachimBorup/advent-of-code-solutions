from collections import defaultdict
import sys

def evolve(n):
    n = (n * 64 ^ n) % 16777216
    n = (n // 32 ^ n) % 16777216
    n = (n * 2048 ^ n) % 16777216
    return n

secret_sum = 0
num_bananas = defaultdict(int)

for n in map(int, sys.stdin):
    seq = [n := evolve(n) for _ in range(2000)]
    secret_sum += seq[-1]

    diffs = [b % 10 - a % 10 for a, b in zip(seq, seq[1:])]
    visited = set()

    for i in range(len(seq) - 4):
        pattern = tuple(diffs[i:i + 4])
        if pattern not in visited:
            visited.add(pattern)
            num_bananas[pattern] += seq[i + 4] % 10

print(f'Part 1: {secret_sum}')
print(f'Part 2: {max(num_bananas.values())}')
