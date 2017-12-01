import requests
import time 



def newAccount(): 
	url = 'http://localhost:8080/school/webapi/admin/account'
	headers = {'content-type': 'application/json'}
	data = ''
	resp = requests.post(url, data=data, auth=('comPlex', 'reSt*@!Api'),headers=headers)
	response = str(resp.content)
	return response


def newStaff(): 
	url = 'http://localhost:8080/school/webapi/staff/b83e9b89-0d52-4191-a6bf-acf501267e2e1'
	headers = {'content-type': 'application/json'}
	data = ''
	resp = requests.post(url, data=data, auth=('demo', '12345678'),headers=headers)
	response = str(resp.content)
	return response




print newAccount()
time.sleep(1) 
print newStaff() 

