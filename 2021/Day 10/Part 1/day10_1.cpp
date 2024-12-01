#include <iostream>
#include <stack>
#include <unordered_map>

using namespace std;

const unordered_map<char, char> pairs = { {')', '('}, {']', '['}, {'}', '{'}, {'>', '<'} };
const unordered_map<char, int> scores = { {')', 3}, {']', 57}, {'}', 1197}, {'>', 25137} };

int getErrorScore(string& line) {
    stack<char> stack;

    for (char ch : line) {
        if (ch == '(' || ch == '[' || ch == '{' || ch == '<') {
            stack.push(ch);
        } else if (stack.top() == pairs.at(ch)) {
            stack.pop();
        } else {
            return scores.at(ch);
        }
    }

    return 0;
}

int main() {
    int sum = 0;

    string line;
    while (cin >> line) {
        sum += getErrorScore(line);
    }

    cout << sum;

    return 0;
}
