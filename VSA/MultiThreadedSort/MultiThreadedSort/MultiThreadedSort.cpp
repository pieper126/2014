// MultiThreadedSort.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <io.h>
#include <ostream>
#include <istream>
#include <iostream>
#include <random>
#include <array>

using namespace std;

void PrintingArray(const int* inputArray, int length);

const int ARRAY_SIZE = 5000;
const int SECOND_ARRAY_SIZE = 10;

int _tmain(int argc, _TCHAR* argv[])
{
	int* intArray = CreatingRandomIntArray(ARRAY_SIZE);

	//printingarray(intarray, array_size);

	//destroyingarray(intarray);

	//cout << multiply(2, 2) << endl;

	//quicksortasmtest(intarray, array_size);

	//fillinggivenarraywithrandomdata(intarray, 10);
	//fillinggivenarraywithrandomdata(intarray, second_array_size);

	PrintingArray(intArray, ARRAY_SIZE);

	QuickSortASMTest(intArray, ARRAY_SIZE);

	PrintingArray(intArray, ARRAY_SIZE);

	ZeroArray((void*)intArray, ARRAY_SIZE*4);

	PrintingArray(intArray, ARRAY_SIZE);

	FillingGivenArrayWithRandomData(intArray, ARRAY_SIZE * 4);

	PrintingArray(intArray, ARRAY_SIZE);

	cin.get();
	return 0;
}

int* CreatingRandomIntArray(const int length){
	int* returnValue = new int[length];

	for (size_t i = 0; i < length; i++)
	{
		returnValue[i] = rand() % 5000;
	}	

	return returnValue;
}

void destroyingArray(void* Array){
	delete[] Array;
}

void PrintingArray(const int* inputArray, const int length){
	for (size_t i = 0; i < length; i++)
	{
		cout << inputArray[i] << endl;
	}
}