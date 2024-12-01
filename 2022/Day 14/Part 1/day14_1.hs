import Data.Char (isDigit)
import Data.List.Split (chunksOf, wordsBy)
import qualified Data.Set as Set

sandStartPoint :: (Int, Int)
sandStartPoint = (500, 0)

pairWise :: [a] -> [(a, a)]
pairWise [] = []
pairWise [x] = []
pairWise (x:y:xs) = (x, y) : pairWise (y:xs)

range :: Int -> Int -> [Int]
range x y = [min x y .. max x y]

coordinatesBetween :: ((Int, Int), (Int, Int)) -> Set.Set (Int, Int)
coordinatesBetween ((x1, y1), (x2, y2)) = Set.fromList [(x, y) | x <- range x1 x2, y <- range y1 y2]

parseLine :: String -> [(Int, Int)]
parseLine = map (\[x, y] -> (x, y)) . chunksOf 2 . map (read) . wordsBy (not . isDigit)

parseCave :: String -> Set.Set (Int, Int)
parseCave = Set.unions . concatMap (map coordinatesBetween . pairWise . parseLine) . lines

nextStep :: (Int, Int) -> Int -> Set.Set (Int, Int) -> Maybe (Int, Int)
nextStep (x, y) max cave
    | y >= max = Just sandStartPoint
    | not $ Set.member (x, y + 1) cave = Just (x, y + 1)
    | not $ Set.member (x - 1, y + 1) cave = Just (x - 1, y + 1)
    | not $ Set.member (x + 1, y + 1) cave = Just (x + 1, y + 1)
    | otherwise = Nothing

dropGrainOfSand :: (Int, Int) -> Int -> Set.Set (Int, Int) -> Maybe (Set.Set (Int, Int))
dropGrainOfSand (x, y) max cave =
    case nextStep (x, y) max cave of
        Just tile | tile == sandStartPoint -> Nothing
        Just (x', y') -> dropGrainOfSand (x', y') max cave
        Nothing -> Just $ Set.insert (x, y) cave

fillCaveWithSand :: Int -> Set.Set (Int, Int) -> Set.Set (Int, Int)
fillCaveWithSand max cave =
    case dropGrainOfSand sandStartPoint max cave of
        Just cave' -> fillCaveWithSand max cave'
        Nothing -> cave

spaceForSand :: Set.Set (Int, Int) -> Int
spaceForSand cave = Set.size (fillCaveWithSand (maximum $ map snd $ Set.toList cave) cave) - Set.size cave

main :: IO ()
main = interact (show . spaceForSand . parseCave)
