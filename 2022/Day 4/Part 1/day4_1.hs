import Data.List.Split (wordsBy)
import Data.Char (isDigit)

readInput :: String -> [[Int]]
readInput = map (map read . wordsBy (not . isDigit)) . lines

fullyContains :: [Int] -> Bool
fullyContains [a, b, c, d] = a <= c && b >= d || a >= c && b <= d

solve :: [[Int]] -> Int
solve = length . filter fullyContains

main :: IO ()
main = interact (show . solve . readInput)
