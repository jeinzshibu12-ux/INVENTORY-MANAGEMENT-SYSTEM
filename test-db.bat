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

echo Testing Database Connection...
mysql -u user -p1234 -e "SELECT 'Connection Successful!' as Status; SELECT VERSION() as MySQL_Version; USE inventory_db; SELECT COUNT(*) as Managers FROM managers; SELECT COUNT(*) as Products FROM products; SELECT COUNT(*) as Alerts FROM alerts;"

echo.
echo ========================================
echo  TEST COMPLETE
echo ========================================
pause
