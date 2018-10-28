set CD="%~dp0"
cd OutputSql

set My_HOME=C:\Program Files\MySQL\MySQL Workbench 6.3 CE
set CLASSPATH=.;My_HOME%
set PATH=%My_HOME%;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;%ANT_HOME%\lib


mysql.exe -rtcps -pfoo@bar201 -Dtcps<adjustHaveValue.sql
mysql.exe -rtcps -pfoo@bar201 -Dtcps<adjustIcashOneDay.sql
mysql.exe -rtcps -pfoo@bar201 -Dtcps<adjustTash.sql
mysql.exe -rtcps -pfoo@bar201 -Dtcps<adjustTransfer.sql

pause