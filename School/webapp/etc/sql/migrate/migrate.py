import requests
import time 
import json
from pprint import pprint
import psycopg2



accountId = ''

def psqlConnect():
	try:
		conn = psycopg2.connect(database='schooldb',user='school',host='localhost',password='AllaManO1',port='5432')   
		return conn  
	except:
		print "Unable to connect to the database" 

#Important 
def importAccount(conn):
	try:
		cur = conn.cursor()
		cur.execute("select * from Account")
		results = cur.fetchall() 
		cur.close()	
	except psycopg2.Error as e:	
		print e 
	return results   



def addApiCredential():
	print 'addApiCredential' 


#Important 
def addCategory():
	print 'addCategory' 




#Important 
def fixSubject():	
	print 'fixSubject' 





#Important 
def addsubCategory():	
	print 'addsubCategory'  




#Important 
def addStream():
	print 'addStream'  





#Important 
def fixStudent():
	print 'fixStudent'   





#Important 
def fixStudentSubject():
	print 'fixStudentSubject'   






def fixHouse():
	print 'fixHouse'   

def fixStudentHouse():
	print 'fixStudentHouse'   






#Important 
def fixStudentParent():
	print 'fixStudentParent'   




#Important 
def fixStudentPrimary():
	print 'fixStudentPrimary'  





#Important 
def addAcessLevel():
	print 'addAcessLevel'  






#Important 
def fixStaff():	
	print 'fixStaff' 






#Important 
def fixClassTeacher():
	print 'fixClassTeacher' 





#Important 
def fixExam():
	print 'fixExam'  




# VERY Important 
def addPerformance():
	print 'addPerformance'  



#Important 
def fixGradingSystem():
	print 'fixGradingSystem'  



#Important 
def addsysConfig():
	print 'addsysConfig'  




#Important 
def fixPocketMoney():
	print 'fixPocketMoney'




#Important 
def fixDeposit():
	print 'fixDeposit'




#Important 
def fixWithdraw():
	print 'fixWithdraw'



#Important 
def fixTermFee():
	print 'fixTermFee'




#Important 
def addOtherFee():
	print 'addOtherFee'		




#Important 
def addStudentOtherFee():
	print 'addStudentOtherFee'		




#Important 
def addRevertedMoney():
	print 'addRevertedMoney'	



#Important 
def addStudentFee():
	print 'addStudentFee'



#Important 
def fixMiscellanous():
	print 'fixMiscellanous'















# pip install psycopg2
# pip install psycopg2-binary 
#print 'Conn: ', psqlConnect()
accounts = importAccount(psqlConnect()) 
for account in accounts:
	print account 



