@echo off
REM Database Connection Test Script
REM This script tests the MySQL database connection

echo ========================================
echo  DATABASE CONNECTION TEST
echo ========================================
echo.

echo Testing MySQL Service Status...
powershell -Command "Get-Service -Name MySQL* | Select-Object Name, Status, DisplayName"
echo.

echo Loading database settings from config.properties...
setlocal ENABLEDELAYEDEXPANSION

REM Parse config.properties for db.url, db.username, db.password (ignore commented lines)
for /f "usebackq tokens=1,* delims==" %%A in (`findstr /R "^[ ]*db\.url[ ]*=[ ]*.* ^[ ]*db\.username[ ]*=[ ]*.* ^[ ]*db\.password[ ]*=[ ]*.*" config.properties`) do (
	set "key=%%A"
	set "val=%%B"
	for /f "tokens=*" %%x in ("!val!") do set "!key!=%%x"
)

REM Extract DB name from JDBC URL in pure batch
set "DB_URL=!db.url!"
set "DB_NAME=!DB_URL!"
:splitloop
for /F "tokens=1* delims=/" %%a in ("!DB_NAME!") do (
	set "head=%%a"
	set "tail=%%b"
)
if defined tail (
	set "DB_NAME=!tail!"
	set "tail="
	goto splitloop
)
for /F "tokens=1 delims=?" %%a in ("!DB_NAME!") do set "DB_NAME=%%a"
if "!DB_NAME!"=="" set "DB_NAME=inventory_db"
echo.
echo Using Connection:
echo   URL: !DB_URL!
echo   User: !db.username!
echo   Database: !DB_NAME!
echo.

echo Testing Database Connection...

REM Write SQL to a temporary file to avoid quoting issues
set "TMP_SQL=%TEMP%\ims_db_check.sql"
>"%TMP_SQL%" echo SELECT 'Connection Successful' as Status;
>>"%TMP_SQL%" echo SELECT VERSION^() as MySQL_Version;
>>"%TMP_SQL%" echo SELECT COUNT(^*) as Managers FROM managers;
>>"%TMP_SQL%" echo SELECT COUNT(^*) as Products FROM products;
>>"%TMP_SQL%" echo SELECT COUNT(^*) as Alerts FROM alerts;

REM Disable delayed expansion before invoking mysql to avoid ! issues
endlocal & cmd /c "mysql -u %db.username% -p%db.password% -D %DB_NAME% < "%TMP%\ims_db_check.sql""

echo.
echo ========================================
echo  TEST COMPLETE
echo ========================================
pause
