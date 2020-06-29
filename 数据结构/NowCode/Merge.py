
def merge(array):
    ret = []
    for i in range(len(array) -1):
        one_0 = array[i][0]
        one_1 = array[i][1]
        two_0 = array[i+1][0]
        two_1 = array[i+1][1]

        if two_0 <= one_1:
            ret[0] = one_0
            ret[1] = two_1




