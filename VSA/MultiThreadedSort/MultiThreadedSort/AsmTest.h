#pragma once

// http://xoax.net/comp_sci/crs/algorithms/lessons/Lesson11/

int* CreatingRandomIntArray(const int length);
void destroyingArray(void* Array);
extern "C" int GetValueFromASM();
extern "C" int Multiply(int a, int b);
extern "C" void QuickSortASMTest(int* input, long length);
extern "C" int* FillingGivenArrayWithRandomData(int* input, int lengthInBytes);
extern "C" void ZeroArray(void* arr, int countInBytes);
void QuicksortCpp(int* ipA, int iSize);