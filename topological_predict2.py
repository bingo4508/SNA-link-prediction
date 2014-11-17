__author__ = 'bingo4508'
# This program ranks the topological feature score to predict friends of a node
# Filter selected nodes according to the friends number of a node
# ex: if a node has 8 friend, output 8/4 = 2 nodes

import sys
from model.social_network import SocialNetwork


def output(f, n, s):
    f.write('%d:%s\n' % (n, ','.join(s)))

topological_feature_file = sys.argv[1]
test_nodes = [int(e) for e in open(sys.argv[2], 'r').readline().strip().split(' ')]


f_result = open(topological_feature_file.split('.')[0]+'_predict_prob.txt', 'w')
m = SocialNetwork("data/train_edges.txt")

curr_node = 7
score = {}  # {score: node}
with open(topological_feature_file, 'r') as f:
    for line in f:
        line = line.strip().split(' ')
        n1 = int(line[0])
        n2 = int(line[1])
        # Next node:
        if n1 != curr_node:
            print curr_node

            while True:
                nn = test_nodes.pop(0)
                if nn != curr_node:
                    f_result.write('%d:\n' % nn)
                else:
                    break
            x = len(m.G.neighbors(curr_node))/4
            x = 1 if x == 0 else x
            x = 30 if x > 30 else x
            s = [str(score[e]) for e in sorted(score.keys(), reverse=True)[:x]]
            output(f_result, curr_node, s)
            score = {}
            curr_node = n1
        if not m.G.has_edge(n1, n2):
            score[float(line[2])] = n2

    print curr_node

    while True:
        nn = test_nodes.pop(0)
        if nn != curr_node:
            f_result.write('%d:\n' % nn)
        else:
            break
    s = [str(score[e]) for e in sorted(score.keys(), reverse=True)[:30]]
    output(f_result, curr_node, s)
    score = {}
    curr_node = n1
