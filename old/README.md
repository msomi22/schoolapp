# README #

AppleTech school Information System



### REST API GUIDE ###

* Install swagger-ui (Find online guides on how to install)
* Make sure you have curl installed
* Demo credentials are : username:demo password : 123456
* In base64 the credentials will be : ZGVtbzoxMjM0NTY3OA==

### Running swagger-ui with docker ###
 
* sudo docker run -p 81:8080 swaggerapi/swagger-ui
* swagger-ui will be available at localhost:81
* paste the below url in your browser
* http://localhost:8080/school/webapi/swagger.json
*
* Sample curl request

* ************************************************************************
curl -X GET "http://localhost:8080/school/webapi/staff/38EFA2D4-352D-4BC0-887F-9CA227950501/subjects/E3CDC578-37BA-4CDB-B150-DAB0409270CD" -H "accept: application/json" -H "authorization:Basic ZGVtbzoxMjM0NTY3OA=="

* ************************************************************************

