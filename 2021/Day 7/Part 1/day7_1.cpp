#include <iostream>
#include <sstream>
#include <vector>

using namespace std;

void parseInput(vector<int>& positions, int& minPos, int& maxPos) {
    string input;
    cin >> input;
    stringstream ss(input);

    for (int pos; ss >> pos;) {
        positions.push_back(pos);
        minPos = min(minPos, pos);
        maxPos = max(maxPos, pos);

        if (ss.peek() == ','){
            ss.ignore();
        }
    }
}

int main() {
    vector<int> positions;
    int minPos, maxPos;
    parseInput(positions, minPos, maxPos);

    int minFuelNeeded = INT_MAX;

    for (int i = minPos; i <= maxPos; i++) {
        int fuelNeeded = 0;

        for (int pos : positions) {
            fuelNeeded += abs(pos - i);
        }

        minFuelNeeded = min(minFuelNeeded, fuelNeeded);
    }

    cout << minFuelNeeded;

    return 0;
}
