import csv
from  work.csvnify import csvnify
import re
import os
from pathlib import Path

class feature_eng:
    '''Normally, if you are going to change anything, change the threshold valueself.
    '''
    def cleanHeader(session):
        '''Clean header so that it is now like:
            Field: value

            instead of:
             field: sth
             value: sth
        '''
        arr_req = {}
        arr_res = {}
        try:
            temp = session['request']['message']['headers'].copy()
            for header in temp:
                arr_req[header['field'].lower()] = header['value']
            session['request']['message']['headers'] = arr_req
        except KeyError:
            lelelel = ''
        try:
            temp2 = session['response']['message']['headers'].copy()
            for header in temp2:
                arr_res[header['field'].lower()] = header['value']
            session['response']['message']['headers'] = arr_res
        except KeyError:
            lelelelel = ''
        return session

    def cleanPort(port, thresh, array):
        '''Cleaning Port into 0 and 1
            Change the threshold value

            Probably will be depricated
        '''
        threshold = thresh

        if int(port) > threshold:
            array.append(1)
        else:
            array.append(0)
        return array

    def cleanContentLength(length, thresh, array):
        '''Clean content-length into 0 and 1

            Change the threshold value
        '''
        threshold = thresh

        if int(length) > threshold:
            array.append(1)
        else:
            array.append(0)
        return array

    def cleanTypeFront(type, tresh1, array):
        '''Clean content type to 0 and 1.

            Differentiate between front and back
        '''
        if ';' in type:
            content = type.split(';')[0].lower()
        else:
            content = type.lower()
        typu = content.split('/')
        #print(typu[0])
        for t in tresh1:
            if typu[0] == t:
                #print(typu[0], t)
                array.append(1)
            else:
                array.append(0)
        return array

    def cleanTypeBack(type, tresh1, array):
        '''Clean content type to 0 and 1.

            Differentiate between front and back
        '''
        if ';' in type:
            content = type.split(';')[0].lower()
        else:
            content = type.lower()
        typu = content.split('/')
        #print(typu[1])
        for t in tresh1:
            if typu[1] == t:
                array.append(1)
            else:
                array.append(0)
        return array

    def cleanContentType(type, thresh, array):
        '''Clean content-type into 0 and 1

        '''
        threshold = thresh

        for thres in threshold:
            if thres in type:
                array.append(1)
                return array
        array.append(0)
        return array

    def cleanMethod(method, thresh, array):
        '''Clean method into 0 if GET and 1 if POST
        '''

        threshold = thresh

        if method == threshold:
            array.append(1)
        else:
            array.append(0)
        return array

    def cleanPath(path, dictionary, array):
        '''Clean path depending on its type of maliciousness
        '''

        mali = dictionary[path]
        #print(mali)
        listo = ['Ads&tracking', 'Ads', 'Malicious', 'Tracking', 'Bitcoin', 'Adult']

        for ma in listo:
            if mali[ma] == True:
                array.append(1)
            else:
                array.append(0)
        return array

    def cleanAltSvc(headers, array):
        '''Clean AltSvc
            Currently not used
        '''

        if 'alt-svc' in headers:
            array.append(1)
        else:
            array.append(0)
        return array

    def cleanXContentTypeOptions(headers, array):
        """Currently not used
        """

        if 'x-content-type-options' not in headers:
            array.append(1)
        else:
            array.append(0)
        return array

    def cleanXXSSProtection(headers, array):
        """Currently not used
        """
        if 'x-xss-protection' not in headers:
            array.append(1)
        else:
            array.append(0)
        return array

    def cleanAccept(headers, array):
        """if accept field value is '*/*', then return 1. Otherwise, return 0"""
        if 'accept' in headers:
            if '*/*' in headers['accept']:
                array.append(1)
                return array
        array.append(0)
        return array

    def cleanReferrer(headers, array):
        """If the path has referer, return 1. Else, return 0
        """
        if 'referer' not in headers:
            array.append(1)
        else:
            array.append(0)
        return array

    def cleanStrictTransportSecurity(headers, array):
        """Not used
        """
        if 'strict-transport-security' not in headers:
            array.append(1)
        else:
            array.append(0)
        return array

    def cleanAccessControlAllowMethods(headers, array):
        """Not used
        """
        if 'access-control-allow-methods' in headers:
            if 'GET,POST' in headers['access-control-allow-origin']:
                array.append(1)
                return array
        array.append(0)
        return array

    def cleanAccessControlAllowOrigin(headers, array):
        """Return 1 if field value is '*'
        Otherwise, return 0
        """
        if 'access-control-allow-origin' in headers:
            if '*' in headers['access-control-allow-origin']:
                array.append(1)
                return array
        array.append(0)
        return array

    def cleanContentEncoding(headers, array):
        """not used"""
        if 'content-encoding' in headers:
            if 'gzip' in headers['content-encoding']:
                array.append(1)
                return array
        array.append(0)
        return array

    def searchBody(body):
        """Currently set to: if any words listed below is in the text, automatically return 1. Else, return 0
        """
        m = re.search('IMEI|MAC|Password|password|Email|email|credit|Credit|email|Email|Phone|phone|jdcburner|burnerice2018|John|Doe|Cena|Birth|PIN|Device|address|devicename|device_name|bank', body)
        if(m != None):
            return 1
        else:
            return 0
    """Getting the body text from the raw TCP file.
    """
    def getTCPBody(session, dir, date):
        path = session['request']['path']
        date = session['response']['message']['headers']['date']
        s_path = path.split('?')[0].replace('%20', ' ').replace('%3', '=').replace('%26', '&')
        obj = re.search('http(\w|):\/\/([^\n]+)\/[^\n]+', s_path)
        tcp = ''
        if obj:
            str_path = obj.group(2)
        else:
            obj = re.search('http(\w|):\/\/([^\n]+)', s_path)
            if obj:
                str_path = obj.group(2)
            else:
                print(s_path)
                return tcp
        directory_in_str = 'raw_TCP/test' + dir + '/' + str_path
        directory_in_str = directory_in_str.replace(':', '_')
        my_file = Path(directory_in_str)
        try:
            if my_file.is_dir():
                for entry in os.scandir(my_file):
                    if entry.path.endswith('.txt'):
                        f = open(entry.path, 'r', encoding='latin-1')
                        temp = f.read()
                        if date in temp:
                            print(entry.path)
                            tcp = temp
                            return tcp
            else:
                print(directory_in_str)
        except OSError:
            print(directory_in_str)
            return tcp
        return tcp
