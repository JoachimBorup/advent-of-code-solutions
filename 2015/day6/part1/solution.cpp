#include <cstring>
#include <iostream>
#include <vector>

using namespace std;

int main() {
    vector<vector<bool>> grid (1000);
    int litLights = 0;

    for (int i = 0; i < 1000; i++) {
        grid[i] = vector<bool> (1000);
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
                    if (!grid[i][j]) {
                        grid[i][j] = true;
                        litLights++;
                    }
                } else if (strcmp(instruction, "off") == 0) {
                    if (grid[i][j]) {
                        grid[i][j] = false;
                        litLights--;
                    }
                } else {
                    if (grid[i][j]) {
                        grid[i][j] = false;
                        litLights--;
                    } else {
                        grid[i][j] = true;
                        litLights++;
                    }
                }
            }
        }
    }

    cout << litLights;

    return 0;
}
