#include <iostream>

using namespace std;

int main() {
    string instructions;
    cin >> instructions;
    
    int floor = 0;

    for (char instruction : instructions) {
        floor += instruction == '(' ? 1 : -1;
    }

    cout << floor;

    return 0;
}
