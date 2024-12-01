#include <iostream>
#include <vector>
#include <math.h>

using namespace std;

void countBits(vector<string>& binaryNumbers, vector<int>& bits) {
    for (string binaryNumber : binaryNumbers) {
        for (int i = 0; i < binaryNumber.size(); i++) {
            if (binaryNumber[i] == '1') {
                bits[i]++;
            }
        }
    }
}

int getRate(vector<string>& binaryNumbers, vector<int>& bits, bool useMostCommonBit) {
    int rate = 0;

    for (int i = 0; i < bits.size(); i++) {
        int power = pow(2, bits.size() - i - 1);

        if (useMostCommonBit && bits[i] >= binaryNumbers.size() / 2) {
            rate += power;
        } else if (!useMostCommonBit && bits[i] < binaryNumbers.size() / 2) {
            rate += power;
        }
    }

    return rate;
}

int main() {
    vector<string> binaryNumbers;
    string binaryNumber;
    
    while (cin >> binaryNumber) {
        binaryNumbers.push_back(binaryNumber);
    }

    vector<int> bits (binaryNumber.size());
    countBits(binaryNumbers, bits);

    int gammaRate = getRate(binaryNumbers, bits, true);
    int epsilonRate = getRate(binaryNumbers, bits, false);

    cout << gammaRate * epsilonRate;

    return 0;
}
