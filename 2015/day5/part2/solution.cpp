#include <iostream>
#include <set>

using namespace std;

bool containsPairOfTwoLetters(string input) {
    for (int i = 1; i < input.length(); i++) {
        for (int j = 1; j < input.length(); j++) {
            if (i - 1 != j && i != j && i != j - 1 &&
                input[i - 1] == input[j - 1] &&
                input[i] == input[j]) {
                return true;
            }
        }
    }

    return false;
}

bool containsSpacedDoubleLetter(string input) {
    for (int i = 2; i < input.length(); i++) {
        if (input[i - 2] == input[i]) {
            return true;
        }
    }

    return false;
}

bool isNiceString(string input) {
    return containsPairOfTwoLetters(input) && containsSpacedDoubleLetter(input);
}

int main() {
    int niceStrings = 0;

    string input;
    while (cin >> input) {
        if (isNiceString(input)) {
            niceStrings++;
        }
    }

    cout << niceStrings;

    return 0;
}
