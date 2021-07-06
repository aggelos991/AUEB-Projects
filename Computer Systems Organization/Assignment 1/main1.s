.data
arrayA:.space 40
arrayB:.space 40
sparseA:.space 80
sparseB:.space 80
sparseC:.space 80
msg1: .asciiz"Position "
msg2: .asciiz" : "
crlf:.asciiz"\n"
msg3:.asciiz"Reading Array A\n\n"
msg4:.asciiz"Reading Array B\n\n"
menu:.asciiz" 1. Read Array A \n 2. Read Array B \n 3. Create Sparse Array A\n 4. Create Sparse Array B \n 5. Create Sparse Array C = A + B \n 6. Display Sparse Array A \n 7. Display Sparse Array B \n 8. Display Sparse Array C \n 0. Exit \n"
msg5:.asciiz"\nPlease enter a valid number.\n\n"
msg6:.asciiz"\nChoice? "
msg7:.asciiz" values\n"
msg8:.asciiz"Creating Sparse Array A\n"
msg9:.asciiz"Creating Sparse Array Β\n"
msg10:.asciiz"Creating Sparse Array C = A + B\n"
msg11:.asciiz" Value: "
msg12:.asciiz"Displaying Sparse Array A\n\n"
msg13:.asciiz"Displaying Sparse Array B\n\n"
msg14:.asciiz"Displaying Sparse Array C\n\n"
msg15:.asciiz"End of program"
space1:.asciiz"--------------------------------------------------------\n"
space2:.asciiz"    "
.text
.globl main

#t0=variable for menu selection

main:

#Printing the menu selections
for:
la $a0,space1
li $v0,4
syscall

la $a0,menu
li $v0,4
syscall

#Ask the user to enter a number
la $a0,msg6
li $v0,4
syscall

li $v0,5
syscall

move $t0,$v0#input

la $a0,space1
li $v0,4
syscall

beq $t0,$0,exit
beq $t0,1,readPinA
beq $t0,2,readPinB
beq $t0,3,createSparseA
beq $t0,4,createSparseB
beq $t0,5,createSparseC
beq $t0,6,printSparseA
beq $t0,7,printSparseB
beq $t0,8,printSparseC

li $t7,0

la $a0,msg5
li $v0,4
syscall

j for

#Goto Conditions

readPinA:
#Printing Reading Array A
	
	la $a0,msg3
	li $v0,4
	syscall
	
	la $a0,arrayA
	li $a1,0
	
	jal readPin
	move $t1,$v0
	j for
	
createSparseA:

	la $a0,msg8
	li $v0,4
	syscall
	
	la $a0,arrayA
	la $a1,sparseA
	
	jal createSparse
	
	move $s1,$v0
	
	j for
	
printSparseA:
	la $a0,msg12
	li $v0,4
	syscall
	
	la $a0,sparseA
	la $a1,($s1)
	jal printSparse
	
	j for 
		
readPinB:
#Printing Reading Array B
	
	la $a0,msg4
	li $v0,4
	syscall
	
	la $a0,arrayB
	li $a1,0
	
	jal readPin
	
	move $t1,$v0
	j for	
	
createSparseB:
	la $a0,msg9
	li $v0,4
	syscall
	
	la $a0,arrayB
	la $a1,sparseB
	jal createSparse
	move $s2,$v0
	
	j for
	
printSparseB:
	
	la $a0,msg13
	li $v0,4
	syscall
	
	la $a0,sparseB
	la $a1,($s2)
	jal printSparse
	j for
	
	
createSparseC:

	la $a0,msg10
	li $v0,4
	syscall
	
	la $a0,sparseA
	la $a1,sparseB
	la $a2,sparseC
	jal addSparse
	
	move $s3,$v0
	
	j for
	
	printSparseC:
	la $a0,msg14
	li $v0,4
	syscall
	
	la $a0,sparseC
	la $a1,($s3)
	jal printSparse
	
	j for
	
#Function

#readPin function
	
readPin:
move $t0,$a0
move $t1,$a1

# register t1 -> i
for_read: 
bge $t1,10,end_for_read #if i>=10 goto end_for_read

#"Position 
li $v0,4
la $a0,msg1
syscall

#i
li $v0,1
move $a0,$t1
syscall

#:"
li $v0,4
la $a0,msg2
syscall

li $v0,5
syscall
move $t2,$v0

#register t0 for address 
addi $t0,$t0,4
sw $t2,0($t0)

li $v0,4
la $a0,crlf
syscall

addi $t1,$t1,1#i++
addi $s0,$s0,1 

j for_read

#end_for_read condition

end_for_read:
move $v0,$t1
jr $ra

j for
	
#createSparse Function
	
createSparse:

	move $t0,$a0
	move $t1,$a1
	addi $t0,$t0,4
	li $t4,0#i=0
	li $t2,0#k=0
	li $t5,0
	for3:
	bge $t4,10,end_for_create#if i>=10 goto end_for
	lw $t3,0($t0)
	beq $t3,$0,zero
	addi $t1,$t1,4
	sw $t4,0($t1)
	#addi $t5,$t5,1
	addi $t1,$t1,4
	sw $t3,0($t1)
	
	addi $t5,$t5,1
	addi $t4,$t4,1
	addi $t0,$t0,4
	j for3
	
