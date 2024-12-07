package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type Equation struct {
	TestValue int
	Numbers   []int
}

func main() {
	equations := readInput()

	add := func(x, y int) int { return x + y }
	multiply := func(x, y int) int { return x * y }
	concatenate := func(x, y int) int {
		combined, _ := strconv.Atoi(fmt.Sprintf("%d%d", x, y))
		return combined
	}

	part1, part2 := 0, 0
	for _, eq := range equations {
		if canBeMadeTrue(eq, []func(int, int) int{add, multiply}) {
			part1 += eq.TestValue
		}
		if canBeMadeTrue(eq, []func(int, int) int{add, multiply, concatenate}) {
			part2 += eq.TestValue
		}
	}

	fmt.Printf("Part 1: %d\n", part1)
	fmt.Printf("Part 2: %d\n", part2)
}

func canBeMadeTrue(eq Equation, operations []func(int, int) int) bool {
	var solve func(result int, numbers []int) bool

	solve = func(result int, numbers []int) bool {
		if len(numbers) == 0 {
			return result == eq.TestValue
		}
		if result > eq.TestValue {
			return false
		}
		for _, operation := range operations {
			if solve(operation(result, numbers[0]), numbers[1:]) {
				return true
			}
		}
		return false
	}

	return solve(eq.Numbers[0], eq.Numbers[1:])
}

func readInput() []Equation {
	scanner := bufio.NewScanner(os.Stdin)
	var equations []Equation

	for scanner.Scan() {
		line := strings.TrimSpace(scanner.Text())
		parts := strings.Split(line, ": ")
		testValue, _ := strconv.Atoi(parts[0])
		numberStrings := strings.Split(parts[1], " ")
		numbers := make([]int, len(numberStrings))
		for i, numStr := range numberStrings {
			numbers[i], _ = strconv.Atoi(numStr)
		}
		equations = append(equations, Equation{TestValue: testValue, Numbers: numbers})
	}

	return equations
}
