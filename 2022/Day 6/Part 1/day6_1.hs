import qualified Data.Set as Set

solve :: String -> Int
solve signal =
    if (length $ Set.fromList $ take 4 signal) == 4
    then 4
    else 1 + solve (tail signal)

main :: IO ()
main = interact (show . solve)
