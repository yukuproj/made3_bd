#!/usr/bin/env python3

import sys
 

def reduce():
    summa = 0
    count = 0
    for line in sys.stdin:
        data = line.strip().split("\t")
        summa = summa + float(data[0])
        count += 1
            
    print(summa / count)

if __name__ == "__main__":
    reduce()