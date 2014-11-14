__author__ = 'bingo4508'

import sys

from sklearn.ensemble import RandomForestClassifier
from sklearn.externals import joblib

# This file contains candidate edge pairs to score, along with
# features on these candidate edges.
#
# The first column is the src node, the second is the dest node,
# the rest of the columns are features.
test_data = sys.argv[1]

rf_model = sys.argv[2]

###############################
# STEP 1: Load model
###############################
clf = joblib.load(rf_model)

###############################
# STEP 2: Score the candidates.
###############################
fo = open('%s_predict_result.csv' % test_data.split('.')[0], 'w')
BATCH_SIZE = 10000
examples = []
predictions = []
for line in open(test_data):
    example_features = [float(feature) for feature in line.split(",")]
    examples.append(example_features)

    if len(examples) == BATCH_SIZE:
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