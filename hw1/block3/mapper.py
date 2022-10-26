#!/usr/bin/env python3

import sys
import csv

def mapper():
    for line in csv.reader(sys.stdin):
        data = line[9]
        if len(data) > 0 and data != 'price':
            sys.stdout.write(f'{data}\t1\n')

if __name__ == "__main__":

    mapper()