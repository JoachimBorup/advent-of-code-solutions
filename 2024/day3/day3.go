package main

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
	"strconv"
)

func main() {
	scanner := bufio.NewScanner(os.Stdin)
	var lines []string
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	part1, part2 := 0, 0
	enabled := true

	re := regexp.MustCompile(`(mul)\((\d+),(\d+)\)|(do(?:n't)?)\(\)`)

	for _, line := range lines {
		for _, match := range re.FindAllStringSubmatch(line, -1) {
			if match[1] == "mul" {
				x, _ := strconv.Atoi(match[2])
				y, _ := strconv.Atoi(match[3])
				part1 += x * y
				if enabled {
					part2 += x * y
				}
			} else if match[4] == "do" {
				enabled = true
			} else if match[4] == "don't" {
				enabled = false
			} else {
				panic(fmt.Sprintf("Unexpected match: %v", match))
			}
		}
	}

	fmt.Printf("Part 1: %d\n", part1)
	fmt.Printf("Part 2: %d\n", part2)
}
