__author__ = 'bingo4508'
# Assume all files' first node in edge is in order
# ex: 1 0
#     1 3
#     1 13
#     2 9
#     ...

import sys

def output(f, table_key, table_val):
    for i, k in enumerate(table_key):
        f.write(k+'\t')
        for e in table_val[i]:
            f.write(str(e)+'\t')
        f.write('\n')
    f.flush()


def merge_feature(f, table_key, table_val, curr_node):
    tb = {}
    last_pos = f.tell()
    for l in f:
        try:
            l = l.strip().split(' ')
            if l[0] == curr_node:
                tb[l[0]+'_'+l[1]] = float(l[2])
            else:
                f.seek(last_pos)
                break
        except:
            pass
        last_pos = f.tell()
    for i, p in enumerate(table_key):
        if p in tb:
            table_val[i].append(tb[p])
        else:
            table_val[i].append(0.0)

def batch(ft_f, fo, table_key, table_val, curr_node):
    for f in ft_f:
        merge_feature(f, table_key, table_val, curr_node)
    # Ouput
    output(fo, table_key, table_val)

edge_file = sys.argv[1]
table_key = []
table_val = []
ft_f = []


# Output file
fo = open(edge_file.replace('.txt', '')+'_feature.txt', 'w')

# Open feature files
for file in sys.argv[2:]:
    ft_f.append(open(file, 'r'))

# Open edge_file and Run- ' ' delimited
with open(edge_file, 'r') as f:
    # First iteration to set current node
    l = f.readline().strip().replace(' ', '_')
    table_key.append(l)
    table_val.append([])
    curr_node = l.split('_')[0]

    for l in f:
        l = l.strip().replace(' ', '_')
        table_key.append(l)
        table_val.append([])
        n = l.split('_')[0]

        # Batch processing, save each feature to table_val
        if n != curr_node:
            batch(ft_f, fo, table_key, table_val, curr_node)
            print curr_node
            curr_node = n
            # Reset
            table_key = []
            table_val = []