#include <iostream>
#include <vector>

using namespace std;

int flash(vector<vector<int>>& grid, vector<vector<bool>>& flashed, int y, int x) {
    if (y < 0 || y >= grid.size() || x < 0 || x >= grid[y].size() || flashed[y][x]) {
        return 0;
    }

    int flashes = 0;
    grid[y][x]++;

    if (grid[y][x] > 9) {
        flashed[y][x] = true;
        grid[y][x] = 0;
        flashes++;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                flashes += flash(grid, flashed, y + i, x + j);
            }
        }
    }

    return flashes;
}

int flashGrid(vector<vector<int>>& grid) {
    vector<vector<bool>> flashed;
    for (int i = 0; i < grid.size(); i++) {
        flashed.push_back(vector<bool> (grid[i].size()));
    }

    int flashes = 0;

    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid[i].size(); j++) {
            if (grid[i][j] > 9) {
                flashes += flash(grid, flashed, i, j);
            }
        }
    }

    return flashes;
}

void incrementGrid(vector<vector<int>>& grid) {
    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid[i].size(); j++) {
            grid[i][j]++;
        }
    }
}

void parseInput(vector<vector<int>>& grid) {
    string input;
    while (cin >> input) {
        grid.push_back(vector<int> (0));

        for (char ch: input) {
            grid.back().push_back(ch - '0');
        }
    }
}

int main() {
    vector<vector<int>> grid;
    parseInput(grid);

    int flashes = 0;

    for (int i = 0; i < 100; i++) {
        incrementGrid(grid);
        flashes += flashGrid(grid);
    }

    cout << flashes;

    return 0;
}
