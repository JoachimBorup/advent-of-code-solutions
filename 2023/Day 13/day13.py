from sys import stdin

def sum_reflections(patterns: list[list[str]], smudges: int):
    def find_reflection(pattern: list[str]):
        def is_reflection(i: int):
            return sum(difference(a, b) for a, b in zip(pattern[i::-1], pattern[i + 1:])) == smudges
        def difference(a: str, b: str):
            return len(a) - sum(l == r for l, r in zip(a, b))

        for pattern, multiplier in [(pattern, 100), (list(zip(*pattern)), 1)]:
            for i in range(len(pattern) - 1):
                if is_reflection(i):
                    return multiplier * (i + 1)

        raise ValueError('No reflection found')

    return sum(find_reflection(pattern) for pattern in patterns)

patterns = [pattern.split() for pattern in stdin.read().strip().split('\n\n')]
print('Part 1:', sum_reflections(patterns, smudges=0))
print('Part 2:', sum_reflections(patterns, smudges=1))
