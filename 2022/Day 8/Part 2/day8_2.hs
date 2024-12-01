import qualified Data.Map as Map
import Data.Char ( digitToInt )
import Data.Maybe ( fromJust )

import Debug.Trace ( trace )
debug = flip trace

enumerate :: [a] -> [(Int, a)]
enumerate = zip [0 ..]

charsToTrees :: (Int, String) -> Map.Map (Int, Int) Int -> Map.Map (Int, Int) Int
charsToTrees (y, line) grid =
    foldr (\(x, height) acc -> Map.insert (x, y) (digitToInt height) acc) grid (enumerate line)

readInput :: String -> Map.Map (Int, Int) Int
readInput = foldr charsToTrees Map.empty . enumerate . lines

scenicScore :: ((Int, Int), Int) -> Map.Map (Int, Int) Int -> Int
scenicScore ((x, y), height) grid = res where
    (gridWidth, gridHeight) = maximum $ map fst $ Map.toList grid

    treesToTheLeft   = (\l -> if l == (length [0 .. x - 1])          then l else l + 1) $ length $ takeWhile (\x' -> height > fromJust (Map.lookup (x', y) grid)) (reverse [0 .. x - 1])
    treesToTheRight  = (\l -> if l == (length [x + 1 .. gridWidth])  then l else l + 1) $ length $ takeWhile (\x' -> height > fromJust (Map.lookup (x', y) grid)) [x + 1 .. gridWidth]
    treesToTheTop    = (\l -> if l == (length [0 .. y - 1])          then l else l + 1) $ length $ takeWhile (\y' -> height > fromJust (Map.lookup (x, y') grid)) (reverse [0 .. y - 1])
    treesToTheBottom = (\l -> if l == (length [y + 1 .. gridHeight]) then l else l + 1) $ length $ takeWhile (\y' -> height > fromJust (Map.lookup (x, y') grid)) [y + 1 .. gridHeight]

    res = treesToTheLeft * treesToTheRight * treesToTheTop * treesToTheBottom

solve :: Map.Map (Int, Int) Int -> Int
solve grid = maximum $ map (\tree -> scenicScore tree grid) $ Map.toList grid

main :: IO ()
main = interact (show . solve . readInput)
