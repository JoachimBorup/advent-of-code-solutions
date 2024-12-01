from collections import Counter
from dataclasses import dataclass
from sys import stdin


@dataclass
class Hand:
    hand: str
    bid: int
    type: int
    joker_rule_applies: bool

    def __init__(self, line, joker_rule_applies=False):
        hand, bid = line.split()
        self.hand = hand
        self.bid = int(bid)
        self.type = self.hand_type(hand)
        self.joker_rule_applies = joker_rule_applies

        if self.joker_rule_applies and hand != 'JJJJJ':
            counter = Counter(hand.replace('J', '')).most_common()
            self.type = self.hand_type(hand.replace('J', counter[0][0]))

    def __lt__(self, other):
        if self.type == other.type:
            for a, b in zip(self.hand, other.hand):
                if a == b: continue
                return self.card_value(a) < self.card_value(b)
        return self.type < other.type

    @staticmethod
    def hand_type(hand):
        match [count for _, count in Counter(hand).most_common()]:
            case [5]: return 7           # Five of a kind
            case [4, 1]: return 6        # Four of a kind
            case [3, 2]: return 5        # Full house
            case [3, 1, 1]: return 4     # Three of a kind
            case [2, 2, 1]: return 3     # Two pair
            case [2, 1, 1, 1]: return 2  # One pair
            case _: return 1             # High card

    def card_value(self, card):
        if self.joker_rule_applies:
            return "J23456789TQKA".index(card)
        else:
            return "23456789TJQKA".index(card)


lines = [line.strip() for line in stdin.readlines()]

# Part 1
hands = sorted(map(Hand, lines))
print(sum(hand.bid * i for i, hand in enumerate(hands, start=1)))

# Part 2
hands = sorted(Hand(line, True) for line in lines)
print(sum(hand.bid * i for i, hand in enumerate(hands, start=1)))
