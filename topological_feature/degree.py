__author__ = 'bingo4508'
from topological_feature.score import *

m = SocialNetwork("../data/train_edges.txt")

with open('../output/degree.txt', 'w') as f:
    degree = m.G.degree(m.G.nodes())
    for k, v in degree.items():
        f.write('%d\t%d\n' % (k, v))
