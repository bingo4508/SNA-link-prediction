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
feature_number = int(sys.argv[4])
feature = sys.argv[5:]


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


# ex: adar_test.txt
def read_feature(f, curr_node):
    map = {}
    last_pos = f.tell()
    while True:
        l = f.readline()
        if l == '': break
        l = l.strip().split(' ')
        if l[0] == curr_node:
            map[l[0]+'_'+l[1]] = float(l[2])
        else:
            f.seek(last_pos)
            break
        last_pos = f.tell()
    return map

curr_node = open(svm_pair, 'r').readline().split(' ')[0]
f_pair = open(svm_pair, 'r')
f_feature = []
for f in feature:
    f_feature.append(open(f, 'r'))


x = svm.split('.')
fo = open(x[0]+'_new.'+x[1], 'w')
map = []
with open(svm, 'r') as f:
    for ft in f_feature:
        map.append(read_feature(ft, curr_node))
    pair, curr_node = read_pair(f_pair, curr_node)
    for l in f:
        if len(pair) == 0:
            if curr_node is None: break
            print curr_node

            for ft in f_feature:
                map.append(read_feature(ft, curr_node))
            pair, curr_node = read_pair(f_pair, curr_node)
        ll = l.strip()
        key = pair.pop(0)
        for i in range(feature_number):
            if key in map[i]:
                ll += ' %d:%g' % (largest_key+i+1, map[i][key])
            else:
                if i == feature_number-1:
                    print key
                    ll += ' %d:100000' % (largest_key+feature_number)
                else:
                    ll += ' %d:0' % (largest_key+i+1)
        fo.write(ll+'\n')

