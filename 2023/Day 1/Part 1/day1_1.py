import re
from sys import stdin

sum = 0

for line in stdin:
    line = re.sub(r'\D', '', line)
    sum += int(f'{line[0]}{line[-1]}')

print(sum)
