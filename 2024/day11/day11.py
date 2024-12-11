from collections import Counter, defaultdict

stones = Counter(map(int, input().split()))

def blink(iterations, stones):
    for _ in range(iterations):
        new_stones = defaultdict(int)
        for stone, count in stones.items():
            if stone == 0:
                new_stones[1] += count
            elif len(str(stone)) % 2 == 0:
                divisor = 10 ** (len(str(stone)) // 2)
                left, right = divmod(stone, divisor)
                new_stones[left] += count
                new_stones[right] += count
            else:
                new_stones[stone * 2024] += count
        stones = new_stones

    return sum(stones.values())

print(f'Part 1: {blink(25, stones)}')
print(f'Part 2: {blink(75, stones)}')
