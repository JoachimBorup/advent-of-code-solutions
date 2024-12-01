#include <algorithm>
#include <iostream>
#include <stack>
#include <unordered_map>
#include <vector>

using namespace std;

const unordered_map<char, char> pairs = { {')', '('}, {']', '['}, {'}', '{'}, {'>', '<'} };
const unordered_map<char, int> scores = { {'(', 1}, {'[', 2}, {'{', 3}, {'<', 4} };

long long getAutocompleteScore(string& line) {
    long long score = 0;
    stack<char> stack;

    for (char ch : line) {
        if (ch == '(' || ch == '[' || ch == '{' || ch == '<') {
            stack.push(ch);
        } else if (stack.top() == pairs.at(ch)) {
            stack.pop();
        } else {
            return score;
        }
    }

    while (!stack.empty()) {
        score = score * 5 + scores.at(stack.top());
        stack.pop();
    }

    return score;
}

int main() {
    vector<long long> scores;

    string line;
    while (cin >> line) {
        long long score = getAutocompleteScore(line);

        if (score != 0) {
            scores.push_back(score);
        }
    }

    sort(scores.begin(), scores.end());

    cout << scores[scores.size() / 2];

    return 0;
}
