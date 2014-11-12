__author__ = 'bingo4508'

import sys
import numpy as np


def histogram(li, i, bins):
    ft = [e[i] for e in li]
    hist, inter = np.histogram(ft, bins=bins)
    with open('output/%s_%s_hist.txt' % (prefix, fn[i]), 'w') as f:
        for j, e in enumerate(hist):
            f.write('%f-%f\t%d\n' %(inter[j], inter[j+1], hist[j]))

feature = sys.argv[1]
prefix = sys.argv[2]
interval_num = 100
interval = float((sys.argv[3]))/interval_num

li = []
with open(feature, 'r') as f:
    fn = f.readline().strip().split('\t')[1:]
    for l in f:
        li.append([float(e) for e in l.strip().split('\t')[1:]])

feature_num = len(li[0])
# for i in range(feature_num):
#   histogram(li, i, 10)
bins = [i*interval for i in range(interval_num+1)]
histogram(li, 4, bins)