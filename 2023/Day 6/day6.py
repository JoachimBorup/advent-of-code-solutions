from functools import reduce
from sys import stdin


def ms_held_beats(ms, time, distance):
    return ms * (time - ms) > distance


def ways_to_win_race(time, distance):
    return reduce(lambda acc, ms: acc + ms_held_beats(ms, time, distance), range(1, time), 0)


def part_1():
    times, distances = [map(int, line.split()[1::]) for line in lines]
    return reduce(lambda acc, race: acc * ways_to_win_race(*race), zip(times, distances), 1)


def part_2():
    time, distance = [int(''.join(line.split()[1::])) for line in lines]
    return ways_to_win_race(time, distance)


lines = [line.strip() for line in stdin.readlines()]
print(part_1())
print(part_2())
