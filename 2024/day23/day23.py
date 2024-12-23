from collections import defaultdict
import sys

network = [line.strip().split('-') for line in sys.stdin]
graph = defaultdict(set)
for a, b in network:
    graph[a].add(b)
    graph[b].add(a)

interconnected = set()
for v in graph:
    for w in graph[v]:
        for x in graph[w]:
            if x in graph[v]:
                interconnected.add(tuple(sorted([v, w, x])))

print(f'Part 1: {sum(any(v.startswith("t") for v in s) for s in interconnected)}')

max_clique = set()
def bron_kerbosch(current, candidates, excluded):
    global max_clique
    if not candidates and not excluded:
        if len(current) > len(max_clique):
            max_clique = current.copy()
        return

    pivot = next(iter(candidates | excluded))
    for v in candidates - graph[pivot]:
        bron_kerbosch(current | {v}, candidates & graph[v], excluded & graph[v])
        candidates -= {v}
        excluded |= {v}

bron_kerbosch(set(), set(graph), set())
print(f"Part 2: {','.join(sorted(max_clique))}")
