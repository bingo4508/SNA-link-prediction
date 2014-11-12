__author__ = 'bingo4508'
# Usage: ./Evaluate.py input_file

import sys
from model.social_network import SocialNetwork


def F1_score(li, G):
    tp = []
    tp_fp = []
    tp_fn = []
    for k, v in li.items():
        result = [G.has_edge(k, friend) for friend in v]
        tp.append(result.count(True))
        tp_fp.append(len(result))
        tp_fn.append(len(G.neighbors(k)))
    precision = float(sum(tp))/sum(tp_fp)
    recall = float(sum(tp))/sum(tp_fn)
    f1_score = 2*(precision*recall)/(precision+recall) if (precision+recall) > 0 else 0
    return f1_score


def MAP(li, G):
    AveP = []
    for k, v in li.items():
        correct = 0
        up = 0
        for i, j in enumerate(v):
            has_edge = G.has_edge(k, j)
            if has_edge: correct += 1
            up += (1 if has_edge else 0)*(correct/(i+1))
        AveP.append(float(up)/len(G.neighbors(k)))
    map = sum(AveP)/len(AveP)
    return map


def load_input(input_file):
    li = {}
    with open(input_file, 'r') as f:
        for l in f:
            l = l.strip().split(':')
            li[int(l[0])] = [int(e) for e in l[1].split(',')]
    return li

if __name__ == '__main__':

    input_file = sys.argv[1]
    li = load_input(input_file)
    m = SocialNetwork("data/train_edges.txt")
    print("F1_score:%f" % F1_score(li, m.G))
    print("MAP: %f" % MAP(li, m.G))

