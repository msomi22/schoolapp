@echo off
setlocal
set PGPASSWORD = 
"C:\Program Files\PostgreSQL\9.3\bin\psql.exe" -h localhost -U postgres  -f C:\home\%username%\school\win_postgres.sql
pause 
endlocal