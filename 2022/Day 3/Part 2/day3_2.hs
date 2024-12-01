import Data.Char (ord)
import Data.List.Split (chunksOf)
import Data.Set (findMin, fromList, intersection)

uniqueItem :: [String] -> Char
uniqueItem = findMin . foldr1 intersection . map fromList

toPriority :: Char -> Int
toPriority ch =
    if ch < 'a'
    then ord ch - ord 'A' + 27
    else ord ch - ord 'a' + 1

solve :: [String] -> Int
solve = sum . map toPriority . map uniqueItem . chunksOf 3

main :: IO ()
main = interact (show . solve . lines)
