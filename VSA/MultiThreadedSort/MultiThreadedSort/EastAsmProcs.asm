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