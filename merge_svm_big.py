__author__ = 'bingo4508'
# Merge tab-delimited data with svm-format data
# Assume all files' first node in edge is in order
# ex: 1 0
#     1 3
#     1 13
#     2 9
#     ...

import sys

svm = sys.argv[1]
svm_pair = sys.argv[2]
largest_key = int(sys.argv[3])
feature = sys.argv[4]
feature_number = int(sys.argv[5])


def read_pair(f, curr_node):
    pair = []
    last_pos = f.tell()
    next_node = None
    while True:
        l = f.readline()
        if l == '': break
        l = l.strip().split(' ')
        if l[0] == curr_node:
            pair.append(l[0]+'_'+l[1])
        else:
            f.seek(last_pos)
            next_node = l[0]
            break
        last_pos = f.tell()
    return (pair, next_node)


def read_feature(f, curr_node):
    map = {}
    last_pos = f.tell()
    for l in f:
        l = l.strip().split('\t')
        k = l[0].split('_')
        if k[0] == curr_node:
            map[k[0]+'_'+k[1]] = [float(e) for e in l[1:]]
        else:
            f.seek(last_pos)
            break
        last_pos = f.tell()
    return map

curr_node = open(svm_pair, 'r').readline().split(' ')[0]
f_pair = open(svm_pair, 'r')
f_feature = open(feature, 'r')


x = svm.split('.')
fo = open(x[0]+'_new.'+x[1], 'w')
with open(svm, 'r') as f:
    map = read_feature(f_feature, curr_node)
    pair, curr_node = read_pair(f_pair, curr_node)
    for l in f:
        if len(pair) == 0:
            if curr_node is None: break
            print curr_node
            map = read_feature(f_feature, curr_node)
            pair, curr_node = read_pair(f_pair, curr_node)
        ll = l.strip()
        key = pair.pop(0)
        if key in map:
            for i, j in enumerate(map[key]):
                ll += ' %d:%g' % (largest_key+i+1, j)
        else:
            for i in range(feature_number-1):
                ll += ' %d:0' % (largest_key+i+1)
            ll += ' %d:10000' % (largest_key+feature_number)
        fo.write(ll+'\n')

