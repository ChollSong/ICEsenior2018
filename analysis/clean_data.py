import json
import csv
from adblockparser import AdblockRules
from urllib.request import Request, urlopen
from work.jsonify import jsonify
from work.csvnify import csvnify

#thing = ['21-40', '41-60', '61-80', '81-100', '101-120', '121-140', '141-160']
thing = ['161-180', '181-200', '201-220']

"""Similar to clean_data_unique.py. Does not remove the parameters after ?"""

#ff = open('')
"""Create a dictionary for each path
"""
for th in thing:
    connection = jsonify.import_json_proxie('time_cleaned/time_cleaned'+th+'.prxs')
    filterlist = csvnify.import_csv('filterlist_category.csv')
    dictionary = {}
    for conn in connection:
        dictionary[conn['request']['path']] = {
            'Ads&tracking': False,
            'Ads': False,
            'Malicious': False,
            'Tracking': False,
            'Bitcoin': False,
            'Adult': False
        }

    array = dictionary.keys()
    print(th)
    filterr = ['Ads&tracking', 'Ads', 'Malicious', 'Tracking', 'Bitcoin', 'Adult']
    """Check each path against filter for maliciousness, ads, tracking, ads and tracking, bitcoin, or pornography.
        (number of n at the end file determines the version for the file. The more the number of n, the newer the version of the file)
    """
    for filt in filterlist[1:]:
        print('Start checking against: ' + filt[0] + ': ' + filt[1])
        if 'host' not in filt[1]:
            try:
                req = Request(filt[1], headers={'User-Agent': 'Mozilla/5.0'})
                raw_rules = urlopen(req).readlines()
                raw_rules2 = [x.decode('utf8') for x in raw_rules if x.decode('utf8') != '\r\n']
                raw_rules3 = []
                for raw in raw_rules2:
                    raw_rules3.append(raw.replace('\n', '').replace('\r', ''))
                rules = AdblockRules(raw_rules3)
            except KeyboardInterrupt:
                raise
            except:
                print('====cannot read filter====')
                raw_rules3 = ''
            if raw_rules3 != '':
                for path in array:
                    if rules.should_block(path) is True:
                        print(path + ' : Yes')
                        dictionary[path][filt[2]] = True
                    else:
                        print(path + ' : No')
            print('---------------------------------')

    jsonify.export_json('filterChecked_nn'+th+'.json', dictionary)
