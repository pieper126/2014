#include "stdafx.h"
#include <ctime>

void Partition(int* ipA, int iSize) {

	// Partitions of size 0 or 1 are already sorted
	if (iSize <= 1) {
		return;
	}

	// Select the pivot randomly, but not the first element
	int iPivot = ipA[(rand() % (iSize - 1)) + 1];

	// Indices of the entries to be swapped
	int iLower = -1;
	int iUpper = iSize;

	// Partition array into sections above and below the pivot
	while (iLower < iUpper) {

		do {
			++iLower;
		} while (ipA[iLower] < iPivot);

		do {
			--iUpper;
		} while (ipA[iUpper] > iPivot);

		// Swap the entries at the lower and upper indices
		if (iLower < iUpper) {
			int iTemp = ipA[iLower];
			ipA[iLower] = ipA[iUpper];
			ipA[iUpper] = iTemp;
		}
	}

	// Recursively call partition on each partititon.
	Partition(ipA, iLower);
	Partition(&(ipA[iLower]), iSize - iLower);
}

void QuicksortCpp(int* ipA, int iSize) {
	// Seed the random number generator
	srand((unsigned int)time(0));
	Partition(ipA, iSize);
}