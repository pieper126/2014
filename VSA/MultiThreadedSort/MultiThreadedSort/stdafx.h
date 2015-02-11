// stdafx.h : include file for standard system include files,
// or project specific include files that are used frequently, but
// are changed infrequently
//

#pragma once

#include "targetver.h"

#include <stdio.h>
#include <tchar.h>

int* CreatingRandomIntArray(const int length);
void destroyingArray(void* Array);
extern "C" int GetValueFromASM();
extern "C" int Multiply(int a, int b);
extern "C" void QuickSortASMTest(int* input, long length);
extern "C" int* FillingGivenArrayWithRandomData(int* input, int lengthInBytes);
extern "C" void ZeroArray(void* arr, int countInBytes);



// TODO: reference additional headers your program requires here
