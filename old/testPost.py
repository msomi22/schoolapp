import requests


url = 'http://localhost:8080/school/webapi/staff/5498156A-FE83-43F4-9592-737HDHJ877S/subjects'
headers = {'content-type': 'application/json'}
data = '{\"teacherId\": \"5498156A-FE83-43F4-9592-737HDHJ877S\", \"subjectId\":\"F1972BF2-C788-4F41-94FE-FBA1869C92BC\",\"streamId\":\"D3733507-C113-4795-91ED-D3CD8039EA03\", \"accountId\":\"E3CDC578-37BA-4CDB-B150-DAB0409270CD\"}'

resp = requests.post(url, data=data, auth=('demo', '12345678'),headers=headers)
response = str(resp.content)

print response
