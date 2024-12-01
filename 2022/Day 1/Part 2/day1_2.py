elves = [0, 0, 0]

for calories in open(0).read().split("\n\n"):
    curr = sum(map(int, calories.split()))
    for i in range(3):
        if curr > elves[i]:
            elves[i], curr = curr, elves[i]

print(sum(elves))