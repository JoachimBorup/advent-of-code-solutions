#include <iostream>
#include <vector>
#include <math.h>

using namespace std;

int countBits(vector<string>& binaryNumbers, int index) {
    int bits = 0;
    
    for (string binaryNumber : binaryNumbers) {
        if (binaryNumber[index] == '1') {
            bits++;
        }
    }

    return bits;
}

int getRating(vector<string> binaryNumbers, bool useMostCommonBit) {
    int length = binaryNumbers[0].size();

    for (int i = 0; i < length && binaryNumbers.size() > 1; i++) {
        int bits = countBits(binaryNumbers, i);
        double half = binaryNumbers.size() / 2.0;
        char mostCommonValue = useMostCommonBit ? bits >= half ? '1' : '0' : bits < half ? '1' : '0';
        
        for (int j = binaryNumbers.size() - 1; j >= 0 && binaryNumbers.size() > 1; j--) {
            if (binaryNumbers[j][i] != mostCommonValue) {
                binaryNumbers.erase(binaryNumbers.begin() + j);
            }
        }
    }

    string binaryNumber = binaryNumbers[0];
    int rating = 0;

    for (int i = 0; i < binaryNumber.size(); i++) {
        if (binaryNumber[i] == '1') {
            rating += pow(2, binaryNumber.size() - i - 1);
        }
    }

    return rating;
}

int main() {
    vector<string> binaryNumbers;
    string binaryNumber;
    
    while (cin >> binaryNumber) {
        binaryNumbers.push_back(binaryNumber);
    }

    int oxygenGeneratorRating = getRating(binaryNumbers, true);
    int CO2ScrubberRating = getRating(binaryNumbers, false);

    cout << oxygenGeneratorRating * CO2ScrubberRating;

    return 0;
}
