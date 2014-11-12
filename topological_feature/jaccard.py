__author__ = 'bingo4508'
# args[1]: edge_file

import sys
from topological_feature.score import *


m = SocialNetwork("../data/train_edges.txt")
edge_file = sys.argv[1]

# Jaccard Coefficient
pair_score(m.G, nx.jaccard_coefficient, edge_file, '../output/jaccard_test.txt')