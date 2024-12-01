import qualified Data.Map as Map
import Data.Char ( digitToInt )
import Data.Maybe ( fromJust )

enumerate :: [a] -> [(Int, a)]
enumerate = zip [0 ..]

charsToTrees :: (Int, String) -> Map.Map (Int, Int) Int -> Map.Map (Int, Int) Int
charsToTrees (y, line) grid =
    foldr (\(x, height) acc -> Map.insert (x, y) (digitToInt height) acc) grid (enumerate line)

readInput :: String -> Map.Map (Int, Int) Int
readInput = foldr charsToTrees Map.empty . enumerate . lines

isVisible :: ((Int, Int), Int) -> Map.Map (Int, Int) Int -> Bool
isVisible ((x, y), height) grid = res where
    (gridWidth, gridHeight) = maximum $ map fst $ Map.toList grid

    aroundEdge        = x == 0 || x == gridWidth || y == 0 || y == gridHeight
    visibleFromLeft   = not $ any (\x' -> height <= fromJust (Map.lookup (x', y) grid)) [0 .. x - 1]
    visibleFromRight  = not $ any (\x' -> height <= fromJust (Map.lookup (x', y) grid)) [x + 1 .. gridWidth]
    visibleFromTop    = not $ any (\y' -> height <= fromJust (Map.lookup (x, y') grid)) [0 .. y - 1]
    visibleFromBottom = not $ any (\y' -> height <= fromJust (Map.lookup (x, y') grid)) [y + 1 .. gridHeight]

    res = aroundEdge || visibleFromLeft || visibleFromRight || visibleFromTop || visibleFromBottom

solve :: Map.Map (Int, Int) Int -> Int
solve grid = length $ filter (\tree -> isVisible tree grid) $ Map.toList grid

main :: IO ()
main = interact (show . solve . readInput)
