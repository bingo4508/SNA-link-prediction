__author__ = 'bingo4508'

from sklearn.ensemble import RandomForestClassifier
from sklearn.externals import joblib
import sys

# The first column in this file is the truth of a (src, dest) edge
# (i.e., 1 if the edge is known to exist, 0 otherwise).
# The rest of the columns are features on that edge.
train_data = sys.argv[1]

trees = int(sys.argv[2])

########################################
# STEP 1: Read in the training examples.
########################################
truths = []
training_examples = []
for line in open(train_data):
    fields = [float(x) for x in line.split(",")]
    truth = fields[0]
    training_example_features = fields[1:]

    truths.append(truth)
    training_examples.append(training_example_features)

#############################
# STEP 2: Train a classifier.
#############################
clf = RandomForestClassifier(n_estimators=trees, oob_score=True)
clf = clf.fit(training_examples, truths)

#############################
# STEP 3: Save classifier.
#############################
joblib.dump(clf, '%s_%dtrees.rf' % (train_data.split('.')[0], trees))