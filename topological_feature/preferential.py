__author__ = 'bingo4508'
# args[1]: edge_file

import sys
from topological_feature.score import *


m = SocialNetwork("../data/train_edges.txt")
edge_file = sys.argv[1]

# Preferential Attachment
pair_score(m.G, nx.preferential_attachment, edge_file, '../output/preferential_test.txt')