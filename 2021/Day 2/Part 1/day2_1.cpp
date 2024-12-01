#include <iostream>
#include <vector>

using namespace std;

int main() {
    int position = 0, depth = 0;
    
    string command;
    int X;
    while (cin >> command >> X) {
        if (command == "forward") {
            position += X;
        } else {
            depth += command == "down" ? X : -X;
        }
    }
    
    cout << position * depth;

    return 0;
}
