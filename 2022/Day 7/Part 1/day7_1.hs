import Data.List ( partition ) 
import Control.Exception ( throw )

import Debug.Trace -- udkommenter til debug.
debug = flip trace -- hjælpefunktion til at printe med

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
-- compute (root, [])     ["$", "cd", ".."] = (root, []) `debug` ("Target is empty")
compute (root, target) ["$", "cd", ".."] = (root, init target) `debug` ("cd .. with target " ++ show target)
compute (root, target) ["$", "cd", name] = (insertDirectory name (root, target), target ++ [name]) `debug` ("cd " ++ name ++ " with target " ++ show target)
compute (root, target) ["$", "ls"]       = (root, target) `debug` ("ls " ++ show target)
compute (root, target) ["dir", name]     = (insertDirectory name (root, target), target) `debug` ("dir " ++ name ++ " with target " ++ show target)
compute (root, target) [size, name]      = (insertFile name (read size) (root, target), target) `debug` ("File " ++ name ++ " with size " ++ size ++ " and target " ++ show target)

computeSize :: Directory -> Int
computeSize dir = sum $ map size (files dir) ++ map computeSize (children dir)

getDirectories :: Directory -> [Directory]
getDirectories dir = children dir ++ concatMap getDirectories (children dir)

readInput :: String -> [[String]]
readInput = map words . tail . lines

solve :: [[String]] -> Int
solve = sum . filter (<= 100_000) . map computeSize . getDirectories . fst . foldl compute (newDirectory "/", [])

main :: IO ()
main = interact (show . solve . readInput)
