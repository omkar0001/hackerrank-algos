# Enter your code here. Read input from STDIN. Print output to STDOUT
# Enter your code here. Read input from STDIN. Print output to STDOUT
# Enter your code here. Read input from STDIN. Print output to STDOUT
from sys import stdin

gap = 0
prevgap = 0


def calc_answer(input_array):
    """
    Sort the array, then calculate the reachability between consecutive elements.
    """
    
    global gap
    global prevgap
    gap = 0
    prevgap = 0
    # Converting the input to the numbers.
    number_array = []
    for i in input_array:
        number_array.append(int(i))
    
    number_array.sort()    
    
    first_element_index = 0
    second_element_index = 1
    temp_array = number_array
    total_reachability = 0
    while(second_element_index < len(number_array)):
        reachability = calculate_reachability(temp_array, first_element_index, second_element_index)
        #temp_array = return_new_array(temp_array, first_element_index, second_element_index)    
        total_reachability = total_reachability + reachability
        first_element_index = first_element_index + 1
        second_element_index = second_element_index + 1
    print total_reachability
    
    
def calculate_reachability(input_array, first_element_index, second_element_index):
    global gap
    global prevgap
    
    difference = (input_array[second_element_index] + gap) - (input_array[first_element_index] + prevgap)
    
    if first_element_index == 0 and len(input_array) > second_element_index + 1:
        if difference == 4 and input_array[second_element_index] == input_array[second_element_index + 1]:
            prevgap = gap + 1
            gap = gap + 6
            return 2
        elif difference == 3 and input_array[second_element_index] == input_array[second_element_index + 1]:
            prevgap = gap + 2
            gap = gap + 7
            return 2
    
    difference = (input_array[second_element_index] + gap) - (input_array[first_element_index] + prevgap)
    prevgap = gap
    gap = gap + difference
    
    
    
    reachability = divmod(difference, 5)[0]
    difference = divmod(difference, 5)[1]
    reachability = reachability + divmod(difference, 2)[0]
    difference = divmod(difference, 2)[1]
    reachability = reachability + divmod(difference, 1)[0]
   
    return reachability
    #return 0

def return_new_array(input_array, first_element_index, second_element_index):
    difference = input_array[second_element_index] - input_array[first_element_index]
    second_element = input_array[second_element_index]
    number_array = []
    for i in input_array:
        number_array.append(i + difference)
    
    number_array[second_element_index] = second_element
    return number_array

#1 5 5 10 10
#2 2 2 2 2 5 5 5 5 5


no_of_inputs = int(stdin.readline())
for i in range(no_of_inputs):
    size_of_input = stdin.readline().rstrip("\n")
    input_array = stdin.readline().rstrip("\n").split(" ")
    calc_answer(input_array)
