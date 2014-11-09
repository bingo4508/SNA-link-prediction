__author__ = 'bingo4508'

import scipy as sp
import sys
from topological_feature.score import *
import itertools


def katz(G_katz, li):
    G = G_katz[0]
    katz_m = G_katz[1]
    nodes = G.nodes()
    rt = []
    for e in li:
        score = katz_m[(nodes.index(e[0]), nodes.index(e[1]))]
        rt.append((e[0], e[1], score))
    return rt

m = SocialNetwork("../data/train_edges.txt")
edge_file = sys.argv[1]

# Compute katz score
A = nx.adjacency_matrix(m.G)
I = sp.sparse.identity(len(m.G.nodes()))
beta = 0.005
katz_m = sp.sparse.linalg.inv(I-beta*A)-I

pair_score((m.G, katz_m), katz, edge_file, 'output/katz_test.txt')