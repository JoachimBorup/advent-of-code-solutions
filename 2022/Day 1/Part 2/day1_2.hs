import Data.List.Split (splitOn)
import Data.List (sort)

readInput :: String -> [Int]
readInput = map (sum . map read . lines) . splitOn "\n\n"

solve :: [Int] -> Int
solve = sum . take 3 . reverse . sort

main :: IO ()
main = interact (show . solve . readInput)
