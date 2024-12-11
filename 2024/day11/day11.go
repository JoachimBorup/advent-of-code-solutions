package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	stones := readInput()
	fmt.Printf("Part 1: %d\n", blink(25, stones))
	fmt.Printf("Part 2: %d\n", blink(75, stones))
}

func blink(iterations int, stones map[int]int) int {
	for i := 0; i < iterations; i++ {
		newStones := make(map[int]int)
		for stone, count := range stones {
			if stone == 0 {
				newStones[1] += count
			} else if len(strconv.Itoa(stone))%2 == 0 {
				stoneStr := strconv.Itoa(stone)
				mid := len(stoneStr) / 2
				left, _ := strconv.Atoi(stoneStr[:mid])
				right, _ := strconv.Atoi(stoneStr[mid:])
				newStones[left] += count
				newStones[right] += count
			} else {
				newStones[stone*2024] += count
			}
		}
		stones = newStones
	}

	total := 0
	for _, count := range stones {
		total += count
	}
	return total
}

func readInput() map[int]int {
	scanner := bufio.NewScanner(os.Stdin)
	scanner.Scan()

	numbers := strings.Fields(scanner.Text())
	stones := make(map[int]int)
	for _, num := range numbers {
		n, _ := strconv.Atoi(num)
		stones[n]++
	}

	return stones
}
