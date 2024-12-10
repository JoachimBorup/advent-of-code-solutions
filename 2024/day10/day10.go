package main

import (
	"bufio"
	"fmt"
	"os"
)

type Point struct{ y, x int }

func main() {
	grid, trailheads := readInput()
	directions := []Point{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}

	outOfBounds := func(p Point) bool {
		return p.y < 0 || p.x < 0 || p.y >= len(grid) || p.x >= len(grid[p.y])
	}

	part1, part2 := 0, 0
	for trailhead := range trailheads {
		stack := []Point{trailhead}
		visited := make(map[Point]bool)
		reachableNines := make(map[Point]bool)
		trailheadRating := 0

		for len(stack) > 0 {
			current := stack[len(stack)-1]
			stack = stack[:len(stack)-1]
			visited[current] = true

			if grid[current.y][current.x] == '9' {
				reachableNines[current] = true
				trailheadRating++
				continue
			}

			for _, dir := range directions {
				next := Point{current.y + dir.y, current.x + dir.x}
				if outOfBounds(next) {
					continue
				}
				if int(grid[next.y][next.x]) == int(grid[current.y][current.x])+1 {
					stack = append(stack, next)
				}
			}
		}

		part1 += len(reachableNines)
		part2 += trailheadRating
	}

	fmt.Printf("Part 1: %d\n", part1)
	fmt.Printf("Part 2: %d\n", part2)
}

func readInput() ([]string, map[Point]bool) {
	scanner := bufio.NewScanner(os.Stdin)

	var grid []string
	for scanner.Scan() {
		grid = append(grid, scanner.Text())
	}

	trailheads := make(map[Point]bool)
	for y, row := range grid {
		for x, cell := range row {
			if cell == '0' {
				trailheads[Point{y, x}] = true
			}
		}
	}

	return grid, trailheads
}
