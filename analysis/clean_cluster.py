from work.jsonify import jsonify
import time
import csv
from datetime import datetime
from feature_eng import feature_eng

def to_localtime(utc_timestamp):
    """Convert utc timestamp to local timestamp.
    :param datetime utc_timestamp: Timestamp from utc
    :return: Local timestamp.
    """
    offset = datetime.now().timestamp() - datetime.utcnow().timestamp()
    #print(offset)
    return utc_timestamp + round(offset)

def countEvil(data, filter, id, danger):
    """Count the number of paths in appID that are malicious, ads, tracking, ads and tracking, Porn
    :param data: list of flows
    :param filter: list of malicious paths from filter
    :param id: appId
    :param danger: either malicious, ads, tracking, ads and tracking or porn
    :return count: the number of paths that belong to either malicious, ads, tracking, ads and tracking, or porn
    :return total: the total number of paths in an appID
    """
    count = 0
    total = 0
    for d in data:
        if d['appID'] == id:
            total += 1
            path = d['request']['path']
            if filter[path][danger] == True:
                count += 1
    return count, total

def countTime(reader, id):
    """Get the starting simulation time and ending simulation time for each appID
    :param reader: the txt file as array
    :param id: appID
    :return new_start: the starting simulation time for an appId
    :return new_end: the ending simulation time for an appID
    """
    for i, line in enumerate(reader):
        li = line.replace('\n', '').split(' ')
        if li[1] == 'start':
            url = li[0].split('/')[-1]
            if id == url:
                new_line = reader[i + 1].replace('\n', '').split(' ')
                break
    start_date = li[-1].split('.')[0]
    end_date = new_line[-1].split('.')[0]
    new_start = to_localtime(time.mktime(time.strptime(start_date, '%Y-%m-%dT%H:%M:%S')))
    new_end = to_localtime(time.mktime(time.strptime(end_date, '%Y-%m-%dT%H:%M:%S')))
    return new_start, new_end

def countSize(data, id):
    """Get the total content-length for an app. This value is the combination of header field 'content-length' (both request and response)
    :param data: flows data
    :param id: appId
    :return new_total: total content-length of appId
    """

    n = 0
    total = 0
    for da in data:
        if da['appID'] == id:
            try:
                size1 = da['request']['message']['headers']['content-length']
                n += 1
                total += int(size1)
            except KeyError:
                #print('no req content-length')
                n += 1
                continue
            try:
                size2 = da['response']['message']['headers']['content-length']
                n += 1
                total += int(size2)
            except KeyError:
                #print('no res content-length')
                n += 1
                continue
    if n != 0:
        new_total = total/n
    else:
        new_total = 0
    return new_total

lin = ['1-20', '21-40', '41-60', '61-80', '81-100', '101-120', '121-140', '141-160', '161-180', '181-200', '201-220']

app_num = 0

"""Cleaning the data and categorizing data into apps
   Then, write the data down into csv file
"""

thing = open('cleaned_cluster.csv', 'w')
writer = csv.writer(thing)
writer.writerow(['appID', 'Malicious Percentage', 'Ads Percentage', 'Tracking Percentage', 'Bitcoin Percentage', 'Adult Percentage', 'Session Rate', 'Packet Size', 'User Permission Count'])
permission = jsonify.import_json('permission.json')
for text in lin:
    lol = jsonify.import_json('time_cleaned/time_cleaned'+text+'.prxs')
    filter = jsonify.import_json('filterChecked_nn'+text+'.json')
    dictionary = {}
    count = 0 #How many are evil
    for path in lol['app']:
        dictionary[path] = {
            'maliciousPercent': 0,
            'adsPercent': 0,
            'trackingPercent': 0,
            'bitcoinPercent': 0,
            'adultPercent': 0,
            'sessionRate': 0,
            'packetSize': 0,
            'upermissionCount': 0
        }
    app_num += len(dictionary)
    data = lol['flows']
    for d in data:
        d = feature_eng.cleanHeader(d)

    lol = open('log/log'+text+'.txt', 'r')
    reader = lol.readlines()

    for id in dictionary:
        countMali, tot = countEvil(data, filter, id, 'Malicious')
        countAds, tot = countEvil(data, filter, id, 'Ads')
        countTracking, tot = countEvil(data, filter, id, 'Tracking')
        countBitcoin, tot = countEvil(data, filter, id, 'Bitcoin')
        countAdult, tot = countEvil(data, filter, id, 'Adult')

        if tot > 0:
            dictionary[id]['maliciousPercent'] = countMali/tot
            dictionary[id]['adsPercent'] = countAds/tot
            dictionary[id]['trackingPercent'] = countTracking/tot
            dictionary[id]['bitcoinPercent'] = countBitcoin/tot
            dictionary[id]['adultPercent'] = countAdult/tot
        else:
            print(id + ' is so damn good.')

        new_start, new_end = countTime(reader, id)
        diff = new_end - new_start
        dictionary[id]['sessionRate'] = tot/diff

        dictionary[id]['packetSize'] = countSize(data, id)
        nu = 0
        useper_count = 0
        #per_count = 0
        for per in permission['data']:
            nus = str(nu)
            if per[nus]['package']+'.apk' == id:
                useper_count = len(per[nus]['uses-permission'])
                print(useper_count)
                #per_count = len(per['uses-permission'])
            nu += 1

        dictionary[id]['upermissionCount'] = useper_count



    for i in dictionary:
        writer.writerow([i, dictionary[i]['maliciousPercent'], dictionary[i]['adsPercent'], dictionary[i]['trackingPercent'], dictionary[i]['bitcoinPercent'], dictionary[i]['adultPercent'], dictionary[i]['sessionRate'], dictionary[i]['packetSize'], dictionary[i]['upermissionCount']])
        #app_num += 1

thing.close()
print('number of apps: '+str(app_num))
