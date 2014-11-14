__author__ = 'bingo4508'

import sys

svm_file = sys.argv[1]
feature_num = int(sys.argv[2])
f_csv = open(svm_file.replace('.txt', '.csv'), 'w')

# Open svm format file
with open(svm_file, 'r') as f:
    for l in f:
        l = l.strip().split(' ')
        map = {}
        for e in l[1:]:
            e = e.split(':')
            map[int(e[0])] = e[1]

        out = ''
        if '-l' in sys.argv: # Output with label(first column)
            out += l[0]+','
        for i in range(1, feature_num+1):
            if i in map:
                out += map[i]+','
            else:
                out += '0,'
        out = out[:-1]+'\n'
        f_csv.write(out)
f_csv.close()