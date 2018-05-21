from feature_eng import feature_eng
from work.jsonify import jsonify
from work.csvnify import csvnify
import csv
import re

"""Feature engineering the header fields and body into 1s and 0s
    Commented lines are depricated header fields (not used in analysis)
"""

"""Threshold for each field is put here
"""
threshold = {
    'reqContentLength': 200,
    'reqContentType': ['application', 'text'],
    'reqMethod': 'POST',
    'front': ['application', 'text', 'multipart', 'image'],
    'back': ['json', 'x-gzip', 'text', 'octet-stream', 'x-www-form-urlencoded', 'plain', 'html', 'javascript']
}

"""Return the index for the path in the data dictionary
    :param data: dictionary
    :param path: the path for each session
    :return i: index of the session that has the path
"""
def searchFor(data, path):
    for i, session in enumerate(data):
        if path in session['request']['path']:
            return i

thing = ['1-20', '21-40', '41-60', '61-80', '81-100', '101-120', '121-140', '141-160', '161-180', '181-200']

for th in thing:
    data = jsonify.import_json_proxie('time_cleaned/time_cleaned'+th+'.prxs')
    filter = jsonify.import_json('filterChecked_nn'+th+'.json')
    ff = open('cleaned_nnnn/cleaned_nnnn'+th+'.csv', 'w')
    writer = csv.writer(ff)
    connection = 0

    """Always check for KeyError, since each http sessions does not have staple header fields"""
    for session in data:
        if 'response' in session:
            session = feature_eng.cleanHeader(session)
            array = []
            try:
                """Clean path"""
                array = feature_eng.cleanPath(session['request']['path'], filter, array)
            except KeyError:
                for i in range(6):
                    array.append(1)

            try:
                '''Clean Request content length'''
                array = feature_eng.cleanContentLength(session['request']['message']['headers']['content-length'], threshold['reqContentLength'], array)
            except KeyError:
                array.append(0)

            # try:
            #     '''Clean request content type'''
            #     array = feature_eng.cleanContentType(session['request']['message']['headers']['content-type'], threshold['reqContentType'], array)
            # except KeyError:
            #     array.append(0)

            try:
                '''Clean Request Content-Type (front and back)'''
                array = feature_eng.cleanTypeFront(session['request']['message']['headers']['content-type'], threshold['front'], array)
                array = feature_eng.cleanTypeBack(session['request']['message']['headers']['content-type'], threshold['back'], array)
            except KeyError:
                for i in range(12):
                    array.append(0)


            # try:
            #     array = feature_eng.cleanTypeFrontBack(session['request']['message']['headers']['content-type'], threshold['back'], array)
            # except KeyError:
            #     array.append(0)

            try:
                '''Clean request method'''
                array = feature_eng.cleanMethod(session['request']['method'], threshold['reqMethod'], array)
            except KeyError:
                array.append(0)

            try:
                '''Clean response content length'''
                array = feature_eng.cleanContentLength(session['response']['message']['headers']['content-length'], threshold['reqContentLength'], array)
            except KeyError:
                array.append(0)
            #
            # try:
            #     '''Clean response content type'''
            #     array = feature_eng.cleanContentType(session['response']['message']['headers']['content-type'], threshold['reqContentType'], array)
            # except KeyError:
            #     array.append(0)

            try:
                ''''''
                array = feature_eng.cleanTypeFront(session['response']['message']['headers']['content-type'], threshold['front'], array)
                array = feature_eng.cleanTypeBack(session['response']['message']['headers']['content-type'], threshold['back'], array)
                #array = feature_eng.cleanTypeFrontBack(session['response']['message']['headers']['content-type'], threshold['front'], threshold['back'], array)
            except KeyError:
                for i in range(12):
                    array.append(0)


            # try:
            #     array = feature_eng.cleanTypeFrontBack(session['response']['message']['headers']['content-type'], threshold['back'], array)
            # except KeyError:
            #     array.append(0)

            # try:
            #     """Clean alt-svc by checking if it exists in header"""
            #     array = feature_eng.cleanAltSvc(session['response']['message']['headers'], array)
            # except KeyError:
            #     array.append(0)

            # try:
            #     """Clean x-content-type-options by checking if it exists in header"""
            #     array = feature_eng.cleanXContentTypeOptions(session['response']['message']['headers'], array)
            # except KeyError:
            #     array.append(1)
            #
            # try:
            #     """Clean x-xss-protection by checking if it exists in header"""
            #     array = feature_eng.cleanXXSSProtection(session['response']['message']['headers'], array)
            # except KeyError:
            #     array.append(1)

            try:
                """Clean accept"""
                array = feature_eng.cleanAccept(session['request']['message']['headers'], array)
            except KeyError:
                array.append(0)

            # try:
            #     """Clean referrer by checking if it exists in header"""
            #     array = feature_eng.cleanReferrer(session['response']['message']['headers'], array)
            # except KeyError:
            #     array.append(1)
            #
            # try:
            #     """Clean strict-transport-security by checking if it exists in header"""
            #     array = feature_eng.cleanStrictTransportSecurity(session['response']['message']['headers'], array)
            # except KeyError:
            #     array.append(1)

            # try:
            #     '''Clean Access-Control-Allow-Methods'''
            #     array = feature_eng.cleanAccessControlAllowMethods(session['response']['message']['headers'], array)
            # except KeyError:
            #     array.append(0)

            try:
                '''Clean Access-Control-Allow-Origin'''
                array = feature_eng.cleanAccessControlAllowOrigin(session['response']['message']['headers'], array)
            except KeyError:
                array.append(0)

            # try:
            #     '''Clean Content-Encoding'''
            #     array = feature_eng.cleanContentEncoding(session['response']['message']['headers'], array)
            # except KeyError:
            #     array.append(0)

            # try:
            #       """Clean request body"""
            #     date = session['request']['message']['headers']['date']
            #     body = feature_eng.getTCPBody(session, th, date)
            #     '''Clean TCP body'''
            #     if body != '':
            #         array.append(feature_eng.searchBody(body))
            #     else:
            #         array.append(0)
            # except KeyError:
            #     array.append(0)

            try:
                """Clean response body"""
                date = session['response']['message']['headers']['date']
                body = feature_eng.getTCPBody(session, th, date)
                '''Clean TCP body'''
                if body != '':
                    array.append(feature_eng.searchBody(body))
                else:
                    array.append(0)
            except KeyError:
                array.append(0)

            writer.writerow(array)
    ff.close()
    print(len(array))
