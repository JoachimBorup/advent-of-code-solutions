package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

var directions = [][2]int{
	{-1, -1}, {-1, 0}, {-1, 1},
	{0, -1} /*{0, 0}*/, {0, 1},
	{1, -1}, {1, 0}, {1, 1},
}

var diagonals = [][2]int{
	{-1, 1},
	{1, 1},
}

const XMAS = "XMAS"

func outOfBounds(grid []string, i, j int) bool {
	return i < 0 || j < 0 || i >= len(grid) || j >= len(grid[0])
}

func searchXmas(grid []string, i, j int) int {
	matches := 0

	for _, dir := range directions {
		x, y := dir[0], dir[1]

		for k := 1; k < len(XMAS); k++ {
			nx, ny := i+k*x, j+k*y
			if outOfBounds(grid, nx, ny) || grid[nx][ny] != XMAS[k] {
				break
			}
			if k == len(XMAS)-1 {
				matches++
			}
		}
	}

	return matches
}

func searchMas(grid []string, i, j int) int {
	for _, diag := range diagonals {
		x, y := diag[0], diag[1]
		nx, ny := i+x, j+y
		px, py := i-x, j-y

		if outOfBounds(grid, nx, ny) || outOfBounds(grid, px, py) {
			return 0
		}

		if (grid[nx][ny] == XMAS[1] && grid[px][py] == XMAS[3]) ||
			(grid[nx][ny] == XMAS[3] && grid[px][py] == XMAS[1]) {
			continue
		}

		return 0
	}

	return 1
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)
	var grid []string

	for scanner.Scan() {
		grid = append(grid, strings.TrimSpace(scanner.Text()))
	}

	part1, part2 := 0, 0

	for i := 0; i < len(grid); i++ {
		for j := 0; j < len(grid[i]); j++ {
			if grid[i][j] == 'X' {
				part1 += searchXmas(grid, i, j)
			}
			if grid[i][j] == 'A' {
				part2 += searchMas(grid, i, j)
			}
		}
	}

	fmt.Printf("Part 1: %d\n", part1)
	fmt.Printf("Part 2: %d\n", part2)
}
