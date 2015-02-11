.data

myByteArray dq 1000 dup(?) ; 1000 gereserveerde bytes met random value
myDualWordArray dq 1000 dup(?) ; 1000 gereserveerde dualwords met random value
myOnesWordArray dd 1000 dup(?) ; creates a array of ten ones

.code
GetValueFromASM proc
	mov rax, 254
	mov rbx, 254
	add rax, rbx
	ret
GetValueFromASM endp
Multiply proc
	mov rax, rcx
	mov rbx, rdx ; puts two into bl
	mul rax	  ; and thus multiplies input by b
	ret
Multiply endp
ShowingASetOfNumbers proc
	mov eax, 3
	mov ebx, 0
	ret
ShowingASetOfNumbers endp
; void QuickSortASMTest(int* input, long length)
QuickSortASMTest proc
	cmp edx, 1
	jle finished			; equal to one the array is already sorted and smaller then one I can do nothing with this array

gettingThePivotPoint:
	mov r12, rdx			; moving the length of the array to R12. So I can starting dividing
	xor rdx, rdx			; emptying rdx so I can use division
	mov rbx, 2				; filling rbx with the divider
	mov rax, r12			; filling rax with the divident
	div rbx					; divide by 2 to get the middle of the array for the pivotpoint
	mov rbx, 4				; filling rbx with the size in bytes per unit in the array
	imul rax, rbx			; position times the size in bytes per unit
	mov r9, rcx				; filling r9 with the pointer to the beginning of the array
	add r9, rax				; moving the pointer to the pivot point

gettingTheSelectors:
	mov r10, rcx			; moving the left selector into r10
	mov rax, r12			; preparing rax for multiplication, filling it with the length of array
	imul rax, rbx			; multiplying rax by 4	
	mov r11, rcx			; moving the beginning of the array to r11
	sub rax, 4				; rax is now pointing past the end of the array by one integer(8b)
	add r11, rax			; r11 is now pointing to the end of the array and is now the rigth selector				

InitArraySizes:
	xor r13, r13			; emptying r13	so the value of left selector array size can be stored
	xor r14, r14			; emptying r14	so the value of right selector array size can be stored

SettingPivot:
	mov ebx, [r9]			; filling rbx with the value of the pivot

MainLoop:
	cmp r10, r11			; test if the two selectors didn't already pass each other
	jg CallRecursive		; if the left Selector is greater then the right selector we should jump to CallRecursive because, the selectors have passed eachother

LoopLeftSelector:
	mov eax, [r10]			; filling rax with the value of the selector
	cmp eax, ebx		    ; test if left selector(r10) is greater then the pivot(r9)
	jge LoopRightSelector	; if greater then we should start to Loop for the RightSelector
	add r10, 4		        ; add 4 bytes for so we can jump another integer in the array
	cmp r10, r11			; test if we went passed the size of the array
	jl	LoopLeftSelector	; if we didn't go passed the end of the array we should jump to the beginning of the loop

LoopRightSelector:
	mov eax, [r11]			; filling rax with the value of the selector
	cmp eax, ebx		    ; test if left selector(r10) is greater then the pivot(r9)
	jle SwitchingValuesOfSelectors	; if lesser then we should continue with our MainLoop
	sub r11, 4		        ; remove 4 bytes for so we can jump another integer in the array
	cmp r11, rcx			; test if we went passed the size of the array
	jg	LoopRightSelector	; if we didn't go passed the end of the array we should jump to the beginning of the loop

SwitchingValuesOfSelectors:
	mov eax, [r10]			; filling rax with the value of the left selector
	mov ebx, [r11]			; filling rbx with the value of the right selector
	mov [r10], ebx			; giving the left selector the value of right selector
	mov [r11], eax			; giving the right selector the value of left selector
	jmp	MainLoop			; jump to main loop for the check  
	
CallRecursive:
	
	call QuickSortASMTest   ; calling QuickSort Again
	call QuickSortASMTest	; calling QuickSort Again

finished:
	ret
QuickSortASMTest endp 
MergeSortASMTest proc
	ret
MergeSortASMTest endp
FillingGivenArrayWithRandomData proc
	cmp edx, 0 ; check for zero or less
	jle finished

	mov rax, rcx ; sava array address for the returnvalue

MainLoop:
	lea r10, myOnesWordArray
	add r10, 10h
	mov r8d, [r10]
	mov dword ptr [rcx], r8d
	inc rcx
	dec rdx
	jnz MainLoop

finished:
	ret
FillingGivenArrayWithRandomData endp
ZeroArray proc
	cmp edx, 0 ; check for zero or less
	jle finished

	cmp edx, 1 ; check for 1
	je SetFinalByte

	xor ax,ax
	mov r8d, edx ; save the original count to r8d
	shr edx, 1

MainLoop:
	mov word ptr [rcx], ax ;  Set two byte to 0
	add rcx, 2
	dec edx
	jnz MainLoop

	and r8d, 1
	jz finished

SetFinalByte:
	mov byte ptr [rcx], 0

finished:
	ret
ZeroArray endp
end