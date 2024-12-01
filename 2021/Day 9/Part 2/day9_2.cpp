#include <iostream>
#include <queue>
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

int getBasinSize(vector<vector<int>>& grid, vector<vector<bool>>& visited, int y, int x) {
    if (visited[y][x] || grid[y][x] == 9) return 0;
    visited[y][x] = true;

    int point = grid[y][x];
    int size = 1;

    if (y - 1 >= 0 && grid[y - 1][x] > point) size += getBasinSize(grid, visited, y - 1, x);
    if (x - 1 >= 0 && grid[y][x - 1] > point) size += getBasinSize(grid, visited, y, x - 1);
    if (y + 1 < grid.size() && grid[y + 1][x] > point) size += getBasinSize(grid, visited, y + 1, x);
    if (x + 1 < grid[y].size() && grid[y][x + 1] > point) size += getBasinSize(grid, visited, y, x + 1);

    return size;
}

int getBasinSize(vector<vector<int>>& grid, int y, int x) {
    vector<vector<bool>> visited (grid.size());
    for (int i = 0; i < visited.size(); i++) {
        visited[i] = vector<bool> (grid[i].size());
    }

    return getBasinSize(grid, visited, y, x);
}

int main() {
    vector<vector<int>> grid;
    parseInput(grid);

    priority_queue<int> basinSizes;

    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid[i].size(); j++) {
            if (isLowPoint(grid, i, j)) {
                basinSizes.push(getBasinSize(grid, i, j));
            }
        }
    }

    int riskLevel = 1;

    for (int i = 0; i < 3; i++) {
        riskLevel *= basinSizes.top();
        basinSizes.pop();
    }

    cout << riskLevel;

    return 0;
}
