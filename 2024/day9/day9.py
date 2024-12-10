from collections import defaultdict
import itertools
import sys

disk_map = list(map(int, sys.stdin.readline().strip()))
files, spaces = disk_map[::2], disk_map[1::2]

file_indices = {}
free_spaces = defaultdict(list)

disk = []
for id, file, index in itertools.zip_longest(range(len(files)), files, spaces, fillvalue=0):
    file_indices[id] = len(disk)
    for _ in range(file):
        disk.append(id)
    free_spaces[index].append(len(disk))
    for _ in range(index):
        disk.append('.')

right = len(files) - 1
part_1_checksum = 0
for i in range(sum(files)):
    if disk[i] == '.':
        part_1_checksum += i * right
        files[right] -= 1
        if files[right] == 0:
            right -= 1
    else:
        part_1_checksum += i * disk[i]

files = disk_map[::2]

for i in range(len(files)):
    i = len(files) - i - 1

    free_spaces = 0
    for j in range(len(disk)):
        if disk[j] == i:
            break
        if disk[j] == '.':
            free_spaces += 1
        else:
            free_spaces = 0
        
        if free_spaces < files[i]:
            continue

        for k in range(files[i]):
            disk[j - k] = i

        for k in range(j + 1, len(disk)):
            if disk[k] == i:
                disk[k] = '.'

        break

part_2_checksum = 0
for i in range(len(disk)):
    if disk[i] == '.':
        continue
    part_2_checksum += i * disk[i]


print(f'Part 1: {part_1_checksum}')
print(f'Part 2: {part_2_checksum}')
# print(f'Disk: {"".join(map(str, disk))}')
