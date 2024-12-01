import Data.List ( partition ) 
import Control.Exception ( throw )

data Directory = Directory {
    dirName :: String, children :: [Directory], files :: [File]
} deriving (Show)

data File = File {
    fileName :: String, size :: Int
} deriving (Show)

newDirectory :: String -> Directory
newDirectory name = Directory name [] []

insertDirectory :: String -> (Directory, [String]) -> Directory
insertDirectory name (root, []) =
    if elem name $ map dirName $ children root
    then root
    else root { children = newDirectory name : children root }
insertDirectory name (root, dir:dirs) = res
    where
        (left, right) = partition (\x -> dirName x == dir) (children root)
        res = root { children = insertDirectory name (head left, dirs) : right }

insertFile :: String -> Int -> (Directory, [String]) -> Directory
insertFile name size (root, []) =
    if elem name $ map fileName $ files root
    then root
    else root { files = File name size : files root }
insertFile name size (root, dir:dirs) = res
    where
        (left, right) = partition (\x -> dirName x == dir) (children root)
        res = root { children = insertFile name size (head left, dirs) : right }

compute :: (Directory, [String]) -> [String] -> (Directory, [String])
compute (root, target) ["$", "cd", ".."] = (root, init target)
compute (root, target) ["$", "cd", name] = (insertDirectory name (root, target), target ++ [name])
compute (root, target) ["$", "ls"]       = (root, target)
compute (root, target) ["dir", name]     = (insertDirectory name (root, target), target)
compute (root, target) [size, name]      = (insertFile name (read size) (root, target), target)

computeSize :: Directory -> Int
computeSize dir = sum $ map size (files dir) ++ map computeSize (children dir)

getDirectories :: Directory -> [Directory]
getDirectories dir = children dir ++ concatMap getDirectories (children dir)

readInput :: String -> [[String]]
readInput = map words . tail . lines

solve :: [[String]] -> Int
solve input = res where
    rootDirectory = fst $ foldl compute (newDirectory "/", []) input
    spaceToDelete = 30_000_000 - (70_000_000 - (computeSize rootDirectory))
    res = minimum $ filter (>= spaceToDelete) $ map computeSize $ getDirectories $ rootDirectory

main :: IO ()
main = interact (show . solve . readInput)
