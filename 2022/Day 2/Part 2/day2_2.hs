solve :: [String] -> Int
solve [] = 0
solve (x:xs) =
    case x of
        "A X" -> 0 + 3 + solve xs
        "A Y" -> 3 + 1 + solve xs
        "A Z" -> 6 + 2 + solve xs
        "B X" -> 0 + 1 + solve xs
        "B Y" -> 3 + 2 + solve xs
        "B Z" -> 6 + 3 + solve xs
        "C X" -> 0 + 2 + solve xs
        "C Y" -> 3 + 3 + solve xs
        "C Z" -> 6 + 1 + solve xs

main :: IO ()
main = interact (show . solve . lines)
