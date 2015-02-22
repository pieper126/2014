.code
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
	mov rax, r12			; preparing rax for multiplication, filling it with the length of arrays
	imul rax, rbx			; multiplying rax by 4	
	mov r11, rcx			; moving the beginning of the array to r11
	sub rax, 4				; rax is now pointing past the end of the array by one integer(40o)
	add r11, rax			; r11 is now pointing to the end of the array and is now the right selector
	mov r15, r11			; moving the pointer of the end of the array to r15 so it can be used later

SettingPivot:
	mov r14d, [r9]			; filling rbx with the value of the pivot
	push r9				    ; save the state of r9

MainLoop:
	cmp r10, r11			; test if the two selectors didn't already pass each other
	jg CallRecursive		; if the left Selector is greater then the right selector we should jump to CallRecursive because, the selectors have passed eachother

LoopLeftSelector:
	;mov eax, [r10]			; filling rax with the value of the selector
	cmp [r10], r14d		    ; test if left selector(r10) is greater then the pivot(r9)
	jge LoopRightSelector	; if greater then we should start to Loop for the RightSelector
	add r10, 4		        ; add 4 bytes for so we can jump another integer in the array
	cmp r10, r15			; test if we went passed the size of the array
	jl	LoopLeftSelector	; if we didn't go passed the end of the array we should jump to the beginning of the loop

LoopRightSelector:
	;mov eax, [r11]			; filling rax with the value of the selector
	cmp [r11], r14d		    ; test if left selector(r10) is greater then the pivot(r9)
	jle SwitchingValuesOfSelectors	; if lesser then we should continue with our MainLoop
	sub r11, 4		        ; remove 4 bytes for so we can jump another integer in the array
	cmp r11, rcx			; test if we went passed the size of the array
	jg	LoopRightSelector	; if we didn't go passed the end of the array we should jump to the beginning of the loop

SwitchingValuesOfSelectors:
	mov eax, [r10]			; filling rax with the value of the left selector
	mov ebx, [r11]			; filling rbx with the value of the right selector
	cmp eax, ebx			; test if they are equal to each other
	je MovingSelectorsOneStep	; if equal jump 
	mov [r10], ebx			; giving the left selector the value of right selector
	mov [r11], eax			; giving the right selector the value of left selector
	;add r10, 4				; moving the left selector 4 bytes forward
	;sub r11, 4				; moving the right selector 4 bytes back
	jmp	MainLoop			; jump to main loop for the check  

MovingSelectorsOneStep:
	add r10, 4				; moving the left selector 4 bytes forward
	sub r11, 4				; moving the right selector 4 bytes backwards
	jmp MainLoop			; jump back to the main loop
	
CallRecursive:
	mov rdx, r9				; filling rdx with the pivot
	sub rdx, rcx			; subtracting the beginning of the array from the pivot so we can get the lenght of the array
	call lengthOfTheArrayInIntegers	; filling rdx with the length of the array in integers
	call QuickSortASMTest   ; calling QuickSort 
	pop r9					; restoring the state of r9
	mov rdx, r15			; retrieving the pointer of to the end of the array from r15
	sub rdx, r9				; subtracting the pivot from the end of the array
	call lengthOfTheArrayInIntegers	; filling rdx with the length of the array in integers
	mov rcx, r9				; giving rcx the pointer of the pivot point
	add rcx, 4				; adding 4 bytes(length of a 32-bit integer) to the pivot point and a such will serve as the pointer to the second array
	call QuickSortASMTest	; calling QuickSort Again

finished:
	ret 
QuickSortASMTest endp 














































lengthOfTheArrayInIntegers proc
	mov rax, rdx			; moving the divident into rax
	xor rdx, rdx			; nulling rdx
	mov rbx, 4				; moving the divider into rbx, this is 4 because the length of a int is 4 bytes long
	div rbx					; dividing the length in bytes by 4 so we get the length in 32-bit integers
	mov rdx, rax			; moving the length in integers to rdx
	ret
lengthOfTheArrayInIntegers endp

MergeSortASMTest proc
	ret
MergeSortASMTest endp
end