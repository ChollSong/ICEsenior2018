import numpy as np
import pandas as pd
from sklearn.naive_bayes import GaussianNB
import random
from sklearn import datasets, linear_model
from sklearn.model_selection import train_test_split
from matplotlib import pyplot as plt
from sklearn.model_selection import KFold
from sklearn.metrics import accuracy_score
from sklearn.metrics import precision_score

"""Compare between the theoretical result with the actual result
    Check the amount of true positive, false positive, true negative, false negative
    :param y_actual: actual results
    :param y_hat: theoretical results
    :return (TP, FP, TN, FN): True positive, false positive, true negative, and false negative
"""
def perf_measure(y_actual, y_hat):
    TP = 0
    FP = 0
    TN = 0
    FN = 0

    for i in range(len(y_hat)):
        if y_actual[i]==y_hat[i]==1:
           TP += 1
        if y_hat[i]==1 and y_actual[i]!=y_hat[i]:
           FP += 1
        if y_actual[i]==y_hat[i]==0:
           TN += 1
        if y_hat[i]==0 and y_actual[i]!=y_hat[i]:
           FN += 1

    return(TP, FP, TN, FN)

"""Using test/train split cross validation
    :param X: trained datasets
    :param Y: theoretical datasets
    :param size: the test size of the datasets
    :return score: the accuracy of the model
"""
def testSplit(X, Y, size):
    model = GaussianNB()
    X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size = size)

    model.fit(X_train, Y_train)
    predicted = model.predict(X_test)
    return model.score(X_test, Y_test)

raw_data = np.genfromtxt('cleaned_combined_m.csv', delimiter=',', skip_header=1)

tempX = []
tempY = [[], [], []]

for row in raw_data:
    temp = []
    '''Request Content Length'''
    temp.append(row[6])
    '''Request Content Type'''
    for i in range(7, 19):
        if i == 7 or i == 15:
            temp.append(row[i])
    '''Request Method'''
    temp.append(row[19])
    '''Response Content Length'''
    temp.append(row[20])
    '''Response Content Type'''
    for i in range(21, 33):
        if i == 21 or i == 25:
            temp.append(row[i])
    '''Accept'''
    temp.append(row[33])
    '''Access-Control-Allow-Origin'''
    temp.append(row[34])
    '''TCP body'''
    temp.append(row[35])

    temp1 = []
    '''Request Content Length'''
    temp1.append(row[6])
    '''Request Content Type'''
    for i in range(7, 19):
        if i == 7 or i == 10 or i == 11 or i == 16:
            temp1.append(row[i])
    '''Request Method'''
    temp1.append(row[19])
    '''Response Content Length'''
    temp1.append(row[20])
    '''Response Content Type'''
    for i in range(21, 33):
        if i == 21 or i == 24 or i == 25 or i == 30:
            temp1.append(row[i])
    '''Accept'''
    temp1.append(row[33])
    '''Access-Control-Allow-Origin'''
    temp1.append(row[34])
    '''TCP body'''
    temp1.append(row[35])

    tempX.append(temp)

    '''Ads and tracking'''
    if row[0] == 1 or row[1] == 1 or row[3] == 1:
        tempY[0].append(1)
    else:
        tempY[0].append(0)
    '''Malicious'''
    tempY[1].append(row[2])
    '''Pornography'''
    tempY[2].append(row[5])
    '''There is no paths that belongs to bitcoin, so bitcoin is not considered'''

'''kfold cross validation for each type of maliciousness: ads and tracking, malicious, and pornogrpahy'''
X = np.array(tempX)
for YY in tempY:
    print('-----------------------------------------')
    Y = np.array(YY)
    score = testSplit(X, Y, 0.2)

    num = 10 #Change this

    kf = KFold(n_splits=num)
    kf.get_n_splits(X)

    m = GaussianNB()

    array = []
    array.append(['TP', 'FP', 'TN', 'FN'])
    total = 0
    for train_index, test_index in kf.split(X):
        sth_1 = 0
        sth_0 = 0
        print("TRAIN:", train_index, "TEST:", test_index)
        x_train, x_test = X[train_index], X[test_index]
        y_train, y_test = Y[train_index], Y[test_index]
        for yy in y_test:
            if yy == 1:
                sth_1 += 1
            elif yy == 0:
                sth_0 += 1
            else:
                print('error')
        print('# 1', '# 0')
        print(sth_1, sth_0)
        m.fit(x_train, y_train)
        predicted2 = m.predict(x_test)
        ss = m.score(x_test, y_test)
        total += ss
        array.append(perf_measure(predicted2, y_test))

    print(total/num)
    print(array)
