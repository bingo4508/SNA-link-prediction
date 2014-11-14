__author__ = 'bingo4508'
# Merge tab-delimited data with svm-format data

import sys

svm = sys.argv[1]
svm_pair = sys.argv[2]
largest_key = int(sys.argv[3])
tab = sys.argv[4]

pair = []
with open(svm_pair, 'r') as f:
    for l in f:
        l = l.strip().split(' ')
        l = sorted([int(l[0]), int(l[1])])
        pair.append(str(l[0])+'_'+str(l[1]))

map = {}
with open(tab, 'r') as f:
    f.readline()
    for l in f:
        l = l.strip().split('\t')
        k = l[0].split('_')
        k = sorted([int(k[0]), int(k[1])])
        map[str(k[0])+'_'+str(k[1])] = [float(e) for e in l[1:]]

x = svm.split('.')
fo = open(x[0]+'_new.'+x[1], 'w')
with open(svm, 'r') as f:
    for l in f:
        ll = l.strip()
        key = pair.pop(0)
        for i, j in enumerate(map[key]):
            ll += ' %d:%g' % (largest_key+i+1, j)
        fo.write(ll+'\n')

