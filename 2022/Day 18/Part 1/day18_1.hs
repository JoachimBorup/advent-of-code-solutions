import Data.Char (isDigit)
import Data.List.Split (wordsBy)
import qualified Data.Set as Set

type Cube = (Int, Int, Int)

readInput :: String -> Set.Set Cube
readInput = Set.fromList . map ((\[x, y, z] -> (x, y, z)) . map read . wordsBy (not . isDigit)) . lines

adjacentCubes :: Cube -> [Cube]
adjacentCubes (x, y, z) = [(x - 1, y, z), (x + 1, y, z), (x, y - 1, z), (x, y + 1, z), (x, y, z - 1), (x, y, z + 1)]

surfaceArea :: Cube -> Set.Set Cube -> Int
surfaceArea cube cubes = length $ filter (\cube' -> not $ Set.member cube' cubes) $ adjacentCubes cube

solve :: Set.Set Cube -> Int
solve cubes = foldl (\acc cube -> acc + surfaceArea cube cubes) 0 cubes

main :: IO ()
main = interact (show . solve . readInput)
