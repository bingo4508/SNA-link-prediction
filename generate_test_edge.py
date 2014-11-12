__author__ = 'bingo4508'
from model.social_network import SocialNetwork
m = SocialNetwork("data/train_edges.txt")

l = open('data/test_nodes.txt','r').readline().strip().split(' ')
l = [int(e) for e in l]

with open('output/test_edges.txt', 'w') as f:
    for n1 in l:
        for n2 in m.G.nodes():
            if n1 != n2:
                f.write('%d %d\n' % (n1, n2))