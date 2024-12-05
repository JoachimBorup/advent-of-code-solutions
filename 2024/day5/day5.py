from collections import defaultdict
import sys

ordering_rules, pages_to_produce = sys.stdin.read().strip().split('\n\n')
ordering_rules = [tuple(map(int, rule.split('|'))) for rule in ordering_rules.split('\n')]
pages_to_produce = [list(map(int, rule.split(','))) for rule in pages_to_produce.split('\n')]

rules = defaultdict(set)
for parent, child in ordering_rules:
    rules[child].add(parent)

part1 = part2 = 0
for pages in pages_to_produce:
    ever_changed = False
    changed = True

    while changed:
        changed = False
        for i in range(len(pages)):
            for j in range(i+1, len(pages)):
                if pages[j] in rules[pages[i]]:
                    ever_changed = changed = True
                    pages[i], pages[j] = pages[j], pages[i]

    middle_page = pages[len(pages) // 2]
    if ever_changed:
        part2 += middle_page
    else:
        part1 += middle_page

print(f'Part 1: {part1}')
print(f'Part 2: {part2}')
