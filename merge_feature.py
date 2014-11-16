__author__ = 'bingo4508'

import sys

edge_file = sys.argv[1]
shortest_path_index = int(sys.argv[2])

table = {}

# ' ' delimited
with open(edge_file, 'r') as f:
    for l in f:
        l = l.strip().replace(' ', '_')
        table[l] = []

for i, file in enumerate(sys.argv[3:]):
    # '\t' delimited
    with open(file, 'r') as f:
        tb = {}
        for l in f:
            try:
                l = l.strip().split('\t')
                tb[l[0]+'_'+l[1]] = float(l[2])
            except:
                pass
        for p in table:
            if p in tb:
                table[p].append(tb[p])
            else:
                if i == shortest_path_index:
                    table[p].append(100000)
                else:
                    table[p].append(0)

with open(edge_file.replace('.txt', '')+'_feature.txt', 'w') as f:
    for k in table:
        f.write(k+'\t')
        for e in table[k]:
            f.write(str(e)+'\t')
        f.write('\n')