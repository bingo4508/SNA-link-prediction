__author__ = 'bingo4508'
# args[1]: edge_file

import sys
from topological_feature.score import *


def clustering_coefficient(G, li):
    rt = []
    for e in li:
        cc = nx.clustering(G, (e[0], e[1]))
        cc = cc[e[0]]*cc[e[1]]
        rt.append((e[0], e[1], cc))
    return rt


m = SocialNetwork("../data/train_edges.txt")
edge_file = sys.argv[1]

# Shortest path
pair_score(m.G, clustering_coefficient, edge_file, 'output/cc_test.txt')
