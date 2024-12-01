#include <iostream>
#include <sstream>

using namespace std;

int main() {
    int totalSquareFeet = 0;

    int l, w, h;
    while (scanf("%ix%ix%i", &l, &w, &h) != EOF) {
        int side1 = l * w, side2 = w * h, side3 = h * l;
        int extra = min(min(side1, side2), side3);
        totalSquareFeet += 2 * (side1 + side2 + side3) + extra;
    }

    cout << totalSquareFeet;

    return 0;
}
