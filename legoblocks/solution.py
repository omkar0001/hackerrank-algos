# X(M,N) = F(N)^M - D(M,N)

# F(N) = 2*F(N-1) - F(N-5)
# D(M,N) = (// )(D(N-1) - D(N-5))*(2^M-1) + F(N-1)^M) //No of 
from sys import stdin

def getF(N):
    if N == 0 or N == 1:
        return 1
    elif N < 0:
        return 0
    else:
        return 2*getF(N-1) - getF(N-5)

def getX(M,N):
    return pow(getF(N),M) - getD(M,N)


def getD(M,N):
    if N == 0 or N == 1:
        return 0
    if N < 5:
        return getD(M,N-1)*(pow(2,M)-1) + pow(getF(N-1),M)
    else:
        return (getD(M,N-1) - getD(M,N-5))*(pow(2,M)-1) + pow(getF(N-1),M)

no_of_inputs = int(stdin.readline())
for i in range(no_of_inputs):
    each_input = stdin.readline().rstrip("\n")
    #each_input.split(" ")[0]each_input.split(" ")[1]
    print getX(int(each_input.split(" ")[0]), int(each_input.split(" ")[1]))%1000000007