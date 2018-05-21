import json
from datetime import datetime
import time

"""Categorize sessions into each application based on the time.
    This time is checked against the log file.
    If the session does not belong to any apps simulation duration, it is discarded.
"""

'''978307200 <- difference in value between timestamp in proxie an real world timestamp
    If proxie is not used, then do not use this number.
'''

def import_json(filename):
    """Importing json file
    :param filename: the json file name
    :return raw_data: json file body
    """
    with open(filename, 'r') as ff:
        raw_data = json.load(ff)
        ff.close()
    return raw_data

def to_localtime(utc_timestamp):
    """Convert utc timestamp to local timestamp.
    :param datetime utc_timestamp: Timestamp from utc
    :return: Local timestamp.
    """
    offset = datetime.now().timestamp() - datetime.utcnow().timestamp()
    return utc_timestamp + round(offset)

thing = ['1-20', '21-40', '41-60', '61-80', '81-100', '101-120', '121-140', '141-160', '161-180', '181-200', '201-220']
total = 0
for text in thing:
    data = import_json('test'+text+'.prxs')
    lol = open('log/log'+text+'.txt', 'r')
    reader = lol.readlines()
    extracted = data['flows'].copy()
    j = {}
    j['app'] = []
    j['flows'] = []
    with open('time_cleaned/time_cleaned'+text+'.prxs', 'w') as ff:
        num = 0
        for i, line in enumerate(reader):
            li = line.replace('\n', '').split(' ')
            if li[1] == 'start':
                url = li[0].split('/')[-1]
                j['app'].append(url)
                start_date = li[-1].split('.')[0]
                new_start = to_localtime(time.mktime(time.strptime(start_date, '%Y-%m-%dT%H:%M:%S'))) #- 978307200
                new_line = reader[i + 1].replace('\n', '').split(' ')
                if li[0] == new_line[0]:
                    num = num + 1
                    end_date = new_line[-1].split('.')[0]
                    new_end = to_localtime(time.mktime(time.strptime(end_date, '%Y-%m-%dT%H:%M:%S'))) #- 978307200
                    for ex in extracted:
                        if '127.0.0.1' not in ex['request']['path'] and 'chula' not in ex['request']['path']:
                            try:
                                req_time = int(ex['request']['endDate']) + 978307200
                            except KeyError:
                                req_time = ''
                            if req_time != '':
                                if req_time >= new_start and req_time <= new_end:
                                    temp = {}
                                    temp['appID'] = url
                                    temp['connectionInfo'] = ex['connectionInfo']
                                    temp['request'] = ex['request']
                                    try:
                                        temp['response'] = ex['response']
                                    except KeyError:
                                        print('no response')
                                    j['flows'].append(temp)
        json.dump(j, ff)
        print(num)
        total += num
        print(len(j['flows']))
        print(len(extracted))
        ff.close()
"""Print out the total number of apps
"""
print(total)
