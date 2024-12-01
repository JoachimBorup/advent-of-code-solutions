#include <iostream>
#include <vector>

using namespace std;

int main() {
    int position = 0, depth = 0, aim = 0;
    
    string command;
    int X;
    while (cin >> command >> X) {
        if (command == "forward") {
            position += X;
            depth += aim * X;
        } else {
            aim += command == "down" ? X : -X;
        }
    }
    
    cout << position * depth;

    return 0;
}
