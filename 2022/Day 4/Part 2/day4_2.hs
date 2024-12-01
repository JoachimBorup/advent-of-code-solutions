import Data.List.Split (wordsBy)
import Data.Char (isDigit)

readInput :: String -> [[Int]]
readInput = map (map read . wordsBy (not . isDigit)) . lines

overlaps :: [Int] -> Bool
overlaps [a, b, c, d] = a <= d && c <= b

solve :: [[Int]] -> Int
solve = length . filter overlaps

main :: IO ()
main = interact (show . solve . readInput)
