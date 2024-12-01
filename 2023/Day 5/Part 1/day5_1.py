import re
from collections import defaultdict
from dataclasses import dataclass
from sys import stdin


@dataclass
class Almanac:
    categories: dict[str, list[tuple[int, int]]]

    def __init__(self):
        self.categories = defaultdict(list)

    def add_range(self, category: str, start: int, end: int, length: int):
        ranges = self.categories[category]
        overlapping_ranges = set()
        new_ranges = []

        for i, (prev_start, length) in enumerate(ranges):
            prev_dest = prev_start + length
            if prev_start <= start <= prev_dest and prev_start <= end <= prev_dest:
                new_ranges.append((prev_start, start - 1))
                new_ranges.append((end + 1, prev_dest))
                new_ranges.append((start, end))
                overlapping_ranges.add(i)
            elif prev_start <= start <= prev_dest:
                new_ranges.append((prev_start, start - 1))
                new_ranges.append((start, prev_dest))
                overlapping_ranges.add(i)
            elif prev_start <= end <= prev_dest:
                new_ranges.append((end, prev_dest))
                new_ranges.append((prev_start, end))
                overlapping_ranges.add(i)

        for i in sorted(overlapping_ranges, reverse=True):
            del ranges[i]
        if len(overlapping_ranges) == 0:
            new_ranges.append((start, end))
        for new_range in new_ranges:
            ranges.append(new_range)

    def get_location(self, seed):
        category = 'seed'

        while category in self.categories:
            for start, length in self.categories[category]:
                if start <= seed <= start + length:
                    seed = seed - start
                    break
            else:
                return category, seed
            category = chr(ord(category) + 1)


seeds, *mappings = stdin.read().strip().split('\n\n')
seeds = [int(seed) for seed in seeds.split()[1::]]
almanac = Almanac()

for mapping in mappings:
    name, *ranges = mapping.split('\n')
    source, destination = re.split(r'[- ]', name)[0::2]
    ranges = [map(int, range_mapping.split()) for range_mapping in ranges]

    for dest_start, src_start, length in ranges:
        almanac.add_range(source, src_start, dest_start, length)
