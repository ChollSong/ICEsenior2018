import os
import json
import requests
import csv

'''Download Android applications' APK

(One problem is that if it errors, then you will have to download the errored APK manually)
'''

with open('appData_5.csv', 'r') as data_file:
    data = csv.reader(data_file)
    f = open('cannotdownload.csv', 'a')
    writer = csv.writer(f)
    for app in data:
        appId = app[1]
        print(appId)
        try:
            os.system('gplaycli -d ' + appId + ' -f /Volumes/WEAB4U/APK --config /Volumes/WEAB4U/APK/gplaycli.conf')
        except:
            writer.writerow([app[0], app[1]])
            print('cannot load ' + app[1])
