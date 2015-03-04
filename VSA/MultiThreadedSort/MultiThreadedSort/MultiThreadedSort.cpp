// MultiThreadedSort.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"

using namespace std;

void PrintingArray(const int* inputArray, int length);

const int ARRAY_SIZE = 8;
const int SECOND_ARRAY_SIZE = 10;

int _tmain(int argc, _TCHAR* argv[])
{
	//int* testIntArray = CreatingRandomIntArray(ARRAY_SIZE);

	//printingarray(intarray, array_size);

	//destroyingarray(intarray);

	//cout << multiply(2, 2) << endl;

	//quicksortasmtest(intarray, array_size);

	//fillinggivenarraywithrandomdata(intarray, 10);
	//fillinggivenarraywithrandomdata(intarray, second_array_size);

	//intArray[0] = -666;
	//intArray[ARRAY_SIZE - 1] = -666;

	int testIntArray[ARRAY_SIZE] = { 10000, 5, 4, 100, 6000, 9000, 25, 30 };

	QuickSortASMTest(testIntArray, ARRAY_SIZE);
	//QuicksortCpp(testIntArray, ARRAY_SIZE);

	cout << endl;

	PrintingArray(testIntArray, ARRAY_SIZE);

	//ZeroArray((void*)intArray, ARRAY_SIZE*4);

	//PrintingArray(intArray, ARRAY_SIZE);

	//FillingGivenArrayWithRandomData(intArray, ARRAY_SIZE * 4);

	//PrintingArray(intArray, ARRAY_SIZE);

	cin.get();
	return 0;
}

int* CreatingRandomIntArray(const int length){
	int* returnValue = new int[length];

	for (size_t i = 0; i < length; i++)
	{
		returnValue[i] = rand() % 10;
	}	

	return returnValue;
}

void destroyingArray(void* Array){
	delete[] Array;
}

void PrintingArray(const int* inputArray, const int length){
	for (size_t i = 0; i < length; i++)
	{
		cout << inputArray[i] << "-";
	}
}