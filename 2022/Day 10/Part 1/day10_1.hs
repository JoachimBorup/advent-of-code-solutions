readInput :: String -> [[String]]
readInput = map words . lines

runInstruction :: [(Int, Int)] -> [String] -> [(Int, Int)]
runInstruction ((x, cycle):xs) ["noop"] = (x, cycle + 1) : (x, cycle) : xs
runInstruction ((x, cycle):xs) ["addx", v] = (x + read v, cycle + 2) : (x, cycle + 1) : (x, cycle) : xs

solve :: [[String]] -> Int
solve = sum . map (\(x, cycle) -> x * (cycle)) . filter (\(_, cycle) -> (cycle) `mod` 40 == 20) . foldl runInstruction [(1, 1)]

main :: IO ()
main = interact (show . solve . readInput)
