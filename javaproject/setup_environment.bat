@echo off
echo Setting up permanent environment variables for Java Development
echo ==============================================================

echo.
echo Adding JDK to PATH...
setx PATH "%PATH%;C:\Program Files\Java\jdk-25\bin" /M
if %errorlevel% neq 0 (
    echo Failed to set PATH. You may need to run as Administrator.
    echo Trying user-level PATH instead...
    setx PATH "%PATH%;C:\Program Files\Java\jdk-25\bin"
)

echo.
echo Setting JAVA_HOME...
setx JAVA_HOME "C:\Program Files\Java\jdk-25" /M
if %errorlevel% neq 0 (
    echo Failed to set JAVA_HOME. You may need to run as Administrator.
    echo Trying user-level JAVA_HOME instead...
    setx JAVA_HOME "C:\Program Files\Java\jdk-25"
)

echo.
echo Adding Maven to PATH...
setx PATH "%PATH%;C:\apache-maven\apache-maven-3.9.5\bin" /M
if %errorlevel% neq 0 (
    echo Failed to set Maven PATH. You may need to run as Administrator.
    echo Trying user-level PATH instead...
    setx PATH "%PATH%;C:\apache-maven\apache-maven-3.9.5\bin"
)

echo.
echo Environment variables have been set!
echo Please restart your Command Prompt or PowerShell for changes to take effect.
echo.
echo After restart, you can run:
echo   java -version
echo   javac -version
echo   mvn -version
echo   mvn clean compile exec:java
echo.
pause