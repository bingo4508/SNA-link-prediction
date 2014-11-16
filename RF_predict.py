__author__ = 'bingo4508'

import sys

from sklearn.ensemble import RandomForestClassifier
from sklearn.externals import joblib


def svm_to_list(l, feature_num):
    l = l.strip().split(' ')
    map = {}
    for e in l[1:]:
        e = e.split(':')
        map[int(e[0])] = float(e[1])
    li = []
    for i in range(1, feature_num+1):
        if i in map:
            li.append(map[i])
        else:
            li.append(0)
    return li

# SVM format
test_data = sys.argv[1]
rf_model = sys.argv[2]
format = sys.argv[3]    # -svm or -csv
feature_number = int(sys.argv[4])

###############################
# STEP 1: Load model
###############################
print "Loading model..."
clf = joblib.load(rf_model)

###############################
# STEP 2: Score the candidates.
###############################
print "Predicting..."
fo = open('%s_predict_result.txt' % test_data.split('.')[0], 'w')
BATCH_SIZE = 10000
examples = []
predictions = []
count = 0
for line in open(test_data):
    if format == '-svm':
        example_features = svm_to_list(line, feature_number)
    elif format == '-csv':
        example_features = [float(feature) for feature in line.split(",")]

    examples.append(example_features)

    if len(examples) == BATCH_SIZE:
        count += BATCH_SIZE
        print count
        # Predictions: (accuracy(label1),accuracy(label2))
        # In our case, predictions[0] is positive
        predictions = clf.predict_proba(examples)
        for i in xrange(BATCH_SIZE):
            fo.write('%f\n' % predictions[i][0])
        examples = []
        predictions = []

predictions = clf.predict_proba(examples)
for i in xrange(len(predictions)):
    fo.write('%f\n' % predictions[i][0])
fo.close()