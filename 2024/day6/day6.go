package main

import (
	"bufio"
	"fmt"
	"os"
)

type Position struct{ y, x int }
type DirectedPosition struct{ y, x, direction int }

var (
	grid            [][]rune
	directions      = [][2]int{{-1, 0}, {0, 1}, {1, 0}, {0, -1}}
	visited         = make(map[Position]bool)
	visitedDirected = make(map[DirectedPosition]bool)
	loopPositions   = make(map[Position]bool)
)

func outOfBounds(i, j int) bool {
	return i < 0 || j < 0 || i >= len(grid) || j >= len(grid[0])
}

func hasLoop(y, x, direction int) bool {
	tempVisited := make(map[DirectedPosition]bool)
	for k, v := range visitedDirected {
		tempVisited[k] = v
	}

	for {
		ny, nx := y+directions[direction][0], x+directions[direction][1]
		if outOfBounds(ny, nx) {
			return false
		} else if grid[ny][nx] == '#' {
			direction = (direction + 1) % 4
		} else if tempVisited[DirectedPosition{ny, nx, direction}] {
			return true
		} else {
			y, x = ny, nx
			tempVisited[DirectedPosition{y, x, direction}] = true
		}
	}
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		line := scanner.Text()
		grid = append(grid, []rune(line))
	}

	var y, x, direction int
	for i, row := range grid {
		for j, cell := range row {
			if cell == '^' {
				y, x = i, j
				break
			}
		}
	}

	visited[Position{y, x}] = true
	visitedDirected[DirectedPosition{y, x, direction}] = true

	for {
		ny, nx := y+directions[direction][0], x+directions[direction][1]
		if outOfBounds(ny, nx) {
			break
		}

		if grid[ny][nx] == '#' {
			direction = (direction + 1) % 4
		} else {
			grid[ny][nx] = '#'
			if !visited[Position{ny, nx}] && hasLoop(y, x, direction) {
				loopPositions[Position{ny, nx}] = true
			}
			grid[ny][nx] = '.'

			y, x = ny, nx
			visited[Position{y, x}] = true
			visitedDirected[DirectedPosition{y, x, direction}] = true
		}
	}

	fmt.Printf("Part 1: %d\n", len(visited))
	fmt.Printf("Part 2: %d\n", len(loopPositions))
}
