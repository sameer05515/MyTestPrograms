@echo off
SET "CDIR=%~dp0"
:: for loop requires removing trailing backslash from %~dp0 output
SET "CDIR=%CDIR:~0,-1%"
FOR %%i IN ("%CDIR%") DO SET "PARENTFOLDERNAME=%%~nxi"
ECHO Parent folder: %PARENTFOLDERNAME%
ECHO Full path: %~dp0


jar -cvf %PARENTFOLDERNAME%.war *

move %PARENTFOLDERNAME%.war ../ 

ECHO created new WAR file: %PARENTFOLDERNAME%.war