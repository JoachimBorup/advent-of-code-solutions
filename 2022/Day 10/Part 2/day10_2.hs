import Data.List ( intercalate )
import Data.List.Split ( chunksOf )

readInput :: String -> [[String]]
readInput = map words . lines

runInstruction :: [(Int, Int)] -> [String] -> [(Int, Int)]
runInstruction ((x, cycle):xs) ["noop"] = (x, cycle + 1) : (x, cycle) : xs
runInstruction ((x, cycle):xs) ["addx", v] = (x + read v, cycle + 2) : (x, cycle + 1) : (x, cycle) : xs

isInSprite :: Int -> Int -> Bool
isInSprite sprite cycle = sprite - 1 <= cycle && cycle <= sprite + 1

convertToCRT :: [(Int, Int)] -> String
convertToCRT [] = ""
convertToCRT ((x, cycle):xs) = (if (isInSprite x (cycle `mod` 40)) then '#' else '.') : convertToCRT xs

solve :: [[String]] -> String
solve = intercalate "\n" . map convertToCRT . filter (\z -> length z == 40) . chunksOf 40 . reverse . foldl runInstruction [(1, 0)]

main :: IO ()
main = interact (solve . readInput)
