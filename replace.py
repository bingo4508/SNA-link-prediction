__author__ = 'bingo4508'
import sys

want_to_replace = sys.argv[2]
place_to = sys.argv[3]


with open(sys.argv[1].split('.')[0]+'_new.txt', 'w') as fo:
    with open(sys.argv[1], 'r') as f:
        for l in f:
            fo.write(l.replace(want_to_replace, place_to))