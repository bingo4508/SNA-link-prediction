__author__ = 'bingo4508'
from model.social_network import SocialNetwork
import random as r

m = SocialNetwork("data/train_edges.txt")
nodes = m.G.nodes()
c = 1
count = 0
sel_pairs = []

with open('negative_pairs.txt', 'w') as f:
    while count < 10000000:
        pair = sorted((r.choice(nodes), r.choice(nodes)))
        if not m.G.has_edge(pair[0], pair[1]):
            if pair not in sel_pairs:
                sel_pairs.append(pair)
                count += 1
                f.write('%d %d\n' % (pair[0], pair[1]))
                c += 1
                if c > 100000:
                    f.flush()
                    c = 0
                    print(count)

