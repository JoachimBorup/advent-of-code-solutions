#include <iostream>
#include <queue>
#include <tuple>
#include <vector>

using namespace std;

vector<vector<int>> grid;
vector<vector<int>> distTo;

void relax(queue<tuple<int, int>>& queue, int dist, int y, int x) {
    if (y < 0 || y >= grid.size() || x < 0 || x >= grid[y].size()) {
        return;
    }

    if (dist + grid[y][x] < distTo[y][x]) {
        distTo[y][x] = dist + grid[y][x];
        queue.push(tuple<int, int> {y, x});
    }
}

void relax(queue<tuple<int, int>>& queue) {
    tuple<int, int> coordinate = queue.front();
    queue.pop();

    int y = get<0>(coordinate);
    int x = get<1>(coordinate);
    int dist = distTo[y][x];

    relax(queue, dist, y + 1, x);
    relax(queue, dist, y - 1, x);
    relax(queue, dist, y, x + 1);
    relax(queue, dist, y, x - 1);
}

void parseInput() {
    string temp;
    while (cin >> temp) {
        vector<int> gridRow;
        vector<int> distToRow;

        for (char ch : temp) {
            gridRow.push_back(ch - '0');
            distToRow.push_back(INT_MAX);
        }

        grid.push_back(gridRow);
        distTo.push_back(distToRow);
    }

    int h = grid.size();

    // Repeat to the right
    for (int i = 0; i < h; i++) {
        vector<int> row = grid[i];

        for (int j = 0; j < 4; j++) {
            for (int cell : row) {
                grid[i].push_back((cell + j) % 9 + 1);
                distTo[i].push_back(INT_MAX);
            }
        }
    }

    // Repeat downwards
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < h; j++) {
            vector<int> row;

            for (int cell : grid[j]) {
                row.push_back((cell + i) % 9 + 1);
            }

            grid.push_back(row);
            distTo.push_back(vector<int> (row.size(), INT_MAX));
        }
    }
}

int main() {
    parseInput();
    distTo[0][0] = 0;

    queue<tuple<int, int>> queue;
    queue.push(tuple<int, int> {0, 0});

    while (!queue.empty()) {
        relax(queue);
    }

    cout << distTo.back().back() <<'\n';

    return 0;
}
