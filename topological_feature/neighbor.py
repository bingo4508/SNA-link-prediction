__author__ = 'bingo4508'
from topological_feature.score import *

m = SocialNetwork("../data/train_edges.txt")

with open('../output/neighbor.txt', 'w') as f:
    for n in m.G.nodes():
        nb = m.G.neighbors(n)
        f.write('%d ' % n)
        for e in nb:
            f.write('%d ' % e)
        f.write('\n')
