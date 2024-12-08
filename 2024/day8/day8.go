package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

type Point struct{ y, x int }

func add(a, b Point) Point { return Point{a.y + b.y, a.x + b.x} }
func sub(a, b Point) Point { return Point{a.y - b.y, a.x - b.x} }
func outOfBounds(p Point, grid [][]rune) bool {
	return p.y < 0 || p.x < 0 || p.y >= len(grid) || p.x >= len(grid[p.y])
}

func main() {
	grid, antennas := readInput()
	antinodes := make(map[Point]bool)
	extendedAntinodes := make(map[Point]bool)

	for _, nodes := range antennas {
		for i := 0; i < len(nodes); i++ {
			for j := i + 1; j < len(nodes); j++ {
				a, b := nodes[i], nodes[j]
				diff := sub(a, b)

				// Part 1: Check both sides of the pair
				for _, antinode := range []Point{add(a, diff), sub(b, diff)} {
					if !outOfBounds(antinode, grid) {
						antinodes[antinode] = true
					}
				}

				// Part 2: Extend in both directions
				for _, extend := range []func(Point, Point) Point{add, sub} {
					// Antennas must also be antinodes
					x := a
					for !outOfBounds(x, grid) {
						extendedAntinodes[x] = true
						x = extend(x, diff)
					}
				}
			}
		}
	}

	fmt.Printf("Part 1: %d\n", len(antinodes))
	fmt.Printf("Part 2: %d\n", len(extendedAntinodes))
}

func readInput() ([][]rune, map[rune][]Point) {
	scanner := bufio.NewScanner(os.Stdin)

	var grid [][]rune
	for scanner.Scan() {
		grid = append(grid, []rune(strings.TrimSpace(scanner.Text())))
	}

	antennas := make(map[rune][]Point)
	for i, row := range grid {
		for j, node := range row {
			if node != '.' {
				antennas[node] = append(antennas[node], Point{i, j})
			}
		}
	}

	return grid, antennas
}
