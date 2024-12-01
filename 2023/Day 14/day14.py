from sys import stdin


def tilt(platform):
    def move_rock(x, y):
        platform[y][x] = '.'
        while y > 0 and platform[y - 1][x] == '.':
            y -= 1
        platform[y][x] = 'O'

    for y, row in enumerate(platform):
        for x, rock in enumerate(row):
            if rock == 'O':
                move_rock(x, y)


def calculate_load(platform):
    return sum(len(platform) - y for y, row in enumerate(platform) for rock in row if rock == 'O')


platform = [list(line.strip()) for line in stdin.readlines()]
cycle_length = 0
memo_load = {}
memo = {}

tilt(platform)
print('Part 1:', calculate_load(platform))

for i in range(1_000_000_000):
    for _ in range(4):
        tilt(platform)
        platform = list(map(list, zip(*platform[::-1])))

    key = ''.join(''.join(row) for row in platform)
    if key in memo:
        cycle_length = i - memo[key]
        break

    memo_load[i] = calculate_load(platform)
    memo[key] = i

print('Part 2:', memo_load[999_999_999 % cycle_length])
