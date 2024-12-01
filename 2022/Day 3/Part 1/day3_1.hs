import Data.Char (ord)
import Data.Set (findMin, fromList, intersection)

compartments :: String -> (String, String)
compartments rucksack = splitAt (div (length rucksack) 2) rucksack

uniqueItem :: (String, String) -> Char
uniqueItem (a, b) = findMin $ intersection (fromList a) (fromList b)

toPriority :: Char -> Int
toPriority ch =
    if ch < 'a'
    then ord ch - ord 'A' + 27
    else ord ch - ord 'a' + 1

solve :: [String] -> Int
solve = sum . map toPriority . map uniqueItem . map compartments

main :: IO ()
main = interact (show . solve . lines)


