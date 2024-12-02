import sys

def is_safe(report: list[int]) -> bool:
    if all(1 <= y - x <= 3 for x, y in zip(report, report[1:])):
        return True
    if all(1 <= x - y <= 3 for x, y in zip(report, report[1:])):
        return True
    return False

def is_tolerably_safe(report: list[int]) -> bool:
    return any(is_safe(report[:i] + report[i+1:]) for i in range(len(report)))

reports = [list(map(int, line.split())) for line in sys.stdin]
print(f'Part 1: {sum(is_safe(report) for report in reports)}')
print(f'Part 2: {sum(is_tolerably_safe(report) for report in reports)}')
