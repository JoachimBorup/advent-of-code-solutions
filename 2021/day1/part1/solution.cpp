#include <iostream>
#include <vector>

using namespace std;

int main() {
    vector<int> vec;
    int next;

    while (cin >> next) {
        vec.push_back(next);
    }

    int increases = 0;

    for (int i = 1; i < vec.size(); i++) {
        if (vec[i - 1] < vec[i]) {
            increases++;
        }
    }

    cout << increases;

    return 0;
}
