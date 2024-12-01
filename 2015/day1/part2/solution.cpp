#include <iostream>

using namespace std;

int main() {
    string instructions;
    cin >> instructions;
    
    int floor = 0;

    for (int i = 0; i < instructions.length(); i++) {
        floor += instructions[i] == '(' ? 1 : -1;

        if (floor == -1) {
            cout << i + 1;
            break;
        }
    }

    return 0;
}
