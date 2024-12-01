#include <iostream>
#include <set>

using namespace std;

set<char> allVowels {'a', 'e', 'i', 'o', 'u'};
set<string> forbiddenStrings {"ab", "cd", "pq", "xy"};

bool containsThreeVowels(string input) {
    int vowels = 0;

    for (char ch : input) {
        if (allVowels.count(ch)) {
            vowels++;
        }
    }

    return vowels >= 3;
}

bool containsDoubleLetter(string input) {
    for (int i = 1; i < input.length(); i++) {
        if (input[i - 1] == input[i]) {
            return true;
        }
    }

    return false;
}

bool containsForbiddenString(string input) {
    for (string forbiddenString : forbiddenStrings) {
        if (input.find(forbiddenString) != -1) {
            return true;
        }
    }

    return false;
}

bool isNiceString(string input) {
    return containsThreeVowels(input) && containsDoubleLetter(input) && !containsForbiddenString(input);
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
