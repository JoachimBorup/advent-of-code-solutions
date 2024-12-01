from sys import stdin


def extrapolate(history):
    if any(history):
        right, left = extrapolate([v - w for v, w in zip(history, history[1:])])
        return history[-1] - right, history[0] + left
    return 0, 0


histories = [list(map(int, line.split())) for line in stdin.readlines()]
part_1, part_2 = map(sum, (zip(*map(extrapolate, histories))))

print(f"Part 1: {part_1}")
print(f"Part 2: {part_2}")
