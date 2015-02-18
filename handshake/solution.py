# Enter your code here. Read input from STDIN. Print output to STDOUT
from sys import stdin

no_of_inputs = int(stdin.readline())


def find_shakes(no):
    answer = 0
    add = no - 1
    for i in range(0,no):
        #answer = answer * (multiplier)
        #multiplier = multiplier - 1
        answer = answer + add
        add = add - 1
    
    print answer

    
for i in range(no_of_inputs):
    each_input = stdin.readline().rstrip("\n")
    find_shakes(int(each_input))