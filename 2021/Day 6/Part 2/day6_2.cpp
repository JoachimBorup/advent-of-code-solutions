#include <algorithm>
#include <iostream>
#include <numeric>
#include <sstream>
#include <vector>

using namespace std;

const int DAYS = 256;

void parseInput(vector<long long>& fish) {
    string input;
    cin >> input;
    stringstream ss(input);

    for (int pos; ss >> pos;) {
        fish[pos]++;

        if (ss.peek() == ','){
            ss.ignore();
        }
    }
}

int main() {
    vector<long long> fish (9);
    parseInput(fish);

    for (int i = 0; i < DAYS; i++) {
        rotate(fish.begin(), fish.begin() + 1, fish.end());
        fish[6] += fish[8];
    }

    cout << accumulate(fish.begin(), fish.end(), 0LL);

    return 0;
}
