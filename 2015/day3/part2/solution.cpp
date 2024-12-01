#include <iostream>
#include <set>

using namespace std;

int main() {
    string instructions;
    cin >> instructions;
    
    set<pair<int, int>> housesVisited;
    housesVisited.insert(make_pair(0, 0));
    int santaX = 0, santaY = 0;
    int robotX = 0, robotY = 0;
    bool isSantasTurn = true;

    for (char instruction : instructions) {
        if (isSantasTurn) {
            if (instruction == '^') santaY++;
            else if (instruction == '>') santaX++;
            else if (instruction == 'v') santaY--;
            else santaX--;
        } else {
            if (instruction == '^') robotY++;
            else if (instruction == '>') robotX++;
            else if (instruction == 'v') robotY--;
            else robotX--;
        }

        housesVisited.insert(make_pair(santaX, santaY));
        housesVisited.insert(make_pair(robotX, robotY));
        isSantasTurn = !isSantasTurn;
    }

    cout << housesVisited.size();

    return 0;
}
