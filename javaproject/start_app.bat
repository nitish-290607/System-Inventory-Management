@echo off
title Inventory Management System
echo Starting System Inventory Management System...
echo =============================================

REM Set environment variables for this session
set "PATH=%PATH%;C:\Program Files\Java\jdk-25\bin"
set "JAVA_HOME=C:\Program Files\Java\jdk-25"
set "PATH=%PATH%;C:\apache-maven\apache-maven-3.9.5\bin"

echo.
echo Launching application...
mvn exec:java -q

echo.
echo Application closed.
pause