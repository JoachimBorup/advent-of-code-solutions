import qualified Data.Set as Set

solve :: String -> Int
solve signal =
    if (length $ Set.fromList $ take 14 signal) == 14
    then 14
    else 1 + solve (tail signal)

main :: IO ()
main = interact (show . solve)
