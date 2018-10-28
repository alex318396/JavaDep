set CD="%~dp0"
java -jar adjustIcash.jar

cd OutputSql
mysql.exe -rtcps -pfoo@bar201 -Dtcps <adjustHaveValue.sql
mysql.exe -rtcps -pfoo@bar201 -Dtcps <adjustIcashOneDay.sql
mysql.exe -rtcps -pfoo@bar201 -Dtcps <adjustTash.sql
mysql.exe -rtcps -pfoo@bar201 -Dtcps <adjustTransfer.sql

pause