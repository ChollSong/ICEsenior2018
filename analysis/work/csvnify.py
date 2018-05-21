import csv

class csvnify:
    def import_csv(filename):
        '''Import csv and convert to array

            Return array
        '''
        with open(filename, 'r') as ff:
            reader = csv.reader(ff)
            data = [x for x in reader]
            ff.close()
        return data

    def create_csv(filename, method):
        '''Create csv depending on the method

            Return writer object
        '''
        with open(filename, method) as ff:
            writer = csv.writer(ff)
            #ff.close()
            return writer

    def write_csv(filename, array):
        '''(Assuming that the method is w) Create csv and write array onto csv filename

            Return array
        '''
        if array == []:
            return []
        with open(filname, 'w') as ff:
            writer = csv,writer(ff)
            for arr in array:
                writer.writerow(arr)
            ff.close()
        return array
