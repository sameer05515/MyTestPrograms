@echo off
SET SCRIPTS_DIR=c:\Users\Premendra_kumar\Desktop\Scripts
pushd %SCRIPTS_DIR% && c: && set-path.bat && popd
call gradle build
call gradle wrapper --gradle-version 6.0.1
call gradle build
call gradlew bootRun