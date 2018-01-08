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
	uuid = 'ceaf0dc9-016f-4e7b-8b26-c02c17f87ac0'
	url = 'http://localhost:8080/school/webapi/staff/'+uuid
	headers = {'content-type': 'application/json'}
	data = json.load(open('staff.json')) 
	json_string = json.dumps(data)
	#print json_string
	resp = requests.post(url, data=json_string, auth=('demo', '12345678'),headers=headers)
	response = str(resp.content)
	return response





#print newAccount()
#time.sleep(10)  
#print newStaff()
print 'Please modify me!' 




