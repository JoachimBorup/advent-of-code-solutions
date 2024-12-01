import re
from sys import stdin

lines = [[re.findall(r'\d+', l) for l in re.split(r'[:|]', line)] for line in stdin]
copies = [1] * len(lines)
scratchcards = 0

for i, (_, winning_numbers, my_numbers) in enumerate(lines):
    matches = len(set(my_numbers) & set(winning_numbers))
    scratchcards += 1 + copies[i] * matches
    for j in range(i + 1, i + matches + 1):
        copies[j] += copies[i]

print(scratchcards)
