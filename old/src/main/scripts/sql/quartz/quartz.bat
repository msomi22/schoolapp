@echo off
setlocal
set PGPASSWORD = 
"C:\Program Files\PostgreSQL\9.3\bin\psql.exe" -h localhost -U postgres  -f C:\home\%username%\school\quartz_jdbc_store.sql 
pause 
endlocal