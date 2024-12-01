#include <iostream>
#include <set>

using namespace std;

int main() {
    string instructions;
    cin >> instructions;
    
    set<pair<int, int>> housesVisited;
    housesVisited.insert(make_pair(0, 0));
    int x = 0, y = 0;

    for (char instruction : instructions) {
        if (instruction == '^') y++;
        else if (instruction == '>') x++;
        else if (instruction == 'v') y--;
        else x--;

        housesVisited.insert(make_pair(x, y));
    }

    cout << housesVisited.size();

    return 0;
}
