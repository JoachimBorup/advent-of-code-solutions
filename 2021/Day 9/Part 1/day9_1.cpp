#include <iostream>
#include <vector>

using namespace std;

void parseInput(vector<vector<int>>& grid) {
    string row;
    while (cin >> row) {
        vector<int> temp;

        for (char ch : row) {
            temp.push_back(ch - '0');
        }

        grid.push_back(temp);
    }
}

bool isLowPoint(vector<vector<int>>& grid, int y, int x) {
    int point = grid[y][x];

    if (y - 1 >= 0 && grid[y - 1][x] <= point) return false;
    if (x - 1 >= 0 && grid[y][x - 1] <= point) return false;
    if (y + 1 < grid.size() && grid[y + 1][x] <= point) return false;
    if (x + 1 < grid[y].size() && grid[y][x + 1] <= point) return false;

    return true;
}

int main() {
    vector<vector<int>> grid;
    parseInput(grid);

    int riskLevel = 0;

    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid[i].size(); j++) {
            if (isLowPoint(grid, i, j)) {
                riskLevel += grid[i][j] + 1;
            }
        }
    }

    cout << riskLevel;

    return 0;
}
