import json

class jsonify:

    def import_json(filename):
        '''Importing file data and converting to json object

            Return json object
        '''
        with open(filename, 'r') as ff:
            raw_data = json.load(ff)
            ff.close()
        return raw_data

    def export_json(filename, data):
        '''Exporting json object to file with name "filename"

            Return json object
        '''
        if data == '':
            return ''
        with open(filename, 'w') as ff:
            json.dump(data, ff)
            ff.close()
        return data

    def import_json_proxie(filename):
        '''Importing proxie json file data and converting to json object

            Return json object with out flows
        '''
        with open(filename, 'r') as ff:
            raw_data = json.load(ff)
            data = raw_data['flows']
            ff.close()
        return data
