awimport csv

#li = ['1-20', '21-40', '41-60', '61-80', '81-100', '101-120', '121-140', '161-180', '180-200']
'''Perform merging of feature engineered files'''

li = ['1-20', '21-40', '41-60', '61-80', '81-100', '101-120', '121-140', '141-160', '161-180', '181-200']

f1 = open('cleaned_combined_m.csv', 'w')
writer = csv.writer(f1)
writer.writerow(['Ads&Tracking', 'Ads', 'Malicious', 'Tracking', 'Bitcoin', 'Porn', 'Request Content-Length', 'RCT application', 'RCT text', 'RCT multipart', 'RCT image', 'RCTB json', 'RCTB x-gzip', 'RCTB text', 'RCTB octet-stream', 'RCTB x-www-form-urlencoded', 'RCTB plain', 'RCTB html', 'RCTB javascript', 'Method', 'Response Content-Length', 'R2CT application', 'R2CT text', 'R2CT multipart', 'R2CT image', 'R2CTB json', 'R2CTB x-gzip', 'R2CTB text', 'R2CTB octet-stream', 'R2CTB x-www-form-urlencoded', 'R2CTB plain', 'R2CTB html', 'R2CTB javascript', 'Accept', 'Access-Control-Allow-Origin', 'TCP body'])
#writer.writerow(['path', 'Request Content-Length', 'Request Content-Type', 'Method', 'Accept', 'Access-Control-Allow-Methods', 'Access-Control-Allow-Origin', 'Content-Encoding', 'TCP body'])

for l in li:
    print(l)
    ff = open('cleaned_nnnn/cleaned_nnnn'+l+'.csv', 'r')
    reader = csv.reader(ff)

    data = [x for x in reader]

    for d in data:
        writer.writerow(d)

    ff.close()

f1.close()
