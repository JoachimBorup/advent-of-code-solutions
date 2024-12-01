import Data.Char (isDigit)
import Data.List.Split (chunksOf, wordsBy)
import qualified Data.Set as Set

pairWise :: [a] -> [(a, a)]
pairWise [] = []
pairWise [x] = []
pairWise (x:y:xs) = (x, y) : pairWise (y:xs)

range :: (Ord a, Enum a) => a -> a -> [a]
range x y = [min x y .. max x y]

coordinatesBetween :: ((Int, Int), (Int, Int)) -> Set.Set (Int, Int)
coordinatesBetween ((x1, y1), (x2, y2)) = Set.fromList [(x, y) | x <- range x1 x2, y <- range y1 y2]

parseLine :: String -> [(Int, Int)]
parseLine = map (\[x, y] -> (x, y)) . chunksOf 2 . map (read) . wordsBy (not . isDigit)

parseCave :: String -> Set.Set (Int, Int)
parseCave = Set.unions . concatMap (map coordinatesBetween . pairWise . parseLine) . lines

sandStartPoint :: (Int, Int)
sandStartPoint = (500, 0)

sandIsBlocked :: Int -> Set.Set (Int, Int) -> Bool
sandIsBlocked max cave = (nextStep sandStartPoint max cave) == Nothing

bottomOfCave :: Set.Set (Int, Int) -> Int
bottomOfCave = maximum . map snd . Set.toList

nextStep :: (Int, Int) -> Int -> Set.Set (Int, Int) -> Maybe (Int, Int)
nextStep (x, y) max cave
    | y + 1 >= max = Nothing
    | not $ Set.member (x, y + 1) cave = Just (x, y + 1)
    | not $ Set.member (x - 1, y + 1) cave = Just (x - 1, y + 1)
    | not $ Set.member (x + 1, y + 1) cave = Just (x + 1, y + 1)
    | otherwise = Nothing

dropGrainOfSand :: (Int, Int) -> Int -> Set.Set (Int, Int) -> Set.Set (Int, Int)
dropGrainOfSand (x, y) max cave =
    case nextStep (x, y) max cave of
        Just (x', y') -> dropGrainOfSand (x', y') max cave
        Nothing -> Set.insert (x, y) cave

fillCaveWithSand :: Int -> Set.Set (Int, Int) -> Set.Set (Int, Int)
fillCaveWithSand max cave =
    case dropGrainOfSand sandStartPoint max cave of
        cave' | sandIsBlocked max cave' -> Set.insert sandStartPoint cave'
        cave' -> fillCaveWithSand max cave'

howMuchSandToFill :: Set.Set (Int, Int) -> Int
howMuchSandToFill cave = Set.size (fillCaveWithSand (2 + bottomOfCave cave) cave) - Set.size cave

main :: IO ()
main = interact (show . howMuchSandToFill . parseCave)
