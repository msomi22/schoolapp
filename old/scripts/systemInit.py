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
	url = 'http://localhost:8080/school/webapi/staff/ca4eab8a-b9f5-428c-b9e6-2cbe0d582f70'
	headers = {'content-type': 'application/json'}
	data = json.load(open('staff.json')) 
	json_string = json.dumps(data)
	#print json_string
	resp = requests.post(url, data=json_string, auth=('demo', '12345678'),headers=headers)
	response = str(resp.content)
	return response





print newAccount()
#print newStaff() 




