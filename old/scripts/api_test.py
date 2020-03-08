import requests



def testPost(): 
	url = 'http://localhost:8080/school/webapi/staff/5498156A-FE83-43F4-9592-737HDHJ877S/subjects'
	headers = {'content-type': 'application/json'}
	data = '{\"teacherId\": \"5498156A-FE83-43F4-9592-737HDHJ877S\", \"subjectId\":\"F1972BF2-C788-4F41-94FE-FBA1869C92BC\",\"streamId\":\"D3733507-C113-4795-91ED-D3CD8039EA03\", \"accountId\":\"E3CDC578-37BA-4CDB-B150-DAB0409270CD\"}'
	resp = requests.post(url, data=data, auth=('demo', '12345678'),headers=headers)
	response = str(resp.content)
	return response


def testGet():
	account = 'E3CDC578-37BA-4CDB-B150-DAB0409270CD'
	uuid = '1EABC062-DC76-42FC-A817-52D89C8CDAE9'
	url = 'http://localhost:8080/school/webapi/config/scale/'+account+'/'+uuid 
	headers = {'content-type': 'application/json'}
	resp = requests.get(url, data={}, auth=('demo', '12345678'),headers=headers)
	response = str(resp.content)
	return response


def testGet2():
	account = 'E3CDC578-37BA-4CDB-B150-DAB0409270CD'
	cat = '55DD5463-6ECB-48A3-B6E7-03548A9E37FE'
	url = 'http://localhost:8080/school/webapi/config/scale/cat/'+account+'/'+cat
	headers = {'content-type': 'application/json'}
	resp = requests.get(url, data={}, auth=('demo', '12345678'),headers=headers)
	response = str(resp.content)
	return response


#print testPost()
#print testGet()
print testGet2() 

