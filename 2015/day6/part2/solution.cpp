#include <cstring>
#include <iostream>
#include <vector>

using namespace std;

int main() {
    vector<vector<int>> grid (1000);
    int totalBrightness = 0;

    for (int i = 0; i < 1000; i++) {
        grid[i] = vector<int> (1000);
    }

    char instruction[100];
    int startX, startY, endX, endY;

    while (scanf("%s", &instruction) != EOF) {
        if (strcmp(instruction, "toggle") != 0) {
            scanf("%s", &instruction);
        }

        scanf("%i,%i through %i,%i", &startX, &startY, &endX, &endY);

        for (int i = startY; i <= endY; i++) {
            for (int j = startX; j <= endX; j++) {
                if (strcmp(instruction, "on") == 0) {
                    grid[i][j]++;
                    totalBrightness++;
                } else if (strcmp(instruction, "off") == 0) {
                    if (grid[i][j] > 0) {
                        grid[i][j]--;
                        totalBrightness--;
                    }
                } else {
                    grid[i][j] += 2;
                    totalBrightness += 2;
                }
            }
        }
    }

    cout << totalBrightness;

    return 0;
}