zero:
	addi $t4,$t4,1
	addi $t0,$t0,4
	j for3
end_for_create:
 
move $a0,$t5
li $v0,1
syscall
	
la $a0,msg7
li $v0,4
syscall

move $v0,$t5
jr $ra
j for


#addSparse Function

addSparse:

#registers

# $s1-->length of sparse A
# $s2-->length of sparse B
# $s3-->length of sparse C = variable c
# $t0-->memory address of sparse A
# $t1-->element of sparse A
# $t2-->memory address of sparse B
# $t3-->element of sparse B
# $t4-->sparse C
# $t6-->variable a
# $t7-->variable b 

move $t0,$a0 #load sparse A
move $t2,$a1 #load sparse B
move $t4,$a2 #load sparse C
li $t6,0 #a=0
li $t7,0 #b=0

for_compare:
	bge $t6,$s1,end_for_compare
	bge $t7,$s2,end_for_compare
	
	lw $t1,($t0) #load an element from sparse A
	lw $t3,($t2) #load an element from sparse B
	
	beq $t1,$t3,equal
	blt $t1,$t3,less
	bgt $t1,$t3,more
	
	#if (SparseA[a] < SparseB [b])
less:
	sw $t1,($t4) #store the element into sparse C
	
	addi $t4,$t4,4 #move onto the next memory address of sparse C
	addi $t0,$t0,4 #move onto the next memory address of sparse A
	
	sw $t1,($t4)#store another element into sparse C
	
	addi $s3,$s3,1 #c++
	
	addi $t4,$t4,4 #move onto next memory address of sparse C
	addi $t0,$t0,4 #move onto next memory address of sparse A
	
	#if (SparseA[a] > SparseB [b])
more:
	
	sw $t3,($t4) #store the element into sparse C
	
	addi $t4,$t4,4 #move onto the next memory address of sparse C
	addi $t2,$t2,4 #move onto the next memory address of sparse B
	
	sw $t3,($t4) #store the element into sparse C
	
	addi $s3,$s3,1 #c++
	
	addi $t4,$t4,4 #move onto the next memory address of sparse C
	addi $t2,$t2,4 #move onto the next memory address of sparse B

equal:
	sw $t1,($t4) #store the element into sparse C
	
	addi $t4,$t4,4 #move onto the next memory address of sparse C
	addi $t0,$t0,4 #move onto the next memory address of sparse A
	addi $t2,$t2,4 #move onto the next memory address of sparse B
	
	add $s4,$t1,$t3 #SparseC [c++] = SparseA[a++] + SparseB[b++]; 
	sw $s4,($t4) #store the element into sparse C

end_for_compare:

for_a_loop:
	bgt $t6,$s1,end_loop_a
	addi $t6,$t6,1 #a++
	lw $t1,($t0) #load an element from sparse A
	sw $t1,($t4) #store the element into sparse C
	
	addi $t4,$t4,4 #move onto the next memory address of sparse C
	addi $t0,$t0,4 #move onto the next memory address of sparse A
	
	sw $t1,($t4)#store another element into sparse C
	
	addi $s3,$s3,1 #c++
	
	addi $t4,$t4,4 #move onto next memory address of sparse C
	addi $t0,$t0,4 #move onto next memory address of sparse A
	
	j for_a_loop
	
end_loop_a:
	addi $s3,$s3,-1
	
for_b_loop:
	bgt $t7,$s2,end_loop_b
	addi $t7,$t7,1#b++
	
	lw $t3,($t2) #load an element from sparse B
	sw $t3,($t4) #store the element into sparse C
	
	addi $t4,$t4,4 #move onto the next memory address of sparse C
	addi $t2,$t2,4 #move onto the next memory address of sparse B
	
	sw $t3,($t4) #store the element into sparse C
	
	addi $s3,$s3,1 #c++
	
	addi $t4,$t4,4 #move onto the next memory address of sparse C
	addi $t2,$t2,4 #move onto the next memory address of sparse B
		
	j for_b_loop
	
end_loop_b:	
	addi $s3,$s3,-1

end_for_add:
	move $a0,$s3
	li $v0,1
	syscall
	
	la $a0,msg7
	li $v0,4
	syscall

	move $v0,$s3
	jr $ra
	j for

#printSparse function

printSparse:

move $t0,$a0
move $t1,$a1
li $t4,-1
addi $t0,$t0,4
	for8:
	bge $t4,$t1,end_for8
	
	lw $t3,0($t0)
	
	la $a0,msg1
	li $v0,4
	syscall
	
	move $a0,$t3
	li $v0,1
	syscall
	
	addi $t0,$t0,4
	
	la $a0,msg11
	li $v0,4
	syscall
	
	lw $t3,0($t0)
	
	move $a0,$t3
	li $v0,1
	syscall
	
	la $a0,space2
	li $v0,4
	syscall

	addi $t0,$t0,4
	
	la $a0,crlf
	li $v0,4
	syscall
	
	addi $t4,$t4,1
	
	j for8

end_for8:
la $a0,crlf
li $v0,4
syscall
 j for

#exit program

exit:
#Printing End of Program
la $a0,msg15
li $v0,4
syscall

li $v0,10
syscall
