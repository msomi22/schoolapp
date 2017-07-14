
@echo off
  cd C:\home\%username%\school\dbBackup
 for /f "tokens=1-6 delims=/ " %%i in ("%date%") do (
 	 set sec=%%g
 	 set min=%%h
     set dow=%%i
	 set month=%%j
	 set day=%%k
	 set year=%%l
	 )
	 set datestr=%month%_%day%_%year%
	 echo datestr is %datestr%
	 
	 set BACKUP_FILE=backup_%datestr%.sql
	 echo backup file is %BACKUP_FILE%
	 set PGPASSWORD=AllaManO1
	 echo on
	 "C:\Program Files\PostgreSQL\9.3\bin\pg_dump.exe" -i -h localhost -p 5432 -U school -f c -b -v -f %BACKUP_FILE% schooldb

