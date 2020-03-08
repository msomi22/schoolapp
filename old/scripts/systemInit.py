import requests
import time 
import json
from pprint import pprint



def newAccount(): 
	url = 'http://localhost:8080/school/webapi/admin/account'
	headers = {'content-type': 'application/json'}
	data = json.load(open('account.json')) 
	json_string = json.dumps(data)
	resp = requests.post(url, data=json_string, auth=('comPlex', 'reSt*@!Api'),headers=headers)
	response = resp.json() 
	print 'response1 : ' , response
	if(response['message'] == 'success'): 
		u_name = json.loads(json_string)['username'] 
		newStaff(u_name)
	return response


def newStaff(uname):
	headers = {'content-type': 'application/json'}
	a_url = 'http://localhost:8080/school/webapi/admin/account/'+uname
	a_respo = requests.get(a_url, data={}, auth=('comPlex', 'reSt*@!Api'),headers=headers)
	account = a_respo.json() 
	uuid = account['uuid']
	url = 'http://localhost:8080/school/webapi/staff/'+str(uuid)
	data = json.load(open('staff.json')) 
	json_string = json.dumps(data)
	resp = requests.post(url, data=json_string, auth=('demo', '12345678'),headers=headers)
	response = str(resp.content)
	print 'response2 : ' , response  




newAccount()




