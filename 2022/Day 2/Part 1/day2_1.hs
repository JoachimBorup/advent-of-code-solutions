solve :: [String] -> Int
solve [] = 0
solve (x:xs) =
    case x of
        "A X" -> 3 + 1 + solve xs
        "A Y" -> 6 + 2 + solve xs
        "A Z" -> 0 + 3 + solve xs
        "B X" -> 0 + 1 + solve xs
        "B Y" -> 3 + 2 + solve xs
        "B Z" -> 6 + 3 + solve xs
        "C X" -> 6 + 1 + solve xs
        "C Y" -> 0 + 2 + solve xs
        "C Z" -> 3 + 3 + solve xs

main :: IO ()
main = interact (show . solve . lines)
