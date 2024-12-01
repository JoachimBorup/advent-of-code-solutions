#include <iostream>
#include <sstream>

using namespace std;

int main() {
    int totalRibbonFeet = 0;

    int l, w, h;
    while (scanf("%ix%ix%i", &l, &w, &h) != EOF) {
        int perimeter = min(min(l + w, w + h), h + l) * 2;
        int ribbon = l * w * h;

        totalRibbonFeet += perimeter + ribbon;
    }

    cout << totalRibbonFeet;

    return 0;
}
