import requests
import time 
import json
from pprint import pprint



def newAccount(): 
	url = 'http://localhost:8080/school/webapi/admin/account'
	headers = {'content-type': 'application/json'}
	data = json.load(open('account.json')) 
	json_string = json.dumps(data)
	#print json_string
	resp = requests.post(url, data=json_string, auth=('comPlex', 'reSt*@!Api'),headers=headers)
	response = str(resp.content)
	return response


def newStaff():
	headers = {'content-type': 'application/json'}
	query = 'mangu'
	a_url = 'http://localhost:8080/school/webapi/admin/account/'+query
	a_respo = requests.get(a_url, data={}, auth=('comPlex', 'reSt*@!Api'),headers=headers)
	account = a_respo.json() 
	uuid = account['uuid']
	url = 'http://localhost:8080/school/webapi/staff/'+str(uuid)
	data = json.load(open('staff.json')) 
	json_string = json.dumps(data)
	#print json_string
	resp = requests.post(url, data=json_string, auth=('demo', '12345678'),headers=headers)
	response = str(resp.content)
	return response





#print 'response : ' , newAccount()
print newStaff() 




