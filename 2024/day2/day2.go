package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func isSafe(report []int) bool {
	ascending, descending := true, true

	for i := 1; i < len(report); i++ {
		diff := report[i] - report[i-1]
		if diff < 1 || diff > 3 {
			ascending = false
		}
		if -diff < 1 || -diff > 3 {
			descending = false
		}
	}

	return ascending || descending
}

func isTolerablySafe(report []int) bool {
	for i := 0; i < len(report); i++ {
		// Create a copy of the report excluding the i-th element
		modified := append([]int{}, report[:i]...)
		modified = append(modified, report[i+1:]...)
		if isSafe(modified) {
			return true
		}
	}
	return false
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)
	var reports [][]int

	for scanner.Scan() {
		line := scanner.Text()
		parts := strings.Fields(line)
		report := make([]int, len(parts))
		for i, part := range parts {
			num, _ := strconv.Atoi(part)
			report[i] = num
		}
		reports = append(reports, report)
	}

	part1, part2 := 0, 0
	for _, report := range reports {
		if isSafe(report) {
			part1++
		}
		if isTolerablySafe(report) {
			part2++
		}
	}

	fmt.Printf("Part 1: %d\n", part1)
	fmt.Printf("Part 2: %d\n", part2)
}
