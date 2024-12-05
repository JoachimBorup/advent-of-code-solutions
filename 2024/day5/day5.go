package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"
)

func main() {
	orderingRules, pagesToProduce := readInput()
	part1, part2 := 0, 0

	for _, pages := range pagesToProduce {
		original := append([]int(nil), pages...)
		sort.Slice(pages, func(i, j int) bool {
			return !(orderingRules[pages[i]] != nil && orderingRules[pages[i]][pages[j]])
		})

		correctlyOrdered := true
		for i := range pages {
			if pages[i] != original[i] {
				correctlyOrdered = false
				break
			}
		}

		middlePage := pages[len(pages)/2]
		if correctlyOrdered {
			part1 += middlePage
		} else {
			part2 += middlePage
		}
	}

	fmt.Printf("Part 1: %d\n", part1)
	fmt.Printf("Part 2: %d\n", part2)
}

func readInput() (map[int]map[int]bool, [][]int) {
	reader := bufio.NewReader(os.Stdin)
	input, _ := reader.ReadString(0)
	parts := strings.Split(strings.TrimSpace(input), "\n\n")

	orderingRules := make(map[int]map[int]bool)
	for _, rule := range strings.Split(parts[0], "\n") {
		parts := strings.Split(rule, "|")
		parent, _ := strconv.Atoi(parts[0])
		child, _ := strconv.Atoi(parts[1])
		if orderingRules[child] == nil {
			orderingRules[child] = make(map[int]bool)
		}
		orderingRules[child][parent] = true
	}

	var pagesToProduce [][]int
	for _, line := range strings.Split(parts[1], "\n") {
		pageParts := strings.Split(line, ",")
		pages := make([]int, len(pageParts))
		for i, p := range pageParts {
			pages[i], _ = strconv.Atoi(p)
		}
		pagesToProduce = append(pagesToProduce, pages)
	}

	return orderingRules, pagesToProduce
}
