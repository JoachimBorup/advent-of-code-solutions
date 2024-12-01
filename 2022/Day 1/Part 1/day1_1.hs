import Data.List.Split (splitOn)

readInput :: String -> [Int]
readInput = map (sum . map read . lines) . splitOn "\n\n"

solve :: [Int] -> Int
solve = maximum

main :: IO ()
main = interact (show . solve . readInput)
