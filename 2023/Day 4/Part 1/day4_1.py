import re
from sys import stdin

points = 0

for line in stdin:
    line = re.split(r'[:|]', line)
    winning_numbers = set(re.findall(r'\d+', line[1]))
    my_numbers = set(re.findall(r'\d+', line[2]))
    matches = len(my_numbers & winning_numbers)
    if matches > 0:
        points += 2 ** (matches - 1)

print(points)
