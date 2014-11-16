__author__ = 'bingo4508'

from sklearn.ensemble import RandomForestClassifier
from sklearn.externals import joblib
import sys


def svm_to_list(l, feature_num):
    l = l.strip().split(' ')
    map = {}
    for e in l[1:]:
        e = e.split(':')
        map[int(e[0])] = float(e[1])
    li = [int(l[0])]
    for i in range(1, feature_num+1):
        if i in map:
            li.append(map[i])
        else:
            li.append(0)
    return li

# SVM format
train_data = sys.argv[1]
trees = int(sys.argv[2])
format = sys.argv[3]    # -svm or -csv
feature_num = int(sys.argv[4])

########################################
# STEP 1: Read in the training examples.
########################################
truths = []
training_examples = []
for line in open(train_data):
    if format == '-svm':
        fields = [float(x) for x in line.split(",")]
    elif format == '-csv':
        fields = svm_to_list(line, feature_num)

    truths.append(fields[0])
    training_examples.append(fields[1:])

#############################
# STEP 2: Train a classifier.
#############################
clf = RandomForestClassifier(n_estimators=trees, oob_score=True)
clf = clf.fit(training_examples, truths)

#############################
# STEP 3: Save classifier.
#############################
joblib.dump(clf, '%s_%dtrees.rf' % (train_data.split('.')[0], trees))