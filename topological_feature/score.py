__author__ = 'bingo4508'
import itertools
import networkx as nx
from model.social_network import SocialNetwork


# edge_file, out_file delimited by ' '
def pair_score(G, func, edge_file, out_file):
    # Output format: node1 node2 score
    with open(out_file, 'w') as f:
        li = []
        count = 0
        with open(edge_file, 'r') as f2:
            for line in f2:
                line = line.strip().split(' ')
                li.append((int(line[0]), int(line[1])))
                if count == 100000:
                    count = 0
                    preds = func(G, li)
                    for n1, n2, p in preds:
                        if p != 0:
                            f.write('%d %d %.5f\n' % (n1, n2, p))
                    li = []
                count += 1


def all_pair_score(G, func, out_file):
    # Output format: node1 node2 score
    with open(out_file, 'w') as f:
        li = []
        count = 0
        for u, v in itertools.combinations(G, 2):
            if G.has_edge(u, v) is False and u != v:
                li.append((u, v))
                if count == 100000:
                    count = 0
                    preds = func(G, li)
                    for n1, n2, p in preds:
                        if p != 0:
                            f.write('%d\t%d\t%.5f\n' % (n1, n2, p))
                    li = []
                count += 1