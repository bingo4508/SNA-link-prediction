__author__ = 'bingo4508'
# args[1]: edge_file

import sys
from topological_feature.score import *


m = SocialNetwork("../data/train_edges.txt")
edge_file = sys.argv[1]

# Adamic/Adar
pair_score(m.G, nx.adamic_adar_index, edge_file, 'output/adar_test.txt')
