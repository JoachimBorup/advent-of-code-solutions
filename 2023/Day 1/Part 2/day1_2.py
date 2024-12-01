import re
from sys import stdin

digits = {'one': 1, 'two': 2, 'three': 3, 'four': 4, 'five': 5, 'six': 6, 'seven': 7, 'eight': 8, 'nine': 9}
as_digit = lambda x: digits[x] if x in digits else int(x)
sum = 0

for line in stdin:
    matches = re.findall(fr'(?=(\d|{"|".join(digits.keys())}))', line)
    sum += int(f'{as_digit(matches[0])}{as_digit(matches[-1])}')

print(sum)
