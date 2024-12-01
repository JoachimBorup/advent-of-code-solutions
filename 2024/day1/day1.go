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
	scanner := bufio.NewScanner(os.Stdin)
	var lefts, rights []int

	for scanner.Scan() {
		line := scanner.Text()
		parts := strings.Fields(line)
		a, _ := strconv.Atoi(parts[0])
		b, _ := strconv.Atoi(parts[1])
		lefts = append(lefts, a)
		rights = append(rights, b)
	}

	// Part 1: Calculate distances
	sort.Ints(lefts)
	sort.Ints(rights)
	distances := 0
	for i := range lefts {
		diff := lefts[i] - rights[i]
		distances += max(diff, -diff)
	}

	fmt.Printf("Part 1: %d\n", distances)

	// Part 2: Calculate similarities
	rightCounts := make(map[int]int)
	for _, r := range rights {
		rightCounts[r]++
	}

	similarities := 0
	for _, l := range lefts {
		similarities += l * rightCounts[l]
	}

	fmt.Printf("Part 2: %d\n", similarities)
}